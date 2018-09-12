package ecore.services;

import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class NMSUtils {

    public static PlayerConnection getPlayerConnection(Player player){
        return ((CraftPlayer) player).getHandle().playerConnection;
    }

    public static ItemStack getNMSCopy(org.bukkit.inventory.ItemStack itemStack){
        return CraftItemStack.asNMSCopy(itemStack);
    }

}
