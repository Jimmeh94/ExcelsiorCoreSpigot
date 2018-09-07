package ecore;

import ecore.events.InventoryEvents;
import ecore.events.PlayerEvents;
import ecore.services.database.mongo.ServiceMongoDB;
import ecore.services.economy.ServiceEconomy;
import ecore.services.errors.ServiceErrorStack;
import ecore.services.inventory.ServiceInventory;
import ecore.services.location.ServiceLocationUtils;
import ecore.services.messages.ServiceMessager;
import ecore.services.nodes.ServiceNode;
import ecore.services.particles.ServiceParticles;
import ecore.services.party.ServiceParty;
import ecore.services.scoreboard.ServiceScoreboard;
import ecore.services.user.ServiceUser;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ECore extends JavaPlugin {

    //TODO ServiceErrorStack and ServiceNode tick funtions
    //TODO include custom event that fires when new error stack is created for players
    //TODO to print the error to players
    //TODO Unregister scoreboard when player leaves
    //TODO Player leave and join events

    public static ECore INSTANCE;

    private final Logger logger;
    private final ServiceMongoDB mongo;
    private final ServiceEconomy economy;
    private final ServiceMessager messager;
    private final ServiceNode nodes;
    private final ServiceParticles particles;
    private final ServiceScoreboard scoreboard;
    private final ServiceErrorStack errorStack;
    private final ServiceUser users;
    private final ServiceParty party;
    private final ServiceInventory inventory;

    public ECore() {
        INSTANCE = this;
        logger = getLogger();
        economy = new ServiceEconomy();
        messager = new ServiceMessager();
        nodes = new ServiceNode();
        particles = new ServiceParticles();
        scoreboard = new ServiceScoreboard();
        errorStack = new ServiceErrorStack();
        users = new ServiceUser();
        party = new ServiceParty();
        inventory = new ServiceInventory();

        mongo = new ServiceMongoDB(getConfig().getString("database-username"), getConfig().getString("database-password"),
                getConfig().getString("database-ip"), getConfig().getString("database-name"));

        nodes.load(mongo);

        registerCommands();
        registerListeners();
        registerRunnables();
    }

    public void shutdown(){
        if(mongo != null && mongo.isConnected()){
            mongo.close();
        }
    }

    private void registerRunnables() {

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

        // ProtocolLib
        //protocolManager.addPacketListener(new ServerToClient());
    }

    private void registerCommands() {

    }

    public ServiceParty getParty() {
        return party;
    }

    public ServiceInventory getInventory() {
        return inventory;
    }

    public ServiceUser getUsers() {
        return users;
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

    public ServiceErrorStack getErrorStack() {
        return errorStack;
    }
}
