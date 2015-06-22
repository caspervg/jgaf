package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Fitter;

import java.util.Comparator;

public class Optimizer<F extends Number & Comparable, O> implements Comparator<O> {

    private Fitter<F, O> fitter;
    private Goal goal;

    private Optimizer() {
        // We need a fitter and a goal
    }

    public Optimizer(Fitter<F, O> fitter, Goal goal) {
        this.fitter = fitter;
        this.goal = goal;
    }

    @Override
    public int compare(final O org1, final O org2) {
        F fitness1 = fitter.calculate(org1);
        F fitness2 = fitter.calculate(org2);
        return goal.compare(fitness1, fitness2);
    }

}
