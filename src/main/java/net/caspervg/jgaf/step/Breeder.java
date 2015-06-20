package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;

/**
 * Provides a way to breed (a part of) the population based on given arguments
 *
 * @param <O> Type of the organism
 */
public interface Breeder<O> extends Step<O> {

    /**
     * Breeds the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to breed from
     * @param parents Organisms to function as parents in the breeding process
     * @return Collection of children that were procreated
     */
    Collection<O> breed(Arguments arguments, Population<O> population, Collection<O> parents);

}
