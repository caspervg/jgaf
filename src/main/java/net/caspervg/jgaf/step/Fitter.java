package net.caspervg.jgaf.step;

/**
 * Provides a way to calculate the fitness of an organism
 *
 * @param <F> Type of the fitness. It should implement {@link Comparable}
 * @param <O> Type of the organism
 */
@FunctionalInterface
public interface Fitter<F extends Number & Comparable, O> {

    /**
     * Calculates the fitness of an organism
     *
     * @param organism Organism to calculate organism of
     * @return Fitness of the organism
     */
    F calculate(O organism);
}
