package ecore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public abstract class ServicePartyEvent extends CustomEvent.ServiceEvent implements Cancellable {

    private boolean cancelled = false;
    private Player player;

    public ServicePartyEvent(String cause, Player player) {
        super(cause);

        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public static class ServicePartyPreCreateEvent extends ServicePartyEvent implements Cancellable {

        public ServicePartyPreCreateEvent(String cause, Player creator) {
            super(cause, creator);
        }
    }

    public static class ServicePartyPreJoinEvent extends ServicePartyEvent implements Cancellable {


        public ServicePartyPreJoinEvent(String cause, Player player) {
            super(cause, player);
        }
    }
}
