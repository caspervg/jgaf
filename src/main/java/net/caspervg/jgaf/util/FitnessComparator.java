package net.caspervg.jgaf.util;

import net.caspervg.jgaf.step.Fitter;

import java.util.Comparator;

public class FitnessComparator<O> implements Comparator<O> {

    private Fitter<O> fitter;

    private FitnessComparator() {
        // Need a fitter
    }

    public FitnessComparator(Fitter<O> fitter) {
        this.fitter = fitter;
    }

    @Override
    public int compare(O a, O b) {
        return new NumberComparator<>().compare(fitter.calculate(a), fitter.calculate(b));
    }

    class NumberComparator<T extends Number & Comparable> implements Comparator<T> {
        public int compare(T a, T b) throws ClassCastException {
            return a.compareTo( b );
        }
    }
}
