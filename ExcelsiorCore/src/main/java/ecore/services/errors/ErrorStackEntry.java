package ecore.services.errors;

import ecore.events.CustomEvent;
import ecore.events.ServiceErrorStackCreatedEvent;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * This is an entry class for the ServiceErrorStack. Keeps track of a certain error, who was involved and when it was created
 */
public class ErrorStackEntry {

    private UUID[] participants;
    private String error;
    private long createdAt;

    public ErrorStackEntry(String error, UUID... participants) {
        this.participants = participants;
        this.error = error;
        this.createdAt = System.currentTimeMillis();

        Bukkit.getPluginManager().callEvent(new ServiceErrorStackCreatedEvent(CustomEvent.SERVER_CAUSE, this));
    }

    public UUID[] getParticipants() {
        return participants;
    }

    public String getError() {
        return error;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
