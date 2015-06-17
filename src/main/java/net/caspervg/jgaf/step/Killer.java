package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;
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
        private Random random;

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
            this.random = new Random();
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
        public Collection<O> select(Arguments arguments, Population<O> population) {
            List<O> organisms = new ArrayList<>(population.getAll());

            double totalFitness = calculateTotalFitness(organisms);
            List<Double> fitnesses = calculateAbsoluteFitnesses(organisms);
            List<Double> probabilities = calculateNormalizedFitnesses(fitnesses, totalFitness);
            List<Double> accumulateds = calculateAccumulatedFitnesses(probabilities);

            List<KillerSelectionItem> selections = new ArrayList<>();
            for (int i = 0; i < population.size(); i++) {
                selections.add(new KillerSelectionItem(
                        i,
                        probabilities.get(i),
                        accumulateds.get(i)
                ));
            }
            Collections.sort(selections, KillerSelectionItem::compareTo);

            List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
            for (int i = 0; i < arguments.breedingPoolSize(); i++) {
                selected.add(organisms.get(spinRoulette(selections)));
            }

            return selected;
        }

        protected double calculateTotalFitness(List<O> population) {
            return population.stream().collect(Collectors.summingDouble(fitter::calculate));
        }

        protected List<Double> calculateAbsoluteFitnesses(List<O> population) {
            return population.stream().map(o -> fitter.calculate(o).doubleValue()).collect(Collectors.toList());
        }

        protected List<Double> calculateNormalizedFitnesses(List<Double> fitnesses, double totalFitness) {
            return fitnesses.stream().map(fitness -> fitness / totalFitness).collect(Collectors.toList());
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

        protected int spinRoulette(List<KillerSelectionItem> selections) {
            double roulette = random.nextDouble() * selections.get(0).getAccumulatedFitness();

            for (KillerSelectionItem selectionItem : selections) {
                if (selectionItem.getAccumulatedFitness() > roulette) {
                    selections.remove(selectionItem);   // Ensure no double picking
                    return selectionItem.getIndex();
                }
            }

            throw new AssertionError("This should never happen");
        }
    }

    class KillerSelectionItem extends SelectionItem implements Comparable<KillerSelectionItem> {
        KillerSelectionItem(int index, double normalizedFitness, double accumulatedFitness) {
            super(index, normalizedFitness, accumulatedFitness);
        }

        @Override
        public int compareTo(KillerSelectionItem selectionItem) {
            return Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }
}
