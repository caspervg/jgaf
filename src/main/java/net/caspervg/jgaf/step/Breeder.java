package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.util.FitnessComparator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides a way to breed (a part of) the population based on given arguments
 *
 * @param <O> Type of the organism
 */
public interface Breeder<O> {

    /**
     * Selects the indices of organisms in the population that should be bred
     *
     * @param arguments Arguments to use
     * @param population Population to select from
     * @return Collection of organisms to breed
     */
     Collection<O> select(Arguments arguments, Population<O> population);

    /**
     * Breeds the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to breed from
     * @param selected Indices of organisms in the population to breed
     * @return Collection of children that were bred
     */
    Collection<O> breed(Arguments arguments, Population<O> population, Collection<O> selected);

    /**
     * Selects the individuals to breed using {@link #select(Arguments, Population)}, then breeds
     * them using {@link #breed(Arguments, Population, Collection)}
     *
     * @param arguments Arguments to use
     * @param population Population to select and breed from
     * @return Collection of children that were bred
     */
    default Collection<O> breed(Arguments arguments, Population<O> population) {
        Collection<O> selected = select(arguments, population);
        return breed(arguments, population, selected);
    }

    /**
     * Default implementation of a {@link Breeder}
     *
     * @param <O> Type of the organism
     */
    class Default<O> implements Breeder<O> {

        private Crosser<O> crosser;
        private Fitter<O> fitter;

        private Default() {
            // We need a fitter and a crosser
        }

        /**
         * Creates a new default Breeder that will use given Crosser and Fitter in the breeding process
         *
         * @param crosser Crosser to use for breeding
         * @param fitter Fitter to use for breeding
         */
        public Default(Crosser<O> crosser, Fitter<O> fitter) {
            this.crosser = crosser;
            this.fitter = fitter;
        }

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation loops over the selection list and breeds pairs
         *     of organisms from it. For breeding, it uses the {@link Crosser} that was
         *     supplied in the constructor.
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @param selected {@inheritDoc}
         * @return {@inheritDoc}
         */
        @Override
        public Collection<O> breed(Arguments arguments, Population<O> population, Collection<O> selected) {
            List<O> bred = new ArrayList<>();
            List<O> breedables = new ArrayList<>(selected);

            for (int i = 0; i < arguments.breedingPoolSize(); i+=2) {
                O father = breedables.get(i);
                O mother = breedables.get(i+1);
                List<O> parents = new ArrayList<>();
                parents.add(father);
                parents.add(mother);

                List<O> children = crosser.cross(parents);
                bred.addAll(children);
            }

            return bred;
        }

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation uses a basic <b>fitness proportionate selection</b> (also known as
         *     <b>roulette-wheel selection</b> to select the organisms for breeding. For more information
         *     about this algorithm, read <a href='http://en.wikipedia.org/wiki/Selection_%28genetic_algorithm%29'>this
         *     Wikipedia article</a>
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @return {@inheritDoc}
         */
        @Override
        public Collection<O> select(Arguments arguments, Population<O> population) {
            List<O> organisms = new ArrayList<>(population.getAll());
            Collections.sort(organisms, new FitnessComparator<>(fitter));
            Population<O> sortedPopulation = new Population.Default<>(organisms);

            double totalFitness = calculateTotalFitness(sortedPopulation);
            List<Double> fitnesses = calculateAbsoluteFitnesses(sortedPopulation);
            List<Double> probabilities = calculateRelativeFitnesses(fitnesses, totalFitness);

            List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
            for (int i = 0; i < arguments.breedingPoolSize(); i++) {
                selected.add(spinRoulette(sortedPopulation, probabilities));
            }

            return selected;
        }

        private double calculateTotalFitness(Population<O> population) {
            return population.getAll().stream().collect(Collectors.summingDouble(fitter::calculate));
        }

        private List<Double> calculateAbsoluteFitnesses(Population<O> population) {
            return population.getAll().stream().map(o -> fitter.calculate(o).doubleValue()).collect(Collectors.toList());
        }

        private List<Double> calculateRelativeFitnesses(List<Double> fitnesses, double totalFitness) {
            // Larger fitness => larger probability => larger chance to be picked for breeding
            return fitnesses.stream().map(fitness -> fitness / totalFitness).collect(Collectors.toList());
        }

        private O spinRoulette(Population<O> population, List<Double> relativeFitnesses) {
            Random random = new Random();
            double roulette = random.nextDouble();

            double lowerBound = 0.0, upperBound = 0.0;
            for (int i = 0; i < population.size(); i++) {
                double relativeFitness = relativeFitnesses.get(i);

                upperBound += relativeFitness;
                if (lowerBound <= roulette && upperBound > roulette) {
                    return population.get(i);
                } else {
                    lowerBound = upperBound;
                }
            }

            throw new AssertionError("This should never happen");
        }
    }
}
