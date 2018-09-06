package ecore.services.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class JsonMessager {

    //Action Command can be: run_command, open_url, suggest_command
    //action value is the command/page to be ran/opened

    public enum ActionCommand{
        COMMAND("run_command"),
        URL("open_url"),
        SUGGEST_COMMAND("suggest_command");

        private String string;

        ActionCommand(String s){this.string = s;}
    }

    public PacketPlayOutChat getChatPacket(String message, ActionCommand actionCommand, String actionValue, String hoverText){
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(generateJsonString(message, actionCommand.string, actionValue, hoverText));
        return new PacketPlayOutChat(component);
    }

    public void sendMessage(Player player, PacketPlayOutChat packet){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendMessage(Player player, String message, ActionCommand actionCommand, String actionValue, String hoverText){
        sendMessage(player, generateJsonString(message, actionCommand.string, actionValue, hoverText));
    }

    public void sendMessage(Player player, String message){
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(message);
        sendMessage(player, new PacketPlayOutChat(component));
    }

    public String generateJsonString(String message, String actionCommand, String actionValue, String hoverText){
        JsonObject obj = new JsonObject();

        obj.add("text", new JsonPrimitive(message));
        obj.add("color", new JsonPrimitive("gold"));
        obj.add("bold", new JsonPrimitive("false"));
        if(actionCommand != null){
            JsonObject temp = new JsonObject();
            temp.add("action", new JsonPrimitive(actionCommand));
            temp.add("value", new JsonPrimitive(actionValue));

            obj.add("clickEvent", temp);
        }
        if(hoverText != null){
            JsonObject temp = new JsonObject();
            JsonObject text = new JsonObject();
            temp.add("action", new JsonPrimitive("show_text"));

            text.add("text", new JsonPrimitive(hoverText));
            text.add("color", new JsonPrimitive("gray"));
            temp.add("value", text);

            obj.add("hoverEvent", temp);
        }

        return obj.toString();
    }
}
