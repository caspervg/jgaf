package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * @return List of indices of organisms to breed
     */
    List<Integer> select(Arguments arguments, List<O> population);

    /**
     * Breeds the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to breed from
     * @param selected Indices of organisms in the population to breed
     * @return List of children
     */
    List<O> breed(Arguments arguments, List<O> population, List<Integer> selected);

    /**
     * Selects the individuals to breed using {@link #select(Arguments, List)}, then breeds
     * them using {@link #breed(Arguments, List, List)}
     *
     * @param arguments Arguments to use
     * @param population Population to select and breed from
     * @return List of children
     */
    default List<O> breed(Arguments arguments, List<O> population) {
        List<Integer> selected = select(arguments, population);
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
        public List<O> breed(Arguments arguments, List<O> population, List<Integer> selected) {
            List<O> bred = new ArrayList<>();

            for (int i = 0; i < arguments.breedingPoolSize(); i+=2) {
                O father = population.get(selected.get(i));
                O mother = population.get(selected.get(i+1));

                List<O> children = crosser.cross(father, mother);
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
        public List<Integer> select(Arguments arguments, List<O> population) {
            double totalFitness = 0, accumulated = 0;
            int numSelected = 0;

            for (O o : population) {
                totalFitness += fitter.calculate(o).doubleValue();
            }

            List<BreederSelectionItem> selections = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); i++) {
                selections.add(new BreederSelectionItem(
                        i,
                        fitter.calculate(population.get(i)).doubleValue() / totalFitness
                ));
            }

            selections.sort(BreederSelectionItem::compareTo);

            for (int i = 0; i < population.size(); i++) {
                accumulated += selections.get(i).getNormalizedFitness();
                selections.set(i, new BreederSelectionItem(
                        selections.get(i),
                        accumulated
                ));
            }

            List<Integer> selected = new ArrayList<>(arguments.breedingPoolSize());
            Random random = new Random();
            while (numSelected < arguments.breedingPoolSize()) {
                double cutOff = random.nextDouble() * selections.get(selections.size() - 1).getAccumulatedFitness();

                for (int i = 0; i < population.size(); i++) {
                    if (selections.get(i).getAccumulatedFitness() > cutOff) {
                        selected.add(selections.get(i).getIndex());
                        selections.set(i, new BreederSelectionItem(
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

    class BreederSelectionItem extends SelectionItem implements Comparable<BreederSelectionItem> {
        BreederSelectionItem(int index, double normalizedFitness) {
            super(index, normalizedFitness);
        }

        BreederSelectionItem(BreederSelectionItem old, double accumulatedFitness) {
            super(old, accumulatedFitness);
        }

        @Override
        public int compareTo(@NotNull BreederSelectionItem selectionItem) {
            return Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }

}
