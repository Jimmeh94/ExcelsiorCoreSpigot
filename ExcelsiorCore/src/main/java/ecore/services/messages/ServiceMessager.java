package ecore.services.messages;

import ecore.services.errors.ErrorStackEntry;
import ecore.services.messages.channels.ChatChannel;
import ecore.services.messages.messagers.JsonMessager;
import ecore.services.messages.messagers.TitleMessager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class ServiceMessager {

    private TitleMessager titleMessager;
    private JsonMessager jsonMessager;
    private String separator;

    public ServiceMessager() {
        titleMessager = new TitleMessager();
        jsonMessager = new JsonMessager();

        separator = ChatColor.GRAY + "";
        for(int i = 0; i < 30; i++){
            separator += ChatColor.BOLD + AltCodes.BULLET_POINT.getSign();
        }
    }

    public void sendErrorStack(ErrorStackEntry entry){
        for(UUID uuid: entry.getParticipants()){
            sendMessage(Bukkit.getPlayer(uuid), ChatColor.RED + entry.getError(), Optional.of(Prefix.ERROR));
        }
    }

    public void sendMessage(Player player, String text, Optional<Prefix> prefix){
        player.sendMessage(" ");
        if(prefix.isPresent()){
            player.sendMessage(prefix.get().getText() + text);
        } else {
            player.sendMessage(text);
        }
    }

    public void sendLineSeparator(Player player){
        player.sendMessage(separator);
    }

    public void sendBlankLine(Player player){player.sendMessage(" ");}

    public void broadcastMessage(String text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            text = prefix.get().getText() + text;
        }
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(text);
            p.sendMessage(" ");
        }

    }

    public void sendMessageToChannel(ChatChannel chatChannel, String message, Optional<Prefix> prefix) {
        for(UUID uuid: chatChannel.getMembers()){
            sendMessage(Bukkit.getPlayer(uuid), message, prefix);
        }
    }

    public enum Prefix{
        ERROR(ChatColor.RED + ChatColor.BOLD.toString() + "[" + AltCodes.THICK_X.getSign() + "] "),
        INFO(ChatColor.GOLD + ChatColor.BOLD.toString() + "[!] "),
        SUCCESS(ChatColor.GREEN + ChatColor.BOLD.toString() + "[" + AltCodes.CHECKMARK.getSign() + "] "),
        ECO(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD.toString() + "ECO" + ChatColor.GOLD + ChatColor.BOLD.toString() + "] "),
        CHAT(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GRAY + ChatColor.BOLD.toString() + "CHAT" + ChatColor.GOLD + ChatColor.BOLD.toString() + "] "),
        DUEL(ChatColor.GRAY + ChatColor.BOLD.toString() + "[" + ChatColor.RED + ChatColor.BOLD.toString() + "DUEL" + ChatColor.GRAY + ChatColor.BOLD.toString() + "] ");
        private String text;

        Prefix(String text){this.text = text;}

        public String getText() {
            return text;
        }
    }

    public TitleMessager getTitleMessager() {
        return titleMessager;
    }

    public JsonMessager getJsonMessager() {
        return jsonMessager;
    }
}
