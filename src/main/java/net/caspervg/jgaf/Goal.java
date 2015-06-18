package net.caspervg.jgaf;

public interface Goal {

    <F extends Comparable<? super F>> int compare(final F a, final F b);
    Goal opposite();

    /**
     * Minimization of the fitness function
     */
    class Minimum implements Goal {

        private static Goal opposite = new Maximum();

        @Override
        public <F extends Comparable<? super F>> int compare(F a, F b) {
            return a.compareTo(b);
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
            return -a.compareTo(b);
        }

        @Override
        public Goal opposite() {
            return opposite;
        }
    }
}
