package net.caspervg.jgaf;

import net.caspervg.jgaf.step.StepProvider;

import java.util.List;
import java.util.Optional;

public interface GeneticAlgorithm<O extends Organism> {

    Solution<O> run(Arguments arguments, StepProvider provider);

    class Default<O extends Organism> implements GeneticAlgorithm<O> {
        @Override
        public Solution<O> run(Arguments arguments, StepProvider provider) {
            int iterations = 0;
            List<O> population = provider.creator().create(arguments);

            while (iterations < arguments.numIterations()) {
                List<O> children = provider.breeder().breed(arguments, population);
                children = provider.mutator().mutate(arguments, children);

                population.addAll(children);

                provider.killer().kill(arguments, population);
            }

            O bestOrganism = population.stream().max(Organism::compare).get();
            Number bestFitness = bestOrganism.fitness();
            return new Solution<>(
                    bestFitness,
                    bestOrganism,
                    population
            );
        }
    }
}
