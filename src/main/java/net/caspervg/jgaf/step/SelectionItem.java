package net.caspervg.jgaf.step;

abstract class SelectionItem {
    int index;
    double normalizedFitness;
    double accumulatedFitness;

    SelectionItem(int index, double normalizedFitness) {
        this.index = index;
        this.normalizedFitness = normalizedFitness;
        this.accumulatedFitness = -1;
    }

    SelectionItem(SelectionItem old, double accumulatedFitness) {
        this.index = old.index;
        this.normalizedFitness = old.normalizedFitness;
        this.accumulatedFitness = accumulatedFitness;
    }

    SelectionItem(int index, double normalizedFitness, double accumulatedFitness) {
        this.index = index;
        this.normalizedFitness = normalizedFitness;
        this.accumulatedFitness = accumulatedFitness;
    }

    int getIndex() {
        return index;
    }

    double getNormalizedFitness() {
        return normalizedFitness;
    }

    double getAccumulatedFitness() {
        return accumulatedFitness;
    }

}