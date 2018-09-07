package ecore.services.inventory;

import ecore.ECore;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DynamicInventory {

    private String name;

    private List<Entry> pages;

    public DynamicInventory(String name) {
        this.name = name;
        pages = new CopyOnWriteArrayList<>();

        ECore.INSTANCE.getInventory().add(this);
    }

    public void display(Player player){
        player.openInventory(pages.get(0).getInventory());
    }

    public void addPage(Inventory inventory, int nextIndex, int previousIndex){
        pages.add(new Entry(inventory, nextIndex, previousIndex));
    }

    public void removePage(int index){
        pages.remove(index);
    }

    public void removePage(Inventory inventory) {
        pages.remove(getIndexOf(inventory));
    }

    public void nextPage(Player player, Inventory current){
        int index = getIndexOf(current);
        if(index < pages.size() - 1){
            player.closeInventory();
            player.openInventory(pages.get(index + 1).getInventory());
        }
    }

    public void previousPage(Player player, Inventory current){
        int index = getIndexOf(current);
        if(index > 0){
            player.closeInventory();
            player.openInventory(pages.get(index - 1).getInventory());
        }
    }

    protected int getIndexOf(Inventory current){
        for(Entry e: pages){
            if(e.inventory == current){
                return pages.indexOf(e);
            }
        }
        return  -1;
    }

    public boolean hasPage(Inventory inventory) {
        for(Entry entry: pages){
            if(entry.getInventory() == inventory){
                return true;
            }
        }
        return false;
    }

    private Entry getEntry(Inventory inventory){
        for(Entry e: pages){
            if(e.getInventory() == inventory){
                return e;
            }
        }
        return null;
    }

    public void handle(int index, Inventory clickedInventory, Player player) {
        Entry entry = getEntry(clickedInventory);
        if(entry.getNextIndex() == index){
            nextPage(player, clickedInventory);
        } else if(entry.previousIndex == index){
            previousPage(player, clickedInventory);
        }
    }

    /**
     * These are the inventory slots that will change the page forward and backwards
     */
    private static class Entry {
        private Inventory inventory;
        int nextIndex, previousIndex;

        public Entry(Inventory inventory, int nextIndex, int previousIndex) {
            this.inventory = inventory;
            this.nextIndex = nextIndex;
            this.previousIndex = previousIndex;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public int getNextIndex() {
            return nextIndex;
        }

        public int getPreviousIndex() {
            return previousIndex;
        }
    }
}
