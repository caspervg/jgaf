package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.Organism;

/**
 * Provides strategies for all the steps in a genetic algorithm.
 *
 * @param <F> Type of the fitness
 * @param <O> Type of the organism
 */
public interface Provider<O extends Organism<O, F>, F extends Number & Comparable> {

    /**
     * Provides a {@link Creator}.
     * This is used to generate the initial population.
     *
     * @return Creator for the initial population
     */
    Creator<O, F> creator();

    /**
     * Provides a {@link Optimizer}
     * This is used to compare organisms with each other based on their fitness
     *
     * @return Optimizer for organisms
     */
    Optimizer<O, F> optimizer();

    /**
     * Provides a {@link Goal} for the Genetic Algorithm (for example: maximisation)
     *
     * @return Goal for the genetic algorithm
     */
    Goal goal();
}
