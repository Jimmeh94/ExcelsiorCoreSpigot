package ecore.services.user;

import ecore.services.Service;

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

}
