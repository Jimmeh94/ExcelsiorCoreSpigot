package ecore;

import ecore.services.database.mongo.ServiceMongoDB;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ECore extends JavaPlugin {

    private final Logger logger;
    private final ServiceMongoDB mongo;

    public ECore() {
        logger = getLogger();
        mongo = new ServiceMongoDB();
    }

}
