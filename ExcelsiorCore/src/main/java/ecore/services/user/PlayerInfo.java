package ecore.services.user;

import ecore.services.particles.ServiceParticles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInfo {

    private UUID player;
    private ServiceParticles.ParticleModifier particleModifier;

    public UUID getUUID() {
        return player;
    }

    public ServiceParticles.ParticleModifier getParticleModifier() {
        return particleModifier;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(player);
    }
}
