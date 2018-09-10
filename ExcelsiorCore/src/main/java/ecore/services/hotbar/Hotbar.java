package ecore.services.hotbar;

import ecore.ECore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Hotbar {

    protected Map<Integer, ItemStack> items;

    public Hotbar() {
        items = new HashMap<>();

        setupItems();
    }

    protected void clearItems(){
        items.clear();
    }

    protected abstract void setupItems();

    protected void addItem(int index, ItemStack item){
        items.put(index, item);
    }

    protected void addItemWithAction(int index, ActionItemStack item){
        items.put(index, item);
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public void setHotbar(Player player){
        for(int i = 0; i < 9; i++){
            player.getInventory().clear(i);
        }
        
        for(Map.Entry<Integer, ItemStack> entry: items.entrySet()){
            player.getInventory().setItem(entry.getKey(), entry.getValue());
        }
        player.updateInventory();

        ECore.INSTANCE.getUsers().findPlayerInfo(player.getUniqueId()).get().setCurrentHotbar(this);
    }

    public void handle(int index, Player player, Action action) {
        if(items.get(index) != null && items.get(index) instanceof ActionItemStack){
            ((ActionItemStack)items.get(index)).doAction(player, action);
        }
    }

    protected static class ActionItemStack extends ItemStack {

        private Callback callback;

        public ActionItemStack(Material type, Callback callback) {
            this(type, 1, callback);
        }

        public ActionItemStack(Material type, int amount, Callback callback) {
            super(type, amount);

            this.callback = callback;
        }

        public void doAction(Player player, Action action){
            callback.action(player, action);
        }

        public interface Callback{

            void action(Player player, Action action);
        }
    }
}
