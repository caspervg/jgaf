package net.caspervg.jgaf.step.fitter;

import net.caspervg.jgaf.step.Fitter;

@FunctionalInterface
public interface BasicIntFitter<O> extends Fitter<Integer, O> {
    /**
     * Calculates the fitness of an organism
     *
     * @param organism Organism to calculate organism of
     * @return Fitness of the organism
     */
    Integer calculate(O organism);

}
