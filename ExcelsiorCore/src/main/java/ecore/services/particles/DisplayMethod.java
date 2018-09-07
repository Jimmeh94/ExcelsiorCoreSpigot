package ecore.services.particles;

import com.google.common.collect.Lists;
import ecore.ECore;
import ecore.services.nodes.Node;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A display method determines how the effect data will display itself
 */
public interface DisplayMethod {

    void display(EffectData effectData);

    class DisplayMethodDistance implements DisplayMethod {

        private double distance;

        public DisplayMethodDistance(double distance) {
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public void display(EffectData effectData) {
            ECore.INSTANCE.getParticles().displayParticlesWithinDistance(effectData, distance);
        }
    }

    class DisplayMethodPlayers implements DisplayMethod {

        private Player[] displayTo;

        public DisplayMethodPlayers(Player... displayTo) {
            this.displayTo = displayTo;
        }

        public Player[] getDisplayTo() {
            return displayTo;
        }

        @Override
        public void display(EffectData effectData) {
            ECore.INSTANCE.getParticles().displayParticles(effectData, Lists.newArrayList(displayTo));
        }
    }

    class DisplayMethodNode implements DisplayMethod {

        private Node node;

        public DisplayMethodNode(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }

        @Override
        public void display(EffectData effectData) {
            ECore.INSTANCE.getParticles().displayParticlesToNode(effectData, node);
        }
    }

}
