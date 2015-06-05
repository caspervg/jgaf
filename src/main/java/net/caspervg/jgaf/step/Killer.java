package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Provides a way to kill organisms in the population
 *
 * @param <O> Type of the organism
 */
public interface Killer<O> {

    /**
     * Selects the iorganisms in the population that should be killed
     *
     * @param arguments Arguments to use
     * @param population Population to select from
     * @return List of indices of organisms to kill
     */
    List<O> select(Arguments arguments, List<O> population);

    /**
     * Kills the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to kill from
     * @param selected Indices of organisms in the population to kill
     */
    void kill(Arguments arguments, List<O> population, List<O> selected);

    /**
     * Selects the individuals to kill using {@link #select(Arguments, List)}, then kills
     * them using {@link #kill(Arguments, List, List)}
     *
     * @param arguments Arguments to use
     * @param population Population to select and kill from
     */
    default void kill(Arguments arguments, List<O> population) {
        List<O> selected = select(arguments, population);
        kill(arguments, population, selected);
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
        public void kill(Arguments arguments, List<O> population, List<O> selected) {
            population.removeAll(selected);
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
        public List<O> select(Arguments arguments, List<O> population) {
            double totalFitness = 0, accumulated = 0;
            int numSelected = 0;

            for (O o : population) {
                totalFitness += fitter.calculate(o).doubleValue();
            }

            List<KillerSelectionItem> selections = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); i++) {
                selections.add(new KillerSelectionItem(
                        i,
                        fitter.calculate(population.get(i)).doubleValue() / totalFitness
                ));
            }

            selections.sort(KillerSelectionItem::compareTo);

            for (int i = 0; i < population.size(); i++) {
                accumulated += selections.get(i).getNormalizedFitness();
                selections.set(i, new KillerSelectionItem(
                        selections.get(i),
                        accumulated
                ));
            }

            List<O> selected = new ArrayList<>(arguments.killingPoolSize());
            Random random = new Random();
            while (numSelected < arguments.killingPoolSize()) {
                double cutOff = random.nextDouble() * selections.get(0).getAccumulatedFitness();

                for (int i = 0; i < population.size(); i++) {
                    if (selections.get(i).getAccumulatedFitness() > cutOff) {
                        selected.add(population.get(selections.get(i).getIndex()));
                        selections.set(i, new KillerSelectionItem(
                                selections.get(i),
                                -1.0D
                        ));
                        break;
                    }
                }

                numSelected++;
            }

            return selected;
        }
    }

    class KillerSelectionItem extends SelectionItem implements Comparable<KillerSelectionItem> {

        KillerSelectionItem(int index, double normalizedFitness) {
            super(index, normalizedFitness);
        }

        KillerSelectionItem(KillerSelectionItem old, double accumulatedFitness) {
            super(old, accumulatedFitness);
        }

        @Override
        public int compareTo(@NotNull KillerSelectionItem selectionItem) {
            return Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }
}
