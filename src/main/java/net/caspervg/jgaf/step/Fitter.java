package net.caspervg.jgaf.step;

public interface Fitter<O> {
    <N extends Number> N calculate(O organism);
}
