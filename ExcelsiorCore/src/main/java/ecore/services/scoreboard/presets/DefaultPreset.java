package ecore.services.scoreboard.presets;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DefaultPreset extends ScoreboardPreset {

    //This should be a class defined in the plugin and not the API

    public DefaultPreset(Player player){
        super(player);
        updateScores();
    }

    @Override
    public void updateScores() {
        /*Player owner = getOwner();
        List<String> strings = new ArrayList<>();

        strings.add(Text.of(TextStyles.BOLD, "Aurelios"));
        strings.add(Text.of("=============="));
        //strings.add(owner.getPresentArea().getDisplayName());
        strings.add(Text.of(TextColors.RED));
        strings.add(Text.of("Bounty: 0"));
        strings.add(Text.of(TextColors.RED, TextColors.AQUA));
        strings.add(Text.of("Gold: " + owner.getAccount().getBalance()));
        strings.add(Text.of(TextColors.RED, TextColors.BLACK));
        strings.add(Text.of("Magic: Fire Dragon Slayer"));
        strings.add(Text.of(TextColors.RED, TextColors.GREEN));
        strings.add(Text.of("Chat: " + owner.getChatChannel().getPrefix().toPlain()));
        strings.add(Text.of(TextColors.RED, TextColors.GOLD));
        strings.add(Text.of("Mana: " + owner.getStats().find(Stat.Type.MANA).get().getAmount()));
        strings.add(Text.of("Time: " + Aurelios.INSTANCE.getCalendar().getCurrentTime()
                .getFormattedTime(false)));
        strings.add(Text.of("Day: " + StringUtils.enumToString(Aurelios.INSTANCE.getCalendar().getCurrentDayOfWeek(), true)));

        if(owner.getCurrentArea() != null) {
            strings.add(Text.of(TextColors.RED, TextColors.DARK_AQUA));
            strings.add(Text.of("Node: " + owner.getCurrentArea().getDisplayName().toPlain()));
        }*/

        //setScores(strings);
    }
}
