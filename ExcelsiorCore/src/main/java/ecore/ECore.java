package ecore;

import ecore.events.InventoryEvents;
import ecore.events.PlayerEvents;
import ecore.runnables.TimerErrorStack;
import ecore.runnables.TimerNode;
import ecore.services.bossbar.ServiceBossBar;
import ecore.services.database.mongo.ServiceMongoDB;
import ecore.services.economy.ServiceEconomy;
import ecore.services.errors.ServiceErrorStack;
import ecore.services.inventory.ServiceInventory;
import ecore.services.messages.ServiceMessager;
import ecore.services.messages.ServiceChannel;
import ecore.services.messages.channels.ChatChannel;
import ecore.services.nodes.ServiceNode;
import ecore.services.particles.ServiceParticles;
import ecore.services.party.ServiceParty;
import ecore.services.scoreboard.ServiceScoreboard;
import ecore.services.user.ServiceUser;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ECore extends JavaPlugin {

    //TODO add option registry of constant time of day event listener and weather too

    public static ECore INSTANCE;

    private Logger logger;
    private ServiceMongoDB mongo;
    private ServiceEconomy economy;
    private ServiceMessager messager;
    private ServiceNode nodes;
    private ServiceParticles particles;
    private ServiceScoreboard scoreboard;
    private ServiceErrorStack errorStack;
    private ServiceUser users;
    private ServiceParty party;
    private ServiceInventory inventory;
    private ServiceChannel channels;
    private ServiceBossBar bossBar;

    @Override
    public void onEnable(){
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
        channels = new ServiceChannel();
        bossBar = new ServiceBossBar();

        mongo = new ServiceMongoDB(getConfig().getString("database-username"), getConfig().getString("database-password"),
                getConfig().getString("database-ip"), getConfig().getString("database-name"));

        nodes.load(mongo);

        registerCommands();
        registerListeners();
        registerRunnables();
    }

    @Override
    public void onDisable(){
        if(mongo != null && mongo.isConnected()){
            mongo.close();
        }
    }

    public void playerQuit(Player player){
        economy.remove(player);
        users.remove(player);
        channels.removePlayerFromAllChannels(player);
        player.setScoreboard(null);
    }

    private void registerRunnables() {
        (new TimerErrorStack()).start();
        (new TimerNode()).start();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

        // ProtocolLib
        //protocolManager.addPacketListener(new ServerToClient());
    }

    private void registerCommands() {

    }

    public ServiceBossBar getBossBar() {
        return bossBar;
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

    public ServiceChannel getChannels() {
        return channels;
    }
}
