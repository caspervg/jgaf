package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Population;

import java.util.Collection;

public interface Selector<O> extends Step<O> {

    /**
     * Selects the organisms in a population that should be killed or bred
     *
     * @param arguments Arguments to use
     * @param population Population to select from
     * @param goal Goal of the selection
     * @return Collection of organisms that were selected
     */
    Collection<O> select(Arguments arguments, Population<O> population, Goal goal);

}
