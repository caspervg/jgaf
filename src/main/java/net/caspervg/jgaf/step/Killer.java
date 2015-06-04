package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Killer {

    <O extends Organism> List<O> select(Arguments arguments, List<O> population);

    <O extends Organism> void kill(Arguments arguments, List<O> population, List<O> selected);

    default <O extends Organism> void kill(Arguments arguments, List<O> population) {
        List<O> selected = select(arguments, population);
        kill(arguments, population, selected);
    }

    class Default implements Killer {

        @Override
        public <O extends Organism> void kill(Arguments arguments, List<O> population, List<O> selected) {
            population.removeAll(selected);
        }

        @Override
        public <O extends Organism> List<O> select(Arguments arguments, List<O> population) {
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

            List<O> selected = new ArrayList<>(arguments.killingPoolSize());
            Random random = new Random();
            while (numSelected < arguments.killingPoolSize()) {
                double cutOff = random.nextDouble() * selections.get(0).getAccumulatedFitness();

                for (int i = 0; i < population.size(); i++) {
                    if (selections.get(i).getAccumulatedFitness() > cutOff) {
                        selected.add(population.get(selections.get(i).getIndex()));
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
            return -Double.compare(getNormalizedFitness(), selectionItem.getNormalizedFitness());
        }
    }
}
