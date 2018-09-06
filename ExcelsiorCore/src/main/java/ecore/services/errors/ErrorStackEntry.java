package ecore.services.errors;

import java.util.UUID;

public class ErrorStackEntry {

    private UUID[] participants;
    private String error;
    private long createdAt;

    public ErrorStackEntry(String error, UUID... participants) {
        this.participants = participants;
        this.error = error;
        this.createdAt = System.currentTimeMillis();
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
