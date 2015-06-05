package net.caspervg.jgaf.step;

public interface StepProvider<O> {
    Creator<O> creator();
    Breeder<O> breeder();
    Mutator<O> mutator();
    Killer<O> killer();
    Fitter<O> fitter();
}
