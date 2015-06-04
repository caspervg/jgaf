package net.caspervg.jgaf;

import net.caspervg.jgaf.step.StepProvider;

import java.util.List;

public interface GeneticAlgorithm<O extends Organism> {

    List<O> run(Arguments arguments, StepProvider provider);

    class Default<O extends Organism> implements GeneticAlgorithm<O> {

        @Override
        public List<O> run(Arguments arguments, StepProvider provider) {
            List<Organism> population = provider.creator().create(arguments);
            return null;
        }
    }
}
