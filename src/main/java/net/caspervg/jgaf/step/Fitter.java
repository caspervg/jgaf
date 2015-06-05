package net.caspervg.jgaf.step;

/**
 * Provides a way to calculate the fitness of an organism
 *
 * @param <O> Type of the organism
 */
public interface Fitter<O> {

    /**
     * Calculates the fitness of an organism
     *
     * @param organism Organism to calculate organism of
     * @param <N> Type of the {@link Number} to return
     * @return Fitness of the organism
     */
    <N extends Number> N calculate(O organism);
}
