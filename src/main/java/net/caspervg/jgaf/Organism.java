package net.caspervg.jgaf;


import org.jetbrains.annotations.NotNull;

public interface Organism<F extends Number & Comparable<F>> {

    F fitness();

    default int compare(@NotNull Organism<F> other) {
        return this.fitness().compareTo(other.fitness());
    }
}
