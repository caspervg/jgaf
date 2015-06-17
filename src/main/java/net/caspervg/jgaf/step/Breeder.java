package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;

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
        private Random random;

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
            this.random = new Random();
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

            List<Double> fitnesses = calculateAbsoluteFitnesses(organisms);
            double totalFitness = calculateTotalFitness(fitnesses);
            List<Double> probabilities = calculateNormalizedFitnesses(fitnesses, totalFitness);
            List<Double> accumulateds = calculateAccumulatedFitnesses(probabilities);

            List<BreederSelectionItem> selections = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {
                selections.add(new BreederSelectionItem(
                        i,
                        probabilities.get(i),
                        accumulateds.get(i)
                ));
            }
            Collections.sort(selections, BreederSelectionItem::compareTo);

            List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
            for (int i = 0; i < arguments.breedingPoolSize(); i++) {
                selected.add(organisms.get(spinRoulette(selections)));
            }

            return selected;
        }

        protected double calculateTotalFitness(List<Double> fitnesses) {
            double totalFitness = 0;
            for (Double fitness : fitnesses) {
                totalFitness += fitness;
            }
            return totalFitness;
        }

        protected List<Double> calculateAbsoluteFitnesses(List<O> population) {
            List<Double> absoluteFitnesses = new ArrayList<>(population.size());
            for (O o : population) {
                absoluteFitnesses.add(fitter.calculate(o));
            }
            return absoluteFitnesses;
        }

        protected List<Double> calculateNormalizedFitnesses(List<Double> fitnesses, double totalFitness) {
            List<Double> normalizedFitnesses = new ArrayList<>(fitnesses.size());
            for (Double fitness: fitnesses) {
                normalizedFitnesses.add(fitness / totalFitness);
            }
            return normalizedFitnesses;
        }

        protected List<Double> calculateAccumulatedFitnesses(List<Double> normalizedFitnesses) {
            List<Double> accumulatedFitnesses = new ArrayList<>(normalizedFitnesses.size());
            double accumulator = 0.0;
            for (Double fitness : normalizedFitnesses) {
                accumulator += fitness;
                accumulatedFitnesses.add(accumulator);
            }

            return accumulatedFitnesses;
        }

        protected int spinRoulette(List<BreederSelectionItem> selections) {
            double roulette = random.nextDouble() * selections.get(selections.size() - 1).getAccumulatedFitness();

            for (BreederSelectionItem selectionItem : selections) {
                if (selectionItem.getAccumulatedFitness() > roulette) {
                    return selectionItem.getIndex();
                }
            }

            throw new AssertionError("This should never happen");
        }
    }

    class BreederSelectionItem extends SelectionItem implements Comparable<BreederSelectionItem> {
        BreederSelectionItem(int index, double normalizedFitness, double accumulatedFitness) {
            super(index, normalizedFitness, accumulatedFitness);
        }

        @Override
        public int compareTo(BreederSelectionItem selectionItem) {
            return -Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }
}
