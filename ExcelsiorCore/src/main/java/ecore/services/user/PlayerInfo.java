package ecore.services.user;

import ecore.services.messages.channels.ChatChannel;
import ecore.services.particles.ServiceParticles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfo {

    private UUID player;
    private ServiceParticles.ParticleModifier particleModifier;
    private List<String> permissions;

    //This is the channel to type in, not only listen to
    private ChatChannel currentChatChannel;

    public PlayerInfo(UUID player, ServiceParticles.ParticleModifier modifier) {
        this.player = player;
        this.particleModifier = modifier;
        permissions = new ArrayList<>();
    }

    public void addPermission(String permission){
        if(!permissions.contains(permission)){
            permissions.add(permission);
        }
    }

    public boolean hasPermission(String permission){
        return permissions.contains(permission);
    }

    public void removePerimission(String permission){
        permissions.remove(permission);
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public UUID getUUID() {
        return player;
    }

    public ServiceParticles.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(player);
    }

    public ChatChannel getCurrentChatChannel() {
        return currentChatChannel;
    }

    public void setCurrentChatChannel(ChatChannel currentChatChannel) {
        this.currentChatChannel = currentChatChannel;
    }
}
