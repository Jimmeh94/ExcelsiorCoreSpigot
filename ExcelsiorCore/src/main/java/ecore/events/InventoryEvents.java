package ecore.events;

import ecore.ECore;
import ecore.services.user.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){

        if(event.getClickedInventory() == event.getWhoClicked().getInventory())
            return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR))
            return;

        PlayerInfo playerInfo = ECore.INSTANCE.getUsers().findPlayerInfo(event.getWhoClicked().getUniqueId()).get();

        if(ECore.INSTANCE.getInventory().isDynamicInventory(event.getClickedInventory())){
            event.setCancelled(true);

            ECore.INSTANCE.getInventory().getDynamicInventory(event.getClickedInventory()).get().handle(event.getSlot(), event.getClickedInventory(), playerInfo.getPlayer());
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(ECore.INSTANCE.getInventory().isDynamicInventory(event.getInventory())){
            ECore.INSTANCE.getInventory().remove(ECore.INSTANCE.getInventory().getDynamicInventory(event.getInventory()).get());
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickUp(InventoryPickupItemEvent event){
        event.setCancelled(true);
    }

}
