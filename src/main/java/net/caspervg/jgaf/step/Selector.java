package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Organism;
import net.caspervg.jgaf.Population;

import java.util.Collection;

@FunctionalInterface
public interface Selector<O extends Organism> {

    /**
     * Selects the organisms in a population that should be killed or bred
     *
     * @param arguments Arguments to use
     * @param population Population to select from
     * @param goal Goal for the selection (maximisation or minimisation?)
     * @return Collection of organisms that were selected
     */
    Collection<O> select(Arguments arguments, Population<O> population, Goal goal);

}
