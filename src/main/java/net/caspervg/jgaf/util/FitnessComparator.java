package net.caspervg.jgaf.util;

import net.caspervg.jgaf.step.Fitter;

import java.util.Comparator;

/**
 * Provides a way to compare organisms by their fitness
 *
 * @param <O> Type of the organism
 */
public class FitnessComparator<O> implements Comparator<O> {

    private Fitter<O> fitter;
    private NumberComparator<Double> comparator;

    private FitnessComparator() {
        // Need a fitter
    }

    /**
     * Creates a new FitnessComparator with given {@link Fitter} to use
     *
     * @param fitter Fitter to use for fitness calculation
     */
    public FitnessComparator(Fitter<O> fitter) {
        this.fitter = fitter;
        this.comparator = new NumberComparator<>();
    }

    /**
     * {@inheritDoc}
     * <p>
     *     Compares two organisms by their calculated fitness
     * </p>
     *
     * @param a First organism to compare
     * @param b Second organism to compare
     * @return Comparison result
     */
    @Override
    public int compare(O a, O b) {
        return comparator.compare(fitter.calculate(a), fitter.calculate(b));
    }

    class NumberComparator<T extends Number & Comparable> implements Comparator<T> {
        public int compare(T a, T b) throws ClassCastException {
            return a.compareTo(b);
        }
    }
}
