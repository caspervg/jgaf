package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;

import java.util.List;
import java.util.stream.Collectors;

public interface Mutator {
    Organism mutate(Arguments arguments, Organism child);

    default List<Organism> mutate(Arguments arguments, List<Organism> children) {
        return children.stream().map(child -> mutate(arguments, child)).collect(Collectors.toList());
    }
}
