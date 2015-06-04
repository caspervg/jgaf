package net.caspervg.jgaf;


import org.jetbrains.annotations.NotNull;

public interface Organism<F extends Comparable<F>> {

    F fitness();

    default int selectBreed(@NotNull Organism<F> other) {
        return this.fitness().compareTo(other.fitness());
    }

    default int selectKill(@NotNull Organism<F> other) {
        return -selectBreed(other);
    }
}
