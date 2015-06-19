package net.caspervg.jgaf.step.selector;

class SelectionItem implements Comparable<SelectionItem> {
    int index;
    double normalizedFitness;
    double accumulatedFitness;

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

    @Override
    public int compareTo(SelectionItem o) {
        return Double.compare(getNormalizedFitness(), o.getNormalizedFitness());
    }
}