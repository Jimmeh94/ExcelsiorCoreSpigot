package ecore.services.nodes;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class NodeWilderness extends Node {

    public NodeWilderness(String name) {
        super(null, name, null);
    }

    @Override
    public boolean isWithin(Location location) {
        return true;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public void addChild(Node child) {

    }

    @Override
    public UUID getUuid() {
        return null;
    }
}
