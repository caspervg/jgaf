package net.caspervg.jgaf;

public interface Goal {

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than the specified object.
     * The implementor must ensure sgn(compare(a, b)) = -sgn(compare(b, a)) and that compare(a, b) == a.compareTo(b)
     * for all a and b.
     * (This implies that compare(a, b) must must throw an exception iff compare(b, a) throws an exception.)
     * The implementor must also ensure that the relation is transitive: (compare(a, b) > 0 && compare(b, c) > 0) implies compare(a, c) > 0.
     * Finally, the implementor must ensure that compare(a, b) == 0 implies that sgn(compare(a, c)) == sgn(compare(b, c)), for all c.
     * It is strongly recommended, but not strictly required that (compare(a, b) == 0) == (a.equals(b)).
     * In the foregoing description, the notation sgn(expression) designates the mathematical signum function,
     * which is defined to return one of -1, 0, or 1 according to whether the value of expression is negative, zero or positive.
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare under this goal
     * @return a negative integer, zero, or a positive integer if the first argument (a) is worse, equal to, or better than
     * the second argument (b) under the current goal.
     */
    <F extends Comparable<? super F>> int compare(final F a, final F b);

    /**
     * Returns the worst of the two parameters under the goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code a} if {@code compare(a, b) <= 0}, else {@code b}
     */
    default <F extends Comparable<? super F>> F worst(final F a, final F b) {
        return compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns the best of the two parameters under the goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code a} if {@code compare(a, b) > 0}, else {@code b}
     */
    default <F extends Comparable<? super F>> F best(final F a, final F b) {
        return compare(a, b) > 0 ? a : b;
    }

    /**
     * Checks if the first argument is strictly better than the second argument under
     * the current goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code true} if {@code a} is strictly better than {@code b} under the current goal
     */
    default <F extends Comparable<? super F>> boolean better(final F a, final F b) {
        return compare(a, b) > 0;
    }

    /**
     * Checks if the first argument is better than (or equal to) the second argument under
     * the current goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code true} if {@code a} is better than (or equal to) {@code b} under the current goal
     */
    default <F extends Comparable<? super F>> boolean betterOrEqual(final F a, final F b) {
        return compare(a, b) >= 0;
    }

    /**
     * Checks if the first argument is strictly worse than the second argument under
     * the current goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code true} if {@code a} is strictly worse than {@code b} under the current goal
     */
    default <F extends Comparable<? super F>> boolean worse(final F a, final F b) {
        return compare(a, b) < 0;
    }

    /**
     * Checks if the first argument is worse than (or equal to) the second argument under
     * the current goal
     *
     * @param a First element to compare
     * @param b Second element to compare
     * @param <F> Type of the elements to compare
     * @return {@code true} if {@code a} is strictly worse than {@code b} under the current goal
     */
    default <F extends Comparable<? super F>> boolean worseOrEqual(final F a, final F b) {
        return compare(a, b) <= 0;
    }

    /**
     * Returns the opposite of the implementing goal
     * @return Opposite of the goal
     */
    Goal opposite();

    /**
     * Minimization of the fitness function
     */
    class Minimum implements Goal {

        private static Goal opposite = new Maximum();

        @Override
        public <F extends Comparable<? super F>> int compare(F a, F b) {
            return -a.compareTo(b);
        }

        @Override
        public Goal opposite() {
            return opposite;
        }
    }

    /**
     * Maximization of the fitness function
     */
    class Maximum implements Goal {

        private static Goal opposite = new Minimum();

        @Override
        public <F extends Comparable<? super F>> int compare(F a, F b) {
            return a.compareTo(b);
        }

        @Override
        public Goal opposite() {
            return opposite;
        }
    }
}
