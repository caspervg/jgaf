package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.step.*;

import java.util.List;

public class ProviderBuilder<O> {

    private Breeder<O> breeder;
    private Creator<O> creator;
    private Fitter<O> fitter;
    private Killer<O> killer;
    private Mutator<O> mutator;
    private Selector<O> selector;
    private Optimizer<O> optimizer;
    private Goal goal;

    public static <T> ProviderBuilder<T> aProvider() {
        return new ProviderBuilder<>();
    }

    public ProviderBuilder<O> withBreeder(Breeder<O> breeder) {
        this.breeder = breeder;
        return this;
    }

    public ProviderBuilder<O> withBreederFactory(StepFactory<Breeder<O>, O> factory, List<Object> arguments) {
        if (this.fitter == null) {
            throw new IllegalStateException("Need to have a fitter before creating a breeder");
        }

        arguments.add(0, fitter);
        return withBreeder(factory.newInstance(arguments));
    }

    public ProviderBuilder<O> withCreator(Creator<O> creator) {
        this.creator = creator;
        return this;
    }

    public ProviderBuilder<O> withFitter(Fitter<O> fitter) {
        this.fitter = fitter;
        return this;
    }

    public ProviderBuilder<O> withKiller(Killer<O> killer) {
        this.killer = killer;
        return this;
    }

    public ProviderBuilder<O> withKillerFactory(StepFactory<Breeder<O>, O> factory, List<Object> arguments) {
        return withBreeder(factory.newInstance(arguments));
    }

    public ProviderBuilder<O> withMutator(Mutator<O> mutator) {
        this.mutator = mutator;
        return this;
    }

    public ProviderBuilder<O> withSelector(Selector<O> selector) {
        this.selector = selector;
        return this;
    }

    public ProviderBuilder<O> withSelectorFactory(StepFactory<Selector<O>, O> factory, List<Object> arguments) {
        if (this.fitter == null) {
            throw new IllegalStateException("Need to have a fitter before creating a selector");
        }

        arguments.add(0, fitter);
        return withSelector(factory.newInstance(arguments));
    }

    public ProviderBuilder<O> optimizer(Optimizer<O> optimizer) {
        this.optimizer = optimizer;
        return this;
    }

    public ProviderBuilder<O> withGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public Provider<O> build() {
        return new BasicProvider<>(creator,
                breeder,
                mutator,
                killer,
                fitter,
                selector,
                optimizer,
                goal);
    }
}
