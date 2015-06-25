package net.caspervg.jgaf;

import java.util.Comparator;

public class Optimizer<O extends Organism<O, F>, F extends Number & Comparable> implements Comparator<O> {

    private Goal goal;

    private Optimizer() {
        // We need a goal
    }

    public Optimizer(Goal goal) {
        this.goal = goal;
    }

    @Override
    public int compare(final O org1, final O org2) {
        F fitness1 = org1.fitness();
        F fitness2 = org2.fitness();
        return goal.compare(fitness1, fitness2);
    }

}
