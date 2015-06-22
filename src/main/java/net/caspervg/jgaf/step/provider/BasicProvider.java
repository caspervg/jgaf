package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.step.*;

public class BasicProvider<F extends Number & Comparable, O> implements Provider<F, O> {

    private Breeder<O> breeder;
    private Creator<O> creator;
    private Fitter<F, O> fitter;
    private Killer<O> killer;
    private Mutator<O> mutator;
    private Selector<O> selector;
    private Optimizer<F, O> optimizer;
    private Goal goal;

    public BasicProvider(Creator<O> creator,
                         Breeder<O> breeder,
                         Mutator<O> mutator,
                         Killer<O> killer,
                         Fitter<F, O> fitter,
                         Selector<O> selector,
                         Optimizer<F, O> optimizer,
                         Goal goal) {
        this.creator = creator;
        this.breeder = breeder;
        this.mutator = mutator;
        this.killer = killer;
        this.fitter = fitter;
        this.selector = selector;
        this.optimizer = optimizer;
        this.goal = goal;
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
    public Fitter<F, O> fitter() {
        return fitter;
    }

    @Override
    public Selector<O> selector() {
        return selector;
    }

    @Override
    public Optimizer<F, O> optimizer() {
        return optimizer;
    }

    @Override
    public Goal goal() {
        return goal;
    }
}
