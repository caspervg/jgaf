package net.caspervg.jgaf.step;

import java.util.List;

/**
 * Provides a way to perform crossover
 *
 * @param <O> Type of the organism
 */
public interface Crosser<O> {

    /**
     * Performs a crossover between a number of parents to create children.
     * It allows using any number of parents using a variable arguments array.
     *
     * @param parents Variable-arguments array for the parents
     * @return List of created children
     */
    @SuppressWarnings("unchecked")
    List<O> cross(O... parents);
}
