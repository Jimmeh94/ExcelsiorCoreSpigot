package ecore.services.bossbar;

import ecore.ECore;
import ecore.events.CustomEvent;
import ecore.events.ServiceBossBarCreatedEvent;
import ecore.services.Service;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Allows easy creation of a boss bar, assign it to an owner, remove and manipulate it
 */
public class ServiceBossBar extends Service<ServiceBossBar.BossBarPair> {

    public BossBarBuilder getBuilder(){return new BossBarBuilder();}

    public void removeBar(UUID owner){
        for(BossBarPair b: objects){
            if(b.getOwner().compareTo(owner) == 0){
                b.getBar().removeAll();
                objects.remove(b);
            }
        }
    }

    public void stopViewingBar(Player viewer){
        for(BossBarPair b: objects){
            if(b.getBar().getPlayers().contains(viewer)){
                b.getBar().removePlayer(viewer);
            }
        }
    }

    public void addViewer(UUID owner, Player viewer){
        for(BossBarPair b: objects){
            if(b.getOwner().compareTo(owner) == 0){
                if(!b.getBar().getPlayers().contains(viewer)){
                    b.getBar().addPlayer(viewer);
                }
            }
        }
    }

    public Optional<BossBarPair> getBossBarPair(UUID owner){
        for(BossBarPair b: objects){
            if(b.getOwner().compareTo(owner) == 0){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public class BossBarBuilder {

        protected String name;
        protected BarColor color;
        protected BarStyle style;
        protected BarFlag[] flags;
        protected double progress = 100;

        public BossBarBuilder setName(String name){
            this.name = name;
            return this;
        }

        public BossBarBuilder setColor(BarColor color){
            this.color = color;
            return this;
        }

        public BossBarBuilder setStyle(BarStyle style){
            this.style = style;
            return this;
        }

        public BossBarBuilder setFlags(BarFlag... flags){
            this.flags = flags;
            return this;
        }

        public BossBarBuilder setProgress(double p){
            this.progress = p;
            return this;
        }

        public void build(UUID owner, Player... display){
            BossBar b = Bukkit.createBossBar(name, color, style, flags);

            for(Player p: display){
                b.addPlayer(p);
            }
            b.setProgress(progress);
            b.setVisible(true);

            Optional<BossBarPair> owned = ECore.INSTANCE.getBossBar().getBossBarPair(owner);
            if(owned.isPresent()){
                ECore.INSTANCE.getBossBar().removeBar(owner);
            }

            BossBarPair pair = new BossBarPair(owner, b);
            ECore.INSTANCE.getBossBar().add(pair);
            Bukkit.getPluginManager().callEvent(new ServiceBossBarCreatedEvent(CustomEvent.SERVER_CAUSE, pair));
        }
    }

    public static class BossBarPair {

        private UUID owner;
        private BossBar bar;

        public BossBarPair(UUID owner, BossBar bar) {
            this.owner = owner;
            this.bar = bar;
        }

        public UUID getOwner() {
            return owner;
        }

        public BossBar getBar() {
            return bar;
        }
    }

}
