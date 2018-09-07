package ecore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CustomEvent extends Event {

    public static final String SERVER_CAUSE = "Server";

    private String cause;

    protected static final HandlerList handlers = new HandlerList();

    public CustomEvent(String cause){
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static abstract class ServiceEvent extends CustomEvent{

        public ServiceEvent(String cause) {
            super(cause);
        }

    }
}
