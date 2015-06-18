package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;

import java.util.*;

/**
 * Provides a way to breed (a part of) the population based on given arguments
 *
 * @param <O> Type of the organism
 */
public interface Breeder<O> {

    /**
     * Breeds the selected individuals
     *
     * @param arguments Arguments to use
     * @param population Population to breed from
     * @param parents Organisms to function as parents in the breeding process
     * @return Collection of children that were procreated
     */
    Collection<O> breed(Arguments arguments, Population<O> population, Collection<O> parents);

    /**
     * Default implementation of a {@link Breeder}
     *
     * @param <O> Type of the organism
     */
    class Default<O> implements Breeder<O> {

        private Crosser<O> crosser;

        private Default() {
            // We need a crosser
        }

        /**
         * Creates a new default Breeder that will use given Crosser and Fitter in the breeding process
         *
         * @param crosser Crosser to use for breeding
         */
        public Default(Crosser<O> crosser) {
            this.crosser = crosser;
        }

        /**
         * {@inheritDoc}
         * <p>
         *     This implementation loops over the selection list and breeds pairs
         *     of organisms from it. For breeding, it uses the {@link Crosser} that was
         *     supplied in the constructor.
         * </p>
         *
         * @param arguments {@inheritDoc}
         * @param population {@inheritDoc}
         * @param parents {@inheritDoc}
         * @return {@inheritDoc}
         */
        @Override
        public Collection<O> breed(Arguments arguments, Population<O> population, Collection<O> parents) {
            List<O> bred = new ArrayList<>();
            List<O> breedables = new ArrayList<>(parents);

            for (int i = 0; i < arguments.breedingPoolSize(); i+=2) {
                O father = breedables.get(i);
                O mother = breedables.get(i+1);
                List<O> currentParents = new ArrayList<>();
                currentParents.add(father);
                currentParents.add(mother);

                List<O> children = crosser.cross(currentParents);
                bred.addAll(children);
            }

            return bred;
        }

    }

}
