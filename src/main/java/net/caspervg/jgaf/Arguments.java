package net.caspervg.jgaf;

public interface Arguments {

    default int populationSize() {
        return 1000;
    }

    default int numIterations() {
        return 1000;
    }

    default double maximumMutationAmount() {
        return 0.1D;
    }

    default int breedingPoolSize() {
        return populationSize() / 10;
    }

    default int tradingPoolSize() {
        return breedingPoolSize() / 2;
    }

    default int killingPoolSize() {
        return breedingPoolSize();
    }

    class Default implements Arguments {
    }
}
