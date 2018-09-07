package ecore.services.messages;

import ecore.services.Service;
import ecore.services.messages.channels.ChatChannel;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class ServiceChannel extends Service<ChatChannel> {

    public Optional<ChatChannel> findChannel(String id){
        for(ChatChannel c: objects){
            if(c.getID().equalsIgnoreCase(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public void remove(ChatChannel chatChannel){
        super.remove(chatChannel);

        for(UUID uuid: chatChannel.getMembers()){
            removePlayer(chatChannel, uuid);
        }
    }

    public void removePlayer(ChatChannel chatChannel, Player player){
        removePlayer(chatChannel, player.getUniqueId());
    }

    public void removePlayer(ChatChannel chatChannel, UUID uuid){
        chatChannel.remove(uuid);
        //TODO put this player in the default channel now
    }

    public void join(ChatChannel chatChannel, Player player){
        join(chatChannel, player.getUniqueId());
    }

    public void join(ChatChannel chatChannel, UUID uuid){
        chatChannel.add(uuid);
    }

}
