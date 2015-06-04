package net.caspervg.jgaf.step;

public interface StepProvider {

    Creator creator();
    Breeder breeder();
    Mutator mutator();

}
