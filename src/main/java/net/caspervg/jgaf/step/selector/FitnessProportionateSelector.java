package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Fitter;
import net.caspervg.jgaf.step.Selector;

import java.util.*;

public class FitnessProportionateSelector<O> extends AbstractSelector<O> {

    public FitnessProportionateSelector(Fitter<?, O> fitter) {
        super(fitter);
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
    public Collection<O> select(Arguments arguments, Population<O> population, Goal goal) {
        List<O> organisms = new ArrayList<>(population.getAll());

        List<Double> fitnesses = calculateAbsoluteFitnesses(organisms);
        double totalFitness = calculateTotalFitness(fitnesses);
        List<Double> probabilities = calculateNormalizedFitnesses(fitnesses, totalFitness);
        List<Double> accumulateds = calculateAccumulatedFitnesses(probabilities);

        List<SelectionItem> selections = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            selections.add(new SelectionItem(
                    i,
                    probabilities.get(i),
                    accumulateds.get(i)
            ));
        }
        Collections.sort(selections, goal.opposite()::compare);

        List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
        for (int i = 0; i < arguments.breedingPoolSize(); i++) {
            selected.add(organisms.get(spinRoulette(selections)));
        }

        return selected;
    }

    private int spinRoulette(List<SelectionItem> selections) {
        double maxFitness;
        double first = selections.get(0).accumulatedFitness;
        double last = selections.get(selections.size() - 1).accumulatedFitness;
        if (first > last) {
            maxFitness = first;
        } else {
            maxFitness = last;
        }

        double roulette = random.nextDouble() * maxFitness;

        for (SelectionItem selectionItem : selections) {
            if (selectionItem.getAccumulatedFitness() > roulette) {
                selections.remove(selectionItem);
                return selectionItem.getIndex();
            }
        }

        throw new AssertionError("This should never happen");
    }
}
