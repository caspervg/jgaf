package net.caspervg.jgaf.step.provider;

import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Optimizer;
import net.caspervg.jgaf.Organism;
import net.caspervg.jgaf.step.*;

public class BasicProvider<O extends Organism<O, F>, F extends Number & Comparable> implements Provider<O, F> {

    private Creator<O, F> creator;
    private Optimizer<O, F> optimizer;
    private Goal goal;

    public BasicProvider(Creator<O, F> creator,
                         Optimizer<O, F> optimizer,
                         Goal goal) {
        if (creator == null || optimizer == null || goal == null) {
            throw new IllegalArgumentException("None of the constructor arguments may be null");
        }

        this.creator = creator;
        this.optimizer = optimizer;
        this.goal = goal;
    }

    @Override
    public Creator<O, F> creator() {
        return creator;
    }

    @Override
    public Optimizer<O, F> optimizer() {
        return optimizer;
    }

    @Override
    public Goal goal() {
        return goal;
    }
}
