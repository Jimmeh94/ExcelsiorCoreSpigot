package ecore.services.nodes;

import ecore.services.database.mongo.ServiceMongoDB;
import ecore.services.Service;
import org.bukkit.Location;

import java.util.Optional;
import java.util.UUID;

public class ServiceNode extends Service<Node> {

    private final NodeWilderness wilderness = new NodeWilderness("Wilderness");

    public void load(ServiceMongoDB mongo){
        mongo.loadData(ServiceMongoDB.COLLECTION_NODES);
    }

    public Optional<Node> findNode(Location location){
        Optional<Node> give = Optional.empty();
        for(Node node: objects){
            if(node.isWithin(location)){
                give = Optional.of(node);
                break;
            }
        }

        if(!give.isPresent()){
            give = Optional.of(wilderness);
        }
        return give;
    }

    public void tick() {
        for(Node node: objects){
            node.tick();
        }
    }

    public Optional<Node> findNode(UUID uuid) {
        for(Node node: objects){
            if(node.getUuid().compareTo(uuid) == 0){
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }
}
