package net.caspervg.jgaf.step.killer;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Killer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of a {@link Killer}
 *
 * @param <O> Type of the organism
 */
public class BasicKiller<O> implements Killer<O> {

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
