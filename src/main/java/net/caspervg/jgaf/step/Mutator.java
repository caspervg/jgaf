package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;

import java.util.List;
import java.util.stream.Collectors;

public interface Mutator<O> {
    O mutate(Arguments arguments, O child);

    default List<O> mutate(Arguments arguments, List<O> children) {
        return children.stream().map(child -> mutate(arguments, child)).collect(Collectors.toList());
    }
}
