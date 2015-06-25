package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;

/**
 * Provides a way to kill organisms in the population
 *
 * @param <O> Type of the organism
 */
public interface Killer<O> extends Step<O> {

    /**
     * Kills the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to kill from
     * @param selected Indices of organisms in the population to kill
     * @return New population with the selected organisms removed
     */
    Population<O> kill(Arguments arguments, Population<O> population, Collection<O> selected);


}
