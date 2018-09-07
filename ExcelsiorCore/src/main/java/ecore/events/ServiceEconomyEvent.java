package ecore.events;

import ecore.services.economy.EconomyAccount;
import org.bukkit.event.HandlerList;

public abstract class ServiceEconomyEvent extends CustomEvent.ServiceEvent {

    public ServiceEconomyEvent(String cause) {
        super(cause);
    }

    public static class ServiceEconomyPaymentEvent extends ServiceEconomyEvent {

        private EconomyAccount payer, receiver;

        public ServiceEconomyPaymentEvent(String cause, EconomyAccount payer, EconomyAccount receiver) {
            super(cause);

            this.payer = payer;
            this.receiver = receiver;
        }

        public EconomyAccount getPayer() {
            return payer;
        }

        public EconomyAccount getReceiver() {
            return receiver;
        }
    }
}
