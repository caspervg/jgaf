package net.caspervg.jgaf;

import java.util.Collections;
import java.util.List;

public interface Organism<O extends Organism, F extends Number & Comparable> {

    /**
     * Mutates this organism
     *
     * @param arguments Arguments that might be useful for the mutation. For example
     *                  {@link Arguments#maximumMutationAmount()}.
     */
    void mutate(Arguments arguments);

    /**
     * Calculates the fitness of the organism
     *
     * @return Fitness of the organism
     */
    F fitness();

    /**
     * Performs a crossover between this organism and a number of
     * crossers to create children.
     * The number of crossers and children can be chosen at will.
     *
     * @param crossers List of organisms to use in crossover
     * @return List of created children
     */
    List<O> cross(List<O> crossers);

    /**
     * Performs a crossover between this organism and another
     * to create children.
     * The number of created children will depend on the number of children in
     * the {@link #cross(List)} implementation.
     *
     * @param organism Organism to use for crossing
     * @return List of created children
     */
    default List<O> cross(O organism) {
        return cross(Collections.singletonList(organism));
    }
}
