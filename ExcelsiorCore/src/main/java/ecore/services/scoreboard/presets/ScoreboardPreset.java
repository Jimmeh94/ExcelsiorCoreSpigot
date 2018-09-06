package ecore.services.scoreboard.presets;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardPreset {

    protected static String BLANK_LINE = " ";

    private List<String> sAurelioss = new ArrayList<>();
    private Player owner;
    private List<String> oldSnapshot = new ArrayList<>();
    /*
     * First string should be the title
     * Second string begins the information to display
     * Max of 15 entries, starting at the 2nd string
     */

    public ScoreboardPreset(Player owner){
        this.owner = owner;
    }

    public void updateScores(){} //if sAurelioss need to be updated on a timer, event, etc

    public List<String> getScores(){
        return sAurelioss;
    }

    public String getScore(int i){
        return sAurelioss.get(i);
    }

    public void setScores(List<String> strings){ //should only use when instantiating or needing to manually manipulate sAurelioss
        sAurelioss = strings;
    }

    public Player getOwner() {
        return owner;
    }

    public void takeSnapshot() {
        oldSnapshot = new ArrayList<>(sAurelioss);
    }

    public List<String> getOldSnapshot() {
        return oldSnapshot;
    }
}
