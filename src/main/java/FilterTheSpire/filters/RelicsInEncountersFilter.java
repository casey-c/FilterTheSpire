package FilterTheSpire.filters;

import FilterTheSpire.simulators.RelicRngSimulator;

import java.util.List;

public class RelicsInEncountersFilter extends AbstractFilter {
    private final List<String> relicNames;
    private final List<Integer> encounterIndices;

    public RelicsInEncountersFilter(List<String> relicNames, List<Integer> encounterIndices) {
        this.relicNames = relicNames;
        this.encounterIndices = encounterIndices;
    }

    public boolean isSeedValid(long seed) {
        return RelicRngSimulator.getInstance().areNRelicsFoundInXEncounters(this.relicNames, seed, this.encounterIndices);
    }

    public String generateHashKey() {
        return null;
    }
}
