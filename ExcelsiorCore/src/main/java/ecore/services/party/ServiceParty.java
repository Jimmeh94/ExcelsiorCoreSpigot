package ecore.services.party;

import ecore.services.Service;

import java.util.Optional;
import java.util.UUID;

public class ServiceParty extends Service<Party> {

    public Optional<Party> findParty(UUID uuid){
        for(Party p: objects){
            if(p.isMember(uuid)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}
