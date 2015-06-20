package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.step.*;

public class StepProviderBuilder<O> {

    private Breeder<O> breeder;
    private Creator<O> creator;
    private Fitter<O> fitter;
    private Killer<O> killer;
    private Mutator<O> mutator;
    private Selector<O> selector;
    private Optimizer<O> optimizer;

    public StepProviderBuilder<O> withBreeder(Breeder<O> breeder) {
        this.breeder = breeder;
        return this;
    }

    public StepProviderBuilder<O> withCreator(Creator<O> creator) {
        this.creator = creator;
        return this;
    }

    public StepProviderBuilder<O> withFitter(Fitter<O> fitter) {
        this.fitter = fitter;
        return this;
    }

    public StepProviderBuilder<O> withKiller(Killer<O> killer) {
        this.killer = killer;
        return this;
    }

    public StepProviderBuilder<O> withMutator(Mutator<O> mutator) {
        this.mutator = mutator;
        return this;
    }

    public StepProviderBuilder<O> withSelector(Selector<O> selector) {
        this.selector = selector;
        return this;
    }

    public StepProviderBuilder<O> optimizer(Optimizer<O> optimizer) {
        this.optimizer = optimizer;
        return this;
    }

    public StepProvider build() {
        return new BasicStepProvider<>(creator,
                breeder,
                mutator,
                killer,
                fitter,
                selector,
                optimizer);
    }
}
