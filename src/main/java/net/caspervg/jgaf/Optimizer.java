package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Fitter;

import java.util.Comparator;

public class Optimizer<O> implements Comparator<O> {

    private Fitter<O> fitter;
    private Goal goal;

    private Optimizer() {
        // We need a fitter and a goal
    }

    public Optimizer(Fitter<O> fitter, Goal goal) {
        this.fitter = fitter;
        this.goal = goal;
    }

    @Override
    public int compare(final O org1, final O org2) {
        double fitness1 = fitter.calculate(org1);
        double fitness2 = fitter.calculate(org2);
        return goal.compare(fitness1, fitness2);
    }

}
