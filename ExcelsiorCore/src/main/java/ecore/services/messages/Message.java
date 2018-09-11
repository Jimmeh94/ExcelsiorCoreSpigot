package ecore.services.messages;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Message {

    public static MessageBuilder builder() {return new MessageBuilder();}

    private List<String> messages;
    private List<UUID> recipients;
    private Optional<ServiceMessager.Prefix> prefix;

    public Message(MessageBuilder messageBuilder) {
        this.messages = messageBuilder.messages;
        this.recipients = messageBuilder.recipients;
        this.prefix = messageBuilder.prefix;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<UUID> getRecipients() {
        return recipients;
    }

    public Optional<ServiceMessager.Prefix> getPrefix() {
        return prefix;
    }

    public static class MessageBuilder{
        private List<String> messages;
        private List<UUID> recipients;
        private Optional<ServiceMessager.Prefix> prefix = Optional.empty();

        public MessageBuilder() {
            messages = new ArrayList<>();
            recipients = new ArrayList<>();
        }

        public MessageBuilder addMessage(String message){
            messages.add(message);
            return this;
        }

        public MessageBuilder addMessageAsChild(ChatColor color, String message){
            messages.add(color + ChatColor.BOLD.toString() + AltCodes.BULLET_POINT.getSign() + " " + ChatColor.RED.toString() + message);
            return this;
        }

        public MessageBuilder addRecipient(UUID uuid){
            recipients.add(uuid);
            return this;
        }

        public MessageBuilder addPrefix(ServiceMessager.Prefix prefix){
            this.prefix = Optional.of(prefix);
            return this;
        }

        public Message build(){
            return new Message(this);
        }
    }

}
