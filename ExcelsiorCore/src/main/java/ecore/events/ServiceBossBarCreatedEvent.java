package ecore.events;

import ecore.services.bossbar.ServiceBossBar;
import org.bukkit.event.HandlerList;

public class ServiceBossBarCreatedEvent extends CustomEvent.ServiceEvent {

    private ServiceBossBar.BossBarPair pair;

    public ServiceBossBarCreatedEvent(String cause, ServiceBossBar.BossBarPair pair) {
        super(cause);

        this.pair = pair;
    }

    public ServiceBossBar.BossBarPair getPair() {
        return pair;
    }
}
