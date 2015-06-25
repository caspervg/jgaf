package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.step.Fitter;

public class FitnessProportionateSelectorFactory<O> extends AbstractSelectorFactory<O> {

    @SuppressWarnings("unchecked")
    @Override
    public FitnessProportionateSelector<O> newInstance(Object... arguments) {
        super.newInstance(arguments);
        return new FitnessProportionateSelector<>((Fitter<?, O>) arguments[0]);
    }

}
