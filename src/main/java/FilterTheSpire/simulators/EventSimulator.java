package FilterTheSpire.simulators;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

/***
 * This should not really be used, there are too many factors in events
 * such as current gold, current deck, and current health that we can't necessarily predict
 */
public class EventSimulator {
    private static EventSimulator singleton = null;

    public static EventSimulator getInstance(){
        if (singleton == null){
            singleton = new EventSimulator();
        }
        return singleton;
    }

    private EventSimulator(){
    }


    public String generateEvent(Random rng, int encounterIndex) {
        assert encounterIndex <= 3; // At most we're doing 3 event checks, no way we should get much further
        // right now this only works for first event (since we don't use encounterIndex)

        final float shrineChance = 0.25F;
        if (rng.random(1.0F) < shrineChance) {
            return getShrine(rng);
        } else {
            String retVal = getEvent(rng);
            return retVal == null ? getShrine(rng) : retVal;
        }
    }

    private String getEvent(Random rng) {
        ArrayList<String> eventList = getEventList();
        int randomInt = rng.random(eventList.size() - 1);
        if (randomInt >= 0 && randomInt < eventList.size()){
            String tmpKey = eventList.get(randomInt);
            eventList.remove(tmpKey);
            return tmpKey;
        }
        return null;
    }

    private String getShrine(Random rng) {
        ArrayList<String> shrineList = getShrineList();
        ArrayList<String> specialOneTimeEventList = getSpecialOneTimeEventList();
        ArrayList<String> shrinesAndOneTimeEvents = new ArrayList<>(shrineList);
        shrinesAndOneTimeEvents.addAll(specialOneTimeEventList);

        int randomInt = rng.random(shrinesAndOneTimeEvents.size() - 1);
        if (randomInt >= 0 && randomInt < shrinesAndOneTimeEvents.size()) {
            String tmpKey = shrinesAndOneTimeEvents.get(randomInt);
            shrineList.remove(tmpKey);
            specialOneTimeEventList.remove(tmpKey);
            return tmpKey;
        }
        return null;
    }

    private ArrayList<String> getSpecialOneTimeEventList() {
        ArrayList<String> specialOneTimeEventList = new ArrayList<>();
        specialOneTimeEventList.add("Accursed Blacksmith");
        specialOneTimeEventList.add("Bonfire Elementals");
//        specialOneTimeEventList.add("Designer"); // Act 2 or 3
//        specialOneTimeEventList.add("Duplicator"); // Act 2 or 3
        specialOneTimeEventList.add("FaceTrader");
//        specialOneTimeEventList.add("Fountain of Cleansing"); // Assuming floor 1
//        specialOneTimeEventList.add("Knowing Skull"); // Act 2
        specialOneTimeEventList.add("Lab");
//        specialOneTimeEventList.add("N'loth"); // Act 2
        if (this.isNoteForYourselfAvailable()) {
            specialOneTimeEventList.add("NoteForYourself");
        }

//        specialOneTimeEventList.add("SecretPortal"); // Act 3
//        specialOneTimeEventList.add("The Joust"); // Act 2
        specialOneTimeEventList.add("WeMeetAgain");
        specialOneTimeEventList.add("The Woman in Blue");
        return specialOneTimeEventList;
    }

    private boolean isNoteForYourselfAvailable() {
        if (Settings.isDailyRun) {
            return false;
        } else if (AbstractDungeon.ascensionLevel >= 15) {
            return false;
        } else if (AbstractDungeon.ascensionLevel == 0) {
            return true;
        } else {
            return AbstractDungeon.ascensionLevel < AbstractDungeon.player.getPrefs().getInteger("ASCENSION_LEVEL");
        }
    }

    private ArrayList<String> getEventList() {
        ArrayList<String> eventList = new ArrayList<>();
        eventList.add("Big Fish");
        eventList.add("The Cleric");
        eventList.add("Dead Adventurer");
        eventList.add("Golden Idol");
        eventList.add("Golden Wing");
        eventList.add("World of Goop");
        eventList.add("Liars Game");
        eventList.add("Living Wall");
        eventList.add("Mushrooms");
        eventList.add("Scrap Ooze");
        eventList.add("Shining Light");
        return eventList;
    }

    private ArrayList<String> getShrineList() {
        ArrayList<String> shrineList = new ArrayList<>();
        shrineList.add("Match and Keep!");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
        shrineList.add("Wheel of Change");
        return shrineList;
    }
}
