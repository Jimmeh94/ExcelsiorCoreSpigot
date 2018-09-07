package ecore.services.user;

import ecore.services.Service;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class ServiceUser extends Service<PlayerInfo> {

    public Optional<PlayerInfo> findPlayerInfo(UUID uuid){
        for(PlayerInfo i: objects){
            if(i.getUUID().compareTo(uuid) == 0){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void remove(Player player){
        remove(player.getUniqueId());
    }

    public void remove(UUID uuid){
        Optional<PlayerInfo> temp = findPlayerInfo(uuid);
        if(temp.isPresent()){
            remove(temp.get());
        }
    }

}
