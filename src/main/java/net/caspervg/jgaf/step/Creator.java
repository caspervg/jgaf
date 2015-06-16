package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;

import java.util.List;

/**
 * Provides a way to create an initial population based on the arguments
 *
 * @param <O> Type of the organism
 */
@FunctionalInterface
public interface Creator<O> {

    /**
     * Creates an initial population for the genetic algorithm.
     *
     * @param arguments Arguments to use
     * @return Initial population
     */
    List<O> create(Arguments arguments);
}
