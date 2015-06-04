package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;

import java.util.List;

public interface Breeder {

    List<Long> select(Arguments arguments, List<Organism> population);

    List<Organism> breed(Arguments arguments, List<Organism> population, List<Long> selected);

    default List<Organism> breed(Arguments arguments, List<Organism> population) {
        List<Long> selected = select(arguments, population);
        return breed(arguments, population, selected);
    }
}
