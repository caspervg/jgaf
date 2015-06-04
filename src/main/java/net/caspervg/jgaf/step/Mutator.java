package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;

import java.util.List;
import java.util.stream.Collectors;

public interface Mutator {
    <O extends Organism> O mutate(Arguments arguments, O child);

    default <O extends Organism> List<O> mutate(Arguments arguments, List<O> children) {
        return children.stream().map(child -> mutate(arguments, child)).collect(Collectors.toList());
    }
}
