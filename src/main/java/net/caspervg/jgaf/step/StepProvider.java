package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Optimizer;

/**
 * Provides strategies for all the steps in a genetic algorithm.
 *
 * @param <O> Type of the organism
 */
public interface StepProvider<O> {

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
    Fitter<O> fitter();

    /**
     * Provides a {@link Optimizer}
     * This is used to compare organisms with each other based on their fitness
     *
     * @return Optimizer for organisms
     */
    Optimizer<O> optimizer();
}
