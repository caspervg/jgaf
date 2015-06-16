package net.caspervg.jgaf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface Population<O> extends Iterable<O> {

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
     * Returns a copy of the backing collection
     *
     * @return Copy of the backing collection
     */
    Collection<O> getAll();

    /**
     * {@inheritDoc}
     *
     * Returns an iterator for the backing collection
     *
     * @return Iterator of the backing collection
     */
    Iterator<O> iterator();

    /**
     * Retrieves the organism at the specific index
     *
     * @param index Index to retrieve organism from
     * @return The organism at the specified index
     * @throws UnsupportedOperationException if the implementation does not support an ordering inside the population
     */
    O get(int index);

    /**
     * Returns the size of the population
     *
     * @return current size of the population
     */
    int size();

    /**
     * Default implementation of a {@link Population} using a List as backing data structure
     *
     * @param <O> Type of the organism
     */
    class Default<O> implements Population<O> {

        private List<O> members;

        /**
         * Creates a new, empty, default {@link Population}.
         */
        public Default() {
            this.members = new ArrayList<>();
        }

        /**
         * Creates a new default {@link Population} with a starting collection of organisms.
         * A copy of the collection will be created.
         *
         * @param os Organisms to use in the new population
         */
        public Default(Collection<? extends O> os) {
            this.members = new ArrayList<>(os);
        }

        /**
         * Copy constructor for a population.
         * A copy of the collection will be created
         *
         * @param population Population to copy
         */
        public Default(Population<O> population) {
            this(population.getAll());
        }

        @Override
        public void add(O o) {
            members.add(o);
        }

        @Override
        public void addAll(Collection<? extends O> os) {
            members.addAll(os);
        }

        @Override
        public Collection<O> getAll() {
            return new ArrayList<>(members);
        }

        @Override
        public Iterator<O> iterator() {
            return getAll().iterator();
        }

        @Override
        public O get(int index) {
            return members.get(index);
        }

        @Override
        public int size() {
            return members.size();
        }
    }
}
