package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;

import java.util.Comparator;

/**
 * Provides strategies for all the steps in a genetic algorithm.
 *
 * @param <F> Type of the fitness
 * @param <O> Type of the organism
 */
public interface Provider<F extends Number & Comparable, O> {

    /**
     * Provides a {@link Creator}.
     * This is used to generate the initial population.
     *
     * @return Creator for the initial population
     */
    Creator<O> creator();

    /**
     * Provides a {@link Breeder}.
     * This is used to breed organisms and perform possible crossovers.
     *
     * @return Breeder for the population
     */
    Breeder<O> breeder();

    /**
     * Provides a {@link Mutator}.
     * This is used to mutate organisms.
     *
     * @return Mutator for organisms
     */
    Mutator<O> mutator();

    /**
     * Provides a {@link Killer}.
     * This is used to kill organisms.
     *
     * @return Killer for the population
     */
    Killer<O> killer();

    /**
     * Provides a {@link Fitter}.
     * This is used to calculate the fitness of an organism.
     *
     * @return Fitter for organisms
     */
    Fitter<F, O> fitter();

    /**
     * Provides a {@link Selector}.
     * This is used to select which organism(s) are picked for breeding and death.
     *
     * @return Selector for organisms
     */
    Selector<O> selector();

    /**
     * Provides a {@link Optimizer}
     * This is used to compare organisms with each other based on their fitness
     *
     * @return Optimizer for organisms
     */
    Optimizer<F, O> optimizer();

    /**
     * Provides a {@link Goal} for the Genetic Algorithm (for example: maximisation)
     *
     * @return Goal for the genetic algorithm
     */
    Goal goal();
}
