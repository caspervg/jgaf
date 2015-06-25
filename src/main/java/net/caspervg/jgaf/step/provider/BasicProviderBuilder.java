package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.Organism;
import net.caspervg.jgaf.step.*;

public class BasicProviderBuilder<O extends Organism<O, F>, F extends Number & Comparable> {

    private Creator<O, F> creator;
    private Optimizer<O, F> optimizer;
    private Goal goal;

    public static <O extends Organism<O, F>, F extends Number & Comparable> BasicProviderBuilder<O, F> aProvider() {
        return new BasicProviderBuilder<>();
    }
    
    public BasicProviderBuilder<O, F> withCreator(Creator<O, F> creator) {
        this.creator = creator;
        return this;
    }


    public BasicProviderBuilder<O, F> withOptimizer(Optimizer<O, F> optimizer) {
        this.optimizer = optimizer;
        return this;
    }

    public BasicProviderBuilder<O, F> withGoal(Goal goal) {
        this.goal = goal;
        return this;
    }

    public Provider<O, F> build() {
        return new BasicProvider<>(creator,
                optimizer,
                goal);
    }
}
