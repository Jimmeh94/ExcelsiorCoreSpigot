package ecore.services.scoreboard;

import ecore.services.scoreboard.presets.DefaultPreset;
import ecore.services.scoreboard.presets.ScoreboardPreset;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.Arrays;

public class Scoreboard {

    private Player owner;
    private ScoreboardPreset preset;

    public Scoreboard(Player player){
        owner = player;

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        owner.getPlayer().setScoreboard(scoreboard);

        preset = new DefaultPreset(owner);
        updatePreset();

        scoreboard.registerNewObjective("side", "dummy", preset.getScore(0)).setDisplaySlot(DisplaySlot.SIDEBAR);

        for(int i = 1; i < preset.getOldSnapshot().size(); i++){
            Score score = scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(preset.getScore(i));
            score.setScore(16 - i);
        }
    }

    private void updatePreset(){ //use this before updating scoreboard
        preset.takeSnapshot();
        preset.updateScores();
    }

    public void setPreset(ScoreboardPreset preset){
        if(preset.getClass() == this.preset.getClass())
            return;
        this.preset.takeSnapshot();

        Objective objective = owner.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        objective.setDisplayName(preset.getScore(0));

        /*for(int i = 0; i < this.preset.getOldSnapshot().size(); i++){
            objective.removeScore(this.preset.getOldSnapshot().get(i));
        }*/

        this.preset = preset;
        updateScoreboard();
    }

    public void unregisterScoreboard(){
        Player owner = this.owner.getPlayer();
        owner.setScoreboard(null);
    }

    public void updateScoreboard(){//sidebar scoreboard
        updatePreset();
        Objective objective = owner.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        objective.setDisplayName(preset.getScore(0));

        //we are to assume that the lines of the snapshot match the lines of the current sAurelioss
        //starting at 1 because 0 is the title
        if(preset.getOldSnapshot().size() == preset.getScores().size()) {

            /*for (int i = 1; i < preset.getOldSnapshot().size(); i++) {
                //For when setting up the scoreboard, if the line is blank or doesn't exist, add it
                if (!objective.hasScore(preset.getOldSnapshot().get(i)) && !objective.hasScore(preset.getScore(i))) {
                    objective.getOrCreateScore(preset.getScore(i)).setScore(16 - i);
                }
                if (!preset.getOldSnapshot().get(i).equals(preset.getScore(i))) {
                    objective.removeScore(preset.getOldSnapshot().get(i));
                    objective.getOrCreateScore(preset.getScore(i)).setScore(16 - i);
                }
            }*/

            //we are to assume that the lines of the snapshot match the lines of the current scores
            //starting at 1 because 0 is the title
            for(int i = 1; i < preset.getOldSnapshot().size(); i++){
                if(!preset.getOldSnapshot().get(i).equals(preset.getScore(i))){
                    objective.getScoreboard().resetScores(this.preset.getOldSnapshot().get(i));
                    objective.getScore(preset.getScore(i)).setScore(16 - i);
                }
            }

        } else {
            for (int i = 1; i < preset.getScores().size(); i++) {
                objective.getScore(preset.getScore(i)).setScore(16 - i);
            }
        }
    }

    public ScoreboardPreset getPreset() {
        return preset;
    }
}
