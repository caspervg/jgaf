package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Provider;

import java.util.Collection;

/**
 * Provides the genetic algorithm
 *
 * @param <O> Type of the organism
 */
public interface GeneticAlgorithm<O> {

    /**
     * Runs the genetic algorithm using given arguments and strategy provider
     *
     * @param arguments Arguments to use for the execution
     * @param provider Strategy providers for the various steps
     * @return Solution object containing the best organism, the best fitness and the final population
     */
    Solution<O> run(Arguments arguments, Provider<O> provider);

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
    class Default<O> implements GeneticAlgorithm<O> {

        /**
         * {@inheritDoc}
         * <p>
         *     Runs the default genetic algorithm. See {@link net.caspervg.jgaf.GeneticAlgorithm.Default} for the
         *     algorithm used.
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param provider {@inheritDoc}
         * @return {@inheritDoc}
         */
        @Override
        public Solution<O> run(Arguments arguments, Provider<O> provider) {
            int iterations = 0;
            Population<O> population = provider.creator().create(arguments);

            while (iterations < arguments.numIterations()) {
                Collection<O> parents = provider.selector().select(arguments, population, arguments.goal());
                Collection<O> children = provider.breeder().breed(arguments, population, parents);
                children = provider.mutator().mutate(arguments, children);

                population.addAll(children);

                Collection<O> conscripted = provider.selector().select(arguments, population, arguments.goal().opposite());
                population = provider.killer().kill(arguments, population, conscripted);

                iterations++;
            }


            O bestOrganism = population.getAll().stream().max(provider.optimizer()).get();
            Number bestFitness = provider.fitter().calculate(bestOrganism);
            return new Solution<>(
                    bestFitness,
                    bestOrganism,
                    population
            );
        }
    }
}
