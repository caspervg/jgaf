package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.step.*;

public class BasicStepProvider<O> implements StepProvider<O> {

    private Breeder<O> breeder;
    private Creator<O> creator;
    private Fitter<O> fitter;
    private Killer<O> killer;
    private Mutator<O> mutator;
    private Selector<O> selector;
    private Optimizer<O> optimizer;

    public BasicStepProvider(Creator<O> creator,
                             Breeder<O> breeder,
                             Mutator<O> mutator,
                             Killer<O> killer,
                             Fitter<O> fitter,
                             Selector<O> selector,
                             Optimizer<O> optimizer) {
        this.creator = creator;
        this.breeder = breeder;
        this.mutator = mutator;
        this.killer = killer;
        this.fitter = fitter;
        this.selector = selector;
        this.optimizer = optimizer;
    }

    @Override
    public Creator<O> creator() {
        return creator;
    }

    @Override
    public Breeder<O> breeder() {
        return breeder;
    }

    @Override
    public Mutator<O> mutator() {
        return mutator;
    }

    @Override
    public Killer<O> killer() {
        return killer;
    }

    @Override
    public Fitter<O> fitter() {
        return fitter;
    }

    @Override
    public Selector<O> selector() {
        return selector;
    }

    @Override
    public Optimizer<O> optimizer() {
        return optimizer;
    }
}
