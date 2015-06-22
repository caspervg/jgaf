package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Fitter;

public class OptimizerFactory<F extends Number & Comparable, O> {

    @SuppressWarnings("unchecked")
    public Optimizer<F, O> newInstance(Object... arguments) {
        if (arguments.length < 1 || !(arguments[0] instanceof Fitter)) {
            throw new IllegalArgumentException("Need at least an instance of Fitter<F, O>");
        }
        if (arguments.length < 2 || !(arguments[1] instanceof Goal)) {
            throw new IllegalArgumentException("Need at least an instance of Goal");
        }

        return new Optimizer<>((Fitter<F, O>) arguments[0], (Goal) arguments[1]);
    }

}
