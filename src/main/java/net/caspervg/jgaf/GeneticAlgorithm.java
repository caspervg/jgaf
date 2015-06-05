package net.caspervg.jgaf;

import net.caspervg.jgaf.util.FitnessComparator;
import net.caspervg.jgaf.step.StepProvider;

import java.util.List;

public interface GeneticAlgorithm<O> {

    Solution<O> run(Arguments arguments, StepProvider<O> provider);

    class Default<O> implements GeneticAlgorithm<O> {
        @Override
        public Solution<O> run(Arguments arguments, StepProvider<O> provider) {
            int iterations = 0;
            List<O> population = provider.creator().create(arguments);

            while (iterations < arguments.numIterations()) {
                List<O> children = provider.breeder().breed(arguments, population);
                children = provider.mutator().mutate(arguments, children);

                population.addAll(children);

                provider.killer().kill(arguments, population);
            }

            FitnessComparator<O> comparator = new FitnessComparator<>(provider.fitter());
            O bestOrganism = population.stream().max(comparator).get();
            Number bestFitness = provider.fitter().calculate(bestOrganism);
            return new Solution<>(
                    bestFitness,
                    bestOrganism,
                    population
            );
        }
    }
}
