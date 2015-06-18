package net.caspervg.jgaf.step.selection;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Fitter;
import net.caspervg.jgaf.step.Selector;

import java.util.*;

public class FitnessProportionateSelector<O> implements Selector<O> {

    private Random random;
    private Fitter<O> fitter;

    private FitnessProportionateSelector() {
        // We need a fitter
    }

    public FitnessProportionateSelector(Fitter<O> fitter) {
        this.random = new Random();
        this.fitter = fitter;
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
        Collections.sort(selections, goal::compare);

        List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
        for (int i = 0; i < arguments.breedingPoolSize(); i++) {
            selected.add(organisms.get(spinRoulette(selections)));
        }

        return selected;
    }

    private double calculateTotalFitness(List<Double> fitnesses) {
        double totalFitness = 0;
        for (Double fitness : fitnesses) {
            totalFitness += fitness;
        }
        return totalFitness;
    }

    private List<Double> calculateAbsoluteFitnesses(List<O> population) {
        List<Double> absoluteFitnesses = new ArrayList<>(population.size());
        for (O o : population) {
            absoluteFitnesses.add(fitter.calculate(o));
        }
        return absoluteFitnesses;
    }

    private List<Double> calculateNormalizedFitnesses(List<Double> fitnesses, double totalFitness) {
        List<Double> normalizedFitnesses = new ArrayList<>(fitnesses.size());
        for (Double fitness: fitnesses) {
            normalizedFitnesses.add(fitness / totalFitness);
        }
        return normalizedFitnesses;
    }

    private List<Double> calculateAccumulatedFitnesses(List<Double> normalizedFitnesses) {
        List<Double> accumulatedFitnesses = new ArrayList<>(normalizedFitnesses.size());
        double accumulator = 0.0;
        for (Double fitness : normalizedFitnesses) {
            accumulator += fitness;
            accumulatedFitnesses.add(accumulator);
        }

        return accumulatedFitnesses;
    }

    private int spinRoulette(List<SelectionItem> selections) {
        double roulette = random.nextDouble() * selections.get(selections.size() - 1).getAccumulatedFitness();

        for (SelectionItem selectionItem : selections) {
            if (selectionItem.getAccumulatedFitness() > roulette) {
                return selectionItem.getIndex();
            }
        }

        throw new AssertionError("This should never happen");
    }
}
