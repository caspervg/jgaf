package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.step.Fitter;

public class TournamentSelectorFactory<O> extends AbstractSelectorFactory<O> {

    @SuppressWarnings("unchecked")
    @Override
    public TournamentSelector<O> newInstance(Object... arguments) {
        super.newInstance(arguments);

        int tournamentSize = 5;
        if (arguments.length > 1 && arguments[1] instanceof Integer) {
            tournamentSize = (int) arguments[1];
        }

        return new TournamentSelector<>((Fitter<?, O>) arguments[0], tournamentSize);
    }

}
