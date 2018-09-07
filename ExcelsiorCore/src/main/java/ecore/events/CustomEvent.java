package ecore.events;

import org.bukkit.event.Event;

public abstract class CustomEvent extends Event {

    private String cause;

    public CustomEvent(String cause){
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
