package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Breeder {

    <O extends Organism> List<Integer> select(Arguments arguments, List<O> population);

    <O extends Organism> List<O> breed(Arguments arguments, List<O> population, List<Integer> selected);

    default <O extends Organism> List<O> breed(Arguments arguments, List<O> population) {
        List<Integer> selected = select(arguments, population);
        return breed(arguments, population, selected);
    }

    class Default implements Breeder {

        private Crosser crosser;

        private Default() {
            // We need an instance responsible for the actual crossover
        }

        public Default(Crosser crosser) {
            this.crosser = crosser;
        }

        @Override
        public <O extends Organism> List<O> breed(Arguments arguments, List<O> population, List<Integer> selected) {
            List<O> bred = new ArrayList<>();

            for (int i = 0; i < arguments.breedingPoolSize(); i+=2) {
                O father = population.get(selected.get(i));
                O mother = population.get(selected.get(i+1));

                List<O> children = crosser.cross(father, mother);
                bred.addAll(children);
            }

            return bred;
        }

        @Override
        public <O extends Organism> List<Integer> select(Arguments arguments, List<O> population) {
            double totalFitness = 0, accumulated = 0;
            int numSelected = 0;

            for (Organism o : population) {
                totalFitness += o.fitness().doubleValue();
            }

            List<BreederSelectionItem> selections = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); i++) {
                selections.add(new BreederSelectionItem(
                        i,
                        population.get(i).fitness().doubleValue() / totalFitness
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
