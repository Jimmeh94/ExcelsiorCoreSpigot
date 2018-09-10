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

    private static TitleMessager titleMessager = new TitleMessager();
    private static JsonMessager jsonMessager = new JsonMessager();
    private static String separator;

    static {
        separator = ChatColor.GRAY + "";
        for(int i = 0; i < 30; i++){
            separator += ChatColor.BOLD + AltCodes.BULLET_POINT.getSign();
        }
    }

    public static void sendMessage(Message message){
        for(UUID uuid: message.getRecipients()){
            for(String s: message.getMessages()){
                sendMessage(Bukkit.getPlayer(uuid), s, message.getPrefix(), false);
            }
        }
    }

    public static void sendErrorStack(ErrorStackEntry entry){
        for(UUID uuid: entry.getParticipants()){
            sendMessage(Bukkit.getPlayer(uuid), ChatColor.RED + entry.getError(), Optional.of(Prefix.ERROR), true);
        }
    }

    public static void sendMessage(Player player, String text, Optional<Prefix> prefix, boolean includeEmptyLine){
        if(includeEmptyLine) {
            player.sendMessage(" ");
        }
        if(prefix.isPresent()){
            player.sendMessage(prefix.get().getText() + text);
        } else {
            player.sendMessage(text);
        }
    }

    public static void sendLineSeparator(Player player){
        player.sendMessage(separator);
    }

    public static void sendBlankLine(Player player){player.sendMessage(" ");}

    public static void broadcastMessage(String text, Optional<Prefix> prefix){
        if(prefix.isPresent()){
            text = prefix.get().getText() + text;
        }
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(text);
            p.sendMessage(" ");
        }

    }

    public static void sendMessageToChannel(ChatChannel chatChannel, String message, Optional<Prefix> prefix) {
        for(UUID uuid: chatChannel.getMembers()){
            sendMessage(Bukkit.getPlayer(uuid), message, prefix, false);
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

    public static TitleMessager getTitleMessager() {
        return titleMessager;
    }

    public static JsonMessager getJsonMessager() {
        return jsonMessager;
    }
}
