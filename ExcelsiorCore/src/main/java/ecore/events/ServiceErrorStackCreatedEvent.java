package ecore.events;

import ecore.services.errors.ErrorStackEntry;
import org.bukkit.event.HandlerList;

public class ServiceErrorStackCreatedEvent extends CustomEvent.ServiceEvent {

    private ErrorStackEntry entry;

    public ServiceErrorStackCreatedEvent(String cause, ErrorStackEntry entry) {
        super(cause);

        this.entry = entry;
    }

    public ErrorStackEntry getEntry() {
        return entry;
    }
}
