package ecore.services.nodes;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Node {

    private Shape shape;
    private UUID uuid;
    private List<UUID> members;
    private String name;
    private List<Node> children;

    public Node(UUID uuid, String name, Shape shape){
        this.uuid = uuid;
        this.name = name;
        this.shape = shape;
        members = new CopyOnWriteArrayList<>();
        children = new ArrayList<>();
    }

    public void tick(){

    }

    public String getName() {
        return name;
    }

    public void enter(UUID uuid){
        if(!members.contains(uuid)){
            members.add(uuid);
        }
    }

    public void leave(UUID uuid){
        members.remove(uuid);
    }

    public boolean isWithin(Location location){
        return shape.isWithin(location);
    }

    public boolean isMember(UUID uuid){
        return members.contains(uuid);
    }

    public Shape getShape() {
        return shape;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public UUID getUuid() {
        return uuid;
    }
}
