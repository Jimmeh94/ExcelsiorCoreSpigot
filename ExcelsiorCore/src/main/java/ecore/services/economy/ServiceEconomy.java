package ecore.services.economy;

import ecore.ECore;
import ecore.services.Service;
import ecore.services.errors.ErrorStackEntry;
import ecore.services.messages.ServiceMessager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class ServiceEconomy extends Service<EconomyAccount> {

    public Optional<EconomyAccount> findAccount(UUID uuid){
        for(EconomyAccount e: objects){
            if(e.isOwner(uuid)){
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * Player one is paying player two
     * @param one
     * @param two
     * @param amount
     * @return
     */
    public boolean pay(UUID one, UUID two, double amount){
        Optional<EconomyAccount> eOne = findAccount(one);
        Optional<EconomyAccount> eTwo = findAccount(two);

        if(!eOne.isPresent()){
            ECore.INSTANCE.getErrorStack().add(new ErrorStackEntry("Other player couldn't be found!", two));
            return false;
        }
        if(!eTwo.isPresent()){
            ECore.INSTANCE.getErrorStack().add(new ErrorStackEntry("Other player couldn't be found!", one));
            return false;
        }

        if(!eOne.get().canAfford(amount)){
            ECore.INSTANCE.getErrorStack().add(new ErrorStackEntry("You don't have enough money!", one));
            return false;
        }

        eOne.get().subtract(amount);
        eTwo.get().add(amount);

        Player pOne = Bukkit.getPlayer(one), pTwo = Bukkit.getPlayer(two);
        ECore.INSTANCE.getMessager().sendMessage(pOne, ChatColor.GREEN + "You transferred $" + amount + " to " + pTwo.getDisplayName(), Optional.of(ServiceMessager.Prefix.SUCCESS));
        ECore.INSTANCE.getMessager().sendMessage(pTwo, ChatColor.GREEN + "You received $" + amount + " from " + pOne.getDisplayName(), Optional.of(ServiceMessager.Prefix.SUCCESS));

        return true;
    }

}
