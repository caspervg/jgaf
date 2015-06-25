package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Provider;

import java.util.Collection;

/**
 * Provides the genetic algorithm
 *
 * @param <O> Type of the organism
 */
@FunctionalInterface
public interface GeneticAlgorithm<O extends Organism<O, ?>> {

    /**
     * Runs the genetic algorithm
     *
     * @return Solution object containing the best organism, the best fitness and the final population
     */
    Solution<O> run();

    /**
     * Default implementation of a genetic algorithm.
     * <p>
     *     The algorithm used is:
     *     <ol>
     *         <li>Create an initial population</li>
     *         <li>While there are still iterations to go
     *         <ol>
     *             <li>Breed children from a selection of the population</li>
     *             <li>Mutate the children that were bred</li>
     *             <li>Add the children to the population</li>
     *             <li>Kill a selection of the population</li>
     *         </ol>
     *         <li>Find the best organism and return the solution</li>
     *     </ol>
     *
     * @param <O> Type of the organism
     */
    class Default<O extends Organism<O, ?>> implements GeneticAlgorithm<O> {

        private Arguments arguments;
        private Provider<O, ?> provider;

        /**
         * Creates a new Default {@link GeneticAlgorithm}
         *
         * @param arguments Arguments to use for the processing
         * @param provider Provider to use for step injection
         */
        public Default(Arguments arguments, Provider<O, ?> provider) {
            this.arguments = arguments;
            this.provider = provider;
        }

        /**
         * {@inheritDoc}
         * <p>
         *     Runs the default genetic algorithm.
         *
         *     The algorithm used in this implementation is:
         *     <ol>
         *         <li>Create an initial population</li>
         *         <li>While there are still iterations to go
         *         <ol>
         *             <li>Breed children from a selection of the population</li>
         *             <li>Mutate the children that were bred</li>
         *             <li>Add the children to the population</li>
         *             <li>Kill a selection of the population</li>
         *         </ol>
         *         <li>Find the best organism and return the solution</li>
         *     </ol>
         * </p>
         *
         * @return {@inheritDoc}
         */
        @Override
        public Solution<O> run() {
            int iterations = 0;
            Population<O> population = provider.creator().create(arguments);

            while (iterations < arguments.numIterations()) {
                population.breed(arguments);
                population.kill(arguments);
                iterations++;
            }


            O bestOrganism = population.getAll().stream().max(provider.optimizer()).get();
            Number bestFitness = bestOrganism.fitness();
            return new Solution<>(
                    bestFitness,
                    bestOrganism,
                    population
            );
        }
    }
}
