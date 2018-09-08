package ecore.services.messages.channels;

import ecore.ECore;
import ecore.services.messages.ServiceMessager;
import ecore.services.user.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ChatChannel {

    private List<UUID> members;
    private String welcomeMessage;
    private String name;

    //The ID should be a final constant
    //public static final String GLOBAL = "GLOBAL";
    private String ID;

    public ChatChannel(String welcomeMessage, String name, String ID) {
        this.welcomeMessage = welcomeMessage;
        this.name = name;
        this.ID = ID;

        members = new CopyOnWriteArrayList<>();

        ECore.INSTANCE.getChannels().add(this);
    }

    public String getID() {
        return ID;
    }

    public boolean isMember(UUID uuid){
        return members.contains(uuid);
    }

    public boolean isMember(PlayerInfo playerInfo){
        return isMember(playerInfo.getUUID());
    }

    public void remove(PlayerInfo playerInfo){
        remove(playerInfo.getUUID());
    }

    public void remove(UUID uuid){
        members.remove(uuid);
    }

    public void add(UUID uuid){
        if(!members.contains(uuid)){
            members.add(uuid);
            ECore.INSTANCE.getMessager().sendMessage(Bukkit.getPlayer(uuid), welcomeMessage, Optional.of(ServiceMessager.Prefix.CHAT));
            PlayerInfo playerInfo = ECore.INSTANCE.getUsers().findPlayerInfo(uuid).get();
            if(playerInfo.getCurrentChatChannel() == null){
                playerInfo.setCurrentChatChannel(this);
            }
        }
    }

    public void sendMessage(String message){
        String prefix = ChatColor.GRAY + "[" + name + ChatColor.GRAY + "] " + ChatColor.GRAY;
        ECore.INSTANCE.getMessager().sendMessageToChannel(this, prefix + message, Optional.empty());
    }

    public List<UUID> getMembers() {
        return members;
    }
}
