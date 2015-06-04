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

            List<SelectionItem> selections = new ArrayList<>(population.size());
            for (int i = 0; i < population.size(); i++) {
                selections.add(new SelectionItem(
                        i,
                        population.get(i).fitness().doubleValue() / totalFitness
                ));
            }

            selections.sort(SelectionItem::compareTo);

            for (int i = 0; i < population.size(); i++) {
                accumulated += selections.get(i).getNormalizedFitness();
                selections.set(i, new SelectionItem(
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
                        selections.set(i, new SelectionItem(
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

    class SelectionItem implements Comparable<SelectionItem> {
        int index;
        double normalizedFitness;
        double accumulatedFitness;

        SelectionItem(int index, double normalizedFitness) {
            this.index = index;
            this.normalizedFitness = normalizedFitness;
            this.accumulatedFitness = -1;
        }

        SelectionItem(SelectionItem old, double accumulatedFitness) {
            this.index = old.index;
            this.normalizedFitness = old.normalizedFitness;
            this.accumulatedFitness = accumulatedFitness;
        }

        int getIndex() {
            return index;
        }

        double getNormalizedFitness() {
            return normalizedFitness;
        }

        double getAccumulatedFitness() {
            return accumulatedFitness;
        }

        @Override
        public int compareTo(@NotNull SelectionItem selectionItem) {
            return Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }

}
