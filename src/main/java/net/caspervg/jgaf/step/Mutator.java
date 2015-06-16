package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides a way to mutate an organism
 *
 * @param <O> Type of the organism
 */
@FunctionalInterface
public interface Mutator<O> {

    /**
     * Mutates a single organism
     *
     * @param arguments Arguments to use for the mutation
     * @param child Child to mutate
     * @return Mutated child
     */
    O mutate(Arguments arguments, O child);

    /**
     * Mutates a list of organisms
     *
     * @param arguments Arguments to use for the mutation
     * @param children Children to mutate
     * @return Mutated organisms
     */
    default List<O> mutate(Arguments arguments, List<O> children) {
        return children.stream().map(child -> mutate(arguments, child)).collect(Collectors.toList());
    }
}
