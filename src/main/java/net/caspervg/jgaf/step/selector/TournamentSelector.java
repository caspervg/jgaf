package net.caspervg.jgaf.step.selector;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Goal;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Fitter;

import java.util.*;

public class TournamentSelector<O> extends AbstractSelector<O> {

    private int tournamentSize;

    public TournamentSelector(Fitter<?, O> fitter, int tournamentSize) {
        super(fitter);
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Collection<O> select(Arguments arguments, Population<O> population, Goal goal) {
        List<O> organisms = new ArrayList<>(population.getAll());

        List<O> selected = new ArrayList<>(arguments.breedingPoolSize());
        for (int i = 0; i < arguments.breedingPoolSize(); i++) {
            int tournamentWinnerIndex = doTournament(organisms, goal);
            selected.add(organisms.get(tournamentWinnerIndex));
            organisms.remove(tournamentWinnerIndex);    // No double selections
        }

        return selected;
    }

    private int doTournament(List<O> organisms, Goal goal) {
        Set<Integer> participantIndices = new HashSet<>();

        while (participantIndices.size() < tournamentSize) {
            int rand = random.nextInt(organisms.size());
            participantIndices.add(rand);
        }

        double bestParticipantFitness = goal.worst(Double.MIN_VALUE, Double.MAX_VALUE); // Start with the worst possible value as a temporary best fitness
        int bestParticipantIndex = -1;
        for (Integer index : participantIndices) {
            O participant = organisms.get(index);
            double participantFitness = fitter.calculate(participant).doubleValue();
            if (goal.better(participantFitness, bestParticipantFitness)) {
                bestParticipantFitness = participantFitness;
                bestParticipantIndex = index;
            }
        }

        return bestParticipantIndex;
    }
}
