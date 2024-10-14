package FilterTheSpire.factory;

import FilterTheSpire.utils.config.FilterType;
import FilterTheSpire.utils.types.RunCheckpoint;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class FilterObject {
    public FilterType filterType;
    public List<String> possibleValues;
    // Do we need an exclusion list? Seems like we only need the anyOf list
    public List<String> secondaryValues;
    public RunCheckpoint runCheckpoint;
    public List<Integer> possibleEncounterIndices;
    public HashMap<String, Integer> searchCards;
    public AbstractPlayer.PlayerClass character;
    private transient String _currentHashKey;

    public FilterObject(FilterType filterType) {
        this.filterType = filterType;
        this.possibleValues = new ArrayList<>();
        this.secondaryValues = new ArrayList<>();
        this.possibleEncounterIndices =  new ArrayList<>(Collections.singletonList(0));
        this.searchCards = new HashMap<>();
        updateHashKey();
    }

    public FilterObject(FilterType filterType, List<String> possibleValues) {
        this.filterType = filterType;
        this.possibleValues = possibleValues;
        this.possibleEncounterIndices = new ArrayList<>(Collections.singletonList(0));
    }

    public String getHashKey(){
        String indices = possibleEncounterIndices.stream().map(String::valueOf).collect(Collectors.joining(""));
        return filterType.toString() + indices;
    }

    public boolean isKeyUpdated(){
        if (_currentHashKey == null){
            updateHashKey();
            return false;
        }
        return !this._currentHashKey.equals(getHashKey());
    }

    public String getOldHashKey(){
        return _currentHashKey;
    }

    public void updateHashKey(){
        _currentHashKey = getHashKey();
    }
}
