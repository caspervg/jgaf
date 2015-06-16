package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Provides a way to kill organisms in the population
 *
 * @param <O> Type of the organism
 */
public interface Killer<O> {

    /**
     * Selects the organisms in the population that should be killed
     *
     * @param arguments Arguments to use
     * @param population Population to select from
     * @return List of indices of organisms to kill
     */
    Collection<O> select(Arguments arguments, Population<O> population);

    /**
     * Kills the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to kill from
     * @param selected Indices of organisms in the population to kill
     * @return New population with the selected organisms removed
     */
    Population<O> kill(Arguments arguments, Population<O> population, Collection<O> selected);

    /**
     * Selects the individuals to kill using {@link #select(Arguments, Population)}, then kills
     * them using {@link #kill(Arguments, Population, Collection)}
     *
     * @param arguments Arguments to use
     * @param population Population to select and kill from
     * @return New population with the selected organisms removed
     */
    default Population<O> kill(Arguments arguments, Population<O> population) {
        Collection<O> selected = select(arguments, population);
        return kill(arguments, population, selected);
    }

    /**
     * Default implementation of a {@link Killer}
     *
     * @param <O> Type of the organism
     */
    class Default<O> implements Killer<O> {

        private Fitter<O> fitter;

        private Default() {
            // We need a fitter
        }

        /**
         * Creates a new default Breeder that will use given {@link Fitter} in the killing process
         *
         * @param fitter Fitter to use for breeding
         */
        public Default(Fitter<O> fitter) {
            this.fitter = fitter;
        }

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation uses {@link List#removeAll(Collection)} to remove the selected
         *     organisms from the population. The population list will be edited in the process.
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @param selected {@inheritDoc}
         */
        @Override
        public Population<O> kill(Arguments arguments, Population<O> population, Collection<O> selected) {
            Collection<O> organisms = population.getAll();
            organisms.removeAll(selected);
            return new Population.Default<>(organisms);
        }

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation uses a basic reverse <b>fitness proportionate selection</b> (also known as
         *     <b>roulette-wheel selection</b> to select the organisms for killing. For more information
         *     about this algorithm, read <a href='http://en.wikipedia.org/wiki/Selection_%28genetic_algorithm%29'>this
         *     Wikipedia article</a>.
         *     It's a reverse selection, because we want <i>worse</i> organisms to have an increased chance
         *     of being selected for killing.
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @return {@inheritDoc}
         */
        @Override
        public List<O> select(Arguments arguments, Population<O> population) {
            double totalFitness = calculateTotalFitness(population);
            List<Double> fitnesses = calculateAbsoluteFitnesses(population);
            List<Double> probabilities = calculateRelativeFitnesses(fitnesses, totalFitness);

            List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
            for (int i = 0; i < arguments.breedingPoolSize(); i++) {
                selected.add(spinRoulette(population, probabilities, totalFitness));
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
            // Larger fitness => smaller probability => smaller chance to be picked for breeding
            return fitnesses.stream().map(fitness -> totalFitness / fitness).collect(Collectors.toList());
        }

        private O spinRoulette(Population<O> population, List<Double> relativeFitnesses, double totalFitness) {
            Random random = new Random();
            double roulette = random.nextDouble() * totalFitness;

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
