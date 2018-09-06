package ecore;

import ecore.services.database.mongo.ServiceMongoDB;
import ecore.services.economy.ServiceEconomy;
import ecore.services.messages.ServiceMessager;
import ecore.services.nodes.ServiceNode;
import ecore.services.particles.ServiceParticles;
import ecore.services.scoreboard.ServiceScoreboard;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ECore extends JavaPlugin {

    private final Logger logger;
    private final ServiceMongoDB mongo;
    private final ServiceEconomy economy;
    private final ServiceMessager messager;
    private final ServiceNode nodes;
    private final ServiceParticles particles;
    private final ServiceScoreboard scoreboard;

    public ECore() {
        logger = getLogger();
        economy = new ServiceEconomy();
        messager = new ServiceMessager();
        nodes = new ServiceNode();
        particles = new ServiceParticles();
        scoreboard = new ServiceScoreboard();

        mongo = new ServiceMongoDB(getConfig().getString("database-username"), getConfig().getString("database-password"),
                getConfig().getString("database-ip"), getConfig().getString("database-name"));
    }

    public void shutdown(){
        if(mongo != null && mongo.isConnected()){
            mongo.close();
        }
    }

    public ServiceMongoDB getMongo() {
        return mongo;
    }

    public ServiceEconomy getEconomy() {
        return economy;
    }

    public ServiceMessager getMessager() {
        return messager;
    }

    public ServiceNode getNodes() {
        return nodes;
    }

    public ServiceParticles getParticles() {
        return particles;
    }

    public ServiceScoreboard getScoreboard() {
        return scoreboard;
    }
}
