package ecore.events;

import ecore.ECore;
import ecore.services.errors.ErrorStackEntry;
import ecore.services.messages.ServiceMessager;
import ecore.services.user.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);

        Optional<PlayerInfo> temp = ECore.INSTANCE.getUsers().findPlayerInfo(event.getPlayer().getUniqueId());
        if(temp.isPresent()){
            temp.get().getCurrentChatChannel().sendMessage(event.getMessage());
        } else {
            ECore.INSTANCE.getErrorStack().add(new ErrorStackEntry(ChatColor.RED + "Your player info wasn't found when trying to send chat. Let staff know.",
                    event.getPlayer().getUniqueId()));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        ECore.INSTANCE.playerQuit(event.getPlayer());
    }

}
