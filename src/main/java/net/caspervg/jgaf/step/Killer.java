package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;

/**
 * Provides a way to kill organisms in the population
 *
 * @param <O> Type of the organism
 */
public interface Killer<O> {

    /**
     * Kills the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to kill from
     * @param selected Indices of organisms in the population to kill
     * @return New population with the selected organisms removed
     */
    Population<O> kill(Arguments arguments, Population<O> population, Collection<O> selected);

    /**
     * Default implementation of a {@link Killer}
     *
     * @param <O> Type of the organism
     */
    class Default<O> implements Killer<O> {

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation uses {@link Set#removeAll(Collection)} to remove the selected
         *     organisms from the population. The new population will be returned
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @param selected {@inheritDoc}
         */
        @Override
        public Population<O> kill(Arguments arguments, Population<O> population, Collection<O> selected) {
            Set<O> organisms = new HashSet<>(population.getAll());
            organisms.removeAll(selected);
            return new Population.Default<>(organisms);
        }
    }
}
