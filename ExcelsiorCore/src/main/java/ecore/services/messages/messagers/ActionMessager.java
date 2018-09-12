package ecore.services.messages.messagers;

import net.minecraft.server.v1_13_R2.ChatMessageType;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionMessager {

    public static void send(Player player, String text){
        if(player != null && text != null) {
            String jsonRepresentation = "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', text) + "\"}";
            IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(jsonRepresentation);
            PacketPlayOutChat actionPacket = new PacketPlayOutChat(chatBaseComponent, ChatMessageType.CHAT);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(actionPacket);
        }
    }
}
