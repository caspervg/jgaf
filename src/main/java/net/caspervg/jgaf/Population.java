package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provides a semantic representation of a {@link Collection} of organisms
 *
 * @param <O> Type of the organism
 */
public interface Population<O> {

    /*
     * Algorithm related methods
     */

    /**
     * Breeds (some of the) organisms in this population and adds them to the
     * backing collection. Implementations can use a {@link Selector} to select
     * which organisms to breed.
     *
     * @param arguments Arguments that could be useful in the breeding process. For example
     *                  {@link Arguments#breedingPoolSize()}
     */
    void breed(Arguments arguments);

    /**
     * Kills (some of the) organisms in this population and removes them from
     * the backing collection. Implementations can use a {@link Selector} to select
     * which organisms to kill.
     *
     * @param arguments Arguments that could be useful in the killing process. For example
     *                  {@link Arguments#killingPoolSize()}
     */
    void kill(Arguments arguments);

    /*
     * Collection related methods
     */

    /**
     * Adds an organism to the population
     *
     * @param o Organism to add
     */
    void add(O o);

    /**
     * Adds a collection of organisms to the population
     *
     * @param os Organisms to add
     */
    void addAll(Collection<? extends O> os);

    /**
     * Retrieves the organism at the specific index
     *
     * @param index Index to retrieve organism from
     * @return Organism at the specified index
     * @throws UnsupportedOperationException if the implementation does not support an ordering inside the population
     */
    O get(int index);

    /**
     * Returns a copy of the backing collection
     *
     * @return Copy of the backing collection
     */
    Collection<O> getAll();

    /**
     * Returns an iterator for the backing collection
     *
     * @return Iterator of the backing collection
     */
    Iterator<O> iterator();

    /**
     * Removes an organism from the backing collection
     *
     * @param o Organism to remove
     */
    void remove(O o);

    /**
     * Removes all the specified organisms from the backing collection
     *
     * @param os Organisms to remove
     */
    void removeAll(Collection<? extends O> os);

    /**
     * Returns the size of the population
     *
     * @return current size of the population
     */
    int size();

    /**
     * Returns a stream of the organisms in this population
     *
     * @return Stream of organisms in this population
     * @see Stream
     */
    Stream<O> stream();
}
