package ecore.services.inventory;

import ecore.services.Service;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class ServiceInventory extends Service<DynamicInventory> {

    public boolean isDynamicInventory(Inventory inventory){
        for(DynamicInventory d: objects){
            if(d.hasPage(inventory)){
                return true;
            }
        }
        return false;
    }

    public Optional<DynamicInventory> getDynamicInventory(Inventory inventory){
        for(DynamicInventory d: objects){
            if(d.hasPage(inventory)){
                return Optional.of(d);
            }
        }
        return Optional.empty();
    }

}
