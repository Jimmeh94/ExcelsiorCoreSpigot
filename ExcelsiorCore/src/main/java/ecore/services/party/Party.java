package ecore.services.party;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Party {

    private List<UUID> members;

    public Party() {
        this.members = new CopyOnWriteArrayList<>();
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void add(UUID uuid){
        if(!members.contains(uuid)){
            members.add(uuid);
        }
    }

    public void remove(UUID uuid){
        members.remove(uuid);
    }

    public boolean isMember(UUID uuid){
        return members.contains(uuid);
    }
}
