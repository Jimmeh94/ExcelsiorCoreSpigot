package ecore.services.party;

import ecore.events.CustomEvent;
import ecore.events.ServicePartyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Party {

    private List<UUID> members;

    public Party(Player creator) {
        Bukkit.getPluginManager().callEvent(new ServicePartyEvent.ServicePartyPreCreateEvent(CustomEvent.SERVER_CAUSE, creator));

        this.members = new CopyOnWriteArrayList<>();
        members.add(creator.getUniqueId());
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void add(UUID uuid){
        Bukkit.getPluginManager().callEvent(new ServicePartyEvent.ServicePartyPreJoinEvent(CustomEvent.SERVER_CAUSE, Bukkit.getPlayer(uuid)));

        if(!members.contains(uuid)){
            members.add(uuid);
        }
    }

    public void remove(UUID uuid){
        members.remove(uuid);
    }

    public boolean isMember(UUID uuid){
        return members.contains(uuid);
    }
}
