package ecore.services.economy;

import ecore.ECore;
import ecore.services.messages.ServiceMessager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;

/**
 * A basic account that keeps track of an entity's money
 */
public class EconomyAccount {

    private UUID owner;
    private double balance;

    public EconomyAccount(UUID owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public boolean canAfford(double cost){
        return cost <= balance;
    }

    public void subtract(double cost){
        balance -= cost;
    }

    public void add(double amount){
        balance += amount;
    }

    public UUID getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isOwner(UUID uuid) {
        return uuid.compareTo(owner) == 0;
    }

    public void printBalance(){
        ECore.INSTANCE.getMessager().sendMessage(Bukkit.getPlayer(owner),
                ChatColor.GRAY + "Your account balance is " + ChatColor.GREEN + "$" + balance, Optional.of(ServiceMessager.Prefix.ECO));
    }
}
