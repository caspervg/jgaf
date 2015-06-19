package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.step.Fitter;
import net.caspervg.jgaf.step.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class AbstractSelector<O> implements Selector<O> {

    protected Fitter<O> fitter;
    protected Random random;

    public AbstractSelector(Fitter<O> fitter) {
        this.fitter = fitter;
        this.random = new Random();
    }

    double calculateTotalFitness(List<Double> fitnesses) {
        double totalFitness = 0;
        for (Double fitness : fitnesses) {
            totalFitness += fitness;
        }
        return totalFitness;
    }

    List<Double> calculateAbsoluteFitnesses(List<O> population) {
        List<Double> absoluteFitnesses = new ArrayList<>(population.size());
        for (O o : population) {
            absoluteFitnesses.add(fitter.calculate(o));
        }
        return absoluteFitnesses;
    }

    List<Double> calculateNormalizedFitnesses(List<Double> fitnesses, double totalFitness) {
        List<Double> normalizedFitnesses = new ArrayList<>(fitnesses.size());
        for (Double fitness: fitnesses) {
            normalizedFitnesses.add(fitness / totalFitness);
        }
        return normalizedFitnesses;
    }

    List<Double> calculateAccumulatedFitnesses(List<Double> normalizedFitnesses) {
        List<Double> accumulatedFitnesses = new ArrayList<>(normalizedFitnesses.size());
        double accumulator = 0.0;
        for (Double fitness : normalizedFitnesses) {
            accumulator += fitness;
            accumulatedFitnesses.add(accumulator);
        }

        return accumulatedFitnesses;
    }
}
