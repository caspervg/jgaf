package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.OptimizerFactory;
import net.caspervg.jgaf.step.*;

import java.util.ArrayList;
import java.util.List;

public class ProviderBuilder<F extends Number & Comparable, O> {

    private Breeder<O> breeder;
    private Creator<O> creator;
    private Crosser<O> crosser;
    private Fitter<F, O> fitter;
    private Killer<O> killer;
    private Mutator<O> mutator;
    private Selector<O> selector;
    private Optimizer<F, O> optimizer;
    private Goal goal;

    public static <F extends Number & Comparable, O> ProviderBuilder<F, O> aProvider() {
        return new ProviderBuilder<>();
    }

    public ProviderBuilder<F, O> withBreeder(Breeder<O> breeder) {
        this.breeder = breeder;
        return this;
    }

    public ProviderBuilder<F, O> withBreederFactory(StepFactory<? extends Breeder<O>, O> factory, List<Object> parameters) {
        if (this.crosser == null) {
            throw new IllegalStateException("Need to have a crosser before creating a breeder");
        }

        parameters.add(0, crosser);
        return withBreeder(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withBreederFactory(StepFactory<? extends Breeder<O>, O> factory) {
        return withBreederFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withCrosser(Crosser<O> crosser) {
        this.crosser = crosser;
        return this;
    }

    public ProviderBuilder<F, O> withCrosserFactory(StepFactory<? extends Crosser<O>, O> factory, List<Object> parameters) {
        if (this.fitter == null) {
            throw new IllegalStateException("Need to have a fitter before creating a crosser");
        }

        parameters.add(0, fitter);
        return withCrosser(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withCrosserFactory(StepFactory<? extends Crosser<O>, O> factory) {
        return withCrosserFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withCreator(Creator<O> creator) {
        this.creator = creator;
        return this;
    }

    public ProviderBuilder<F, O> withCreatorFactory(StepFactory<? extends Creator<O>, O> factory, List<Object> parameters) {
        return withCreator(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withCreatorFactory(StepFactory<? extends Creator<O>, O> factory) {
        return withCreatorFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withFitter(Fitter<F, O> fitter) {
        this.fitter = fitter;
        return this;
    }

    public ProviderBuilder<F, O> withFitterFactory(StepFactory<? extends Fitter<F, O>, O> factory, List<Object> parameters) {
        return withFitter(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withFitterFactory(StepFactory<? extends Fitter<F, O>, O> factory) {
        return withFitterFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withKiller(Killer<O> killer) {
        this.killer = killer;
        return this;
    }

    public ProviderBuilder<F, O> withKillerFactory(StepFactory<? extends Killer<O>, O> factory, List<Object> parameters) {
        return withKiller(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withKillerFactory(StepFactory<? extends Killer<O>, O> factory) {
        return withKillerFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withMutator(Mutator<O> mutator) {
        this.mutator = mutator;
        return this;
    }

    public ProviderBuilder<F, O> withMutatorFactory(StepFactory<? extends Mutator<O>, O> factory, List<Object> parameters) {
        return withMutator(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withMutatorFactory(StepFactory<? extends Mutator<O>, O> factory) {
        return withMutatorFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withSelector(Selector<O> selector) {
        this.selector = selector;
        return this;
    }

    public ProviderBuilder<F, O> withSelectorFactory(StepFactory<? extends Selector<O>, O> factory, List<Object> parameters) {
        if (this.fitter == null) {
            throw new IllegalStateException("Need to have a fitter before creating a selector");
        }

        parameters.add(0, fitter);
        return withSelector(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withSelectorFactory(StepFactory<? extends Selector<O>, O> factory) {
        return withSelectorFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withOptimizer(Optimizer<F, O> optimizer) {
        this.optimizer = optimizer;
        return this;
    }

    public ProviderBuilder<F, O> withOptimizerFactory(OptimizerFactory<F, O> factory, List<Object> parameters) {
        if (this.goal == null) {
            throw new IllegalStateException("Need to have a goal before creating an optimizer");
        }
        if (this.fitter == null) {
            throw new IllegalStateException("Need to have a fitter before creating an optimizer");
        }

        parameters.add(0, fitter);
        parameters.add(1, goal);
        return withOptimizer(factory.newInstance(parameters.toArray()));
    }

    public ProviderBuilder<F, O> withOptimizerFactory(OptimizerFactory<F, O> factory) {
        return withOptimizerFactory(factory, new ArrayList<>());
    }

    public ProviderBuilder<F, O> withGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public Provider<F, O> build() {
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
