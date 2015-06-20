package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.step.Fitter;
import net.caspervg.jgaf.step.StepFactory;

abstract class AbstractSelectorFactory<O> implements StepFactory<AbstractSelector<O>, O> {
    @Override
    public AbstractSelector<O> newInstance(Object... arguments) {
        if (arguments.length < 1 || !(arguments[0] instanceof Fitter)) {
            throw new IllegalArgumentException("Need at least an instance of Fitter<O>");
        }

        return null;
    }
}
