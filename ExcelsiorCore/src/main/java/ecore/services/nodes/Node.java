package ecore.services.nodes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
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

    public Collection<Player> getMembers() {
        List<Player> give = new ArrayList<>();
        for(UUID uuid: members){
            Player temp = Bukkit.getPlayer(uuid);
            if(temp != null){
                give.add(temp);
            }
        }
        return give;
    }
}
