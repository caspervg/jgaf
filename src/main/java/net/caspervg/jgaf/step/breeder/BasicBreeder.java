package net.caspervg.jgaf.step.breeder;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Breeder;
import net.caspervg.jgaf.step.Crosser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation of a {@link Breeder}
 *
 * @param <O> Type of the organism
 */
public class BasicBreeder<O> implements Breeder<O> {

    private Crosser<O> crosser;

    private BasicBreeder() {
        // We need a crosser
    }

    /**
     * Creates a new default Breeder that will use given Crosser and Fitter in the breeding process
     *
     * @param crosser Crosser to use for breeding
     */
    public BasicBreeder(Crosser<O> crosser) {
        this.crosser = crosser;
    }
    /**
     * {@inheritDoc}
     * <p>
     *     This implementation loops over the selections ("parents") and breeds pairs
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
