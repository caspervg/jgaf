package net.caspervg.jgaf.step;

import java.util.List;

/**
 * Provides a way to perform crossover
 *
 * @param <O> Type of the organism
 */
@FunctionalInterface
public interface Crosser<O> {

    /**
     * Performs a crossover between a number of parents to create children.
     * The number of parents and children can be chosen at will.
     *
     * @param parents List of parents to use
     * @return List of created children
     */
    List<O> cross(List<O> parents);
}
