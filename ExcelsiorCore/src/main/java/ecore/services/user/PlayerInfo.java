package ecore.services.user;

import ecore.services.messages.channels.ChatChannel;
import ecore.services.particles.ServiceParticles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInfo {

    private UUID player;
    private ServiceParticles.ParticleModifier particleModifier;

    //This is the channel to type in, not only listen to
    private ChatChannel currentChatChannel;

    public PlayerInfo(UUID player, ServiceParticles.ParticleModifier modifier) {
        this.player = player;
        this.particleModifier = modifier;
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
