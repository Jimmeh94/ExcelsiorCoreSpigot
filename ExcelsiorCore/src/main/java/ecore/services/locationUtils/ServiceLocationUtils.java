package ecore.services.locationUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ServiceLocationUtils {

    public static Location getMiddleLocation(Location firstCorner, Location secondCorner){
        Vector temp = getMiddleLocation(firstCorner.toVector(), secondCorner.toVector());
        return new Location(firstCorner.getWorld(), temp.getX(), temp.getY(), temp.getZ());
    }

    public static Vector getMiddleLocation(Vector firstCorner, Vector secondCorner){
        int x = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX()) - Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
        int y = Math.max(firstCorner.getBlockY(), secondCorner.getBlockY()) - Math.min(firstCorner.getBlockY(), secondCorner.getBlockY());
        int z = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ()) - Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());
        x /= 2;
        y /= 2;
        z /= 2;
        return new Vector(firstCorner.getBlockX() + x + 0.5, firstCorner.getBlockY() + y + 0.5, firstCorner.getBlockZ() + z + 0.5);
    }

    public static boolean isWithinTwoLocations(Location first, Location second, Location check){
        return isWithinTwoLocations(first.toVector(), second.toVector(), check.toVector());
    }

    public static boolean isWithinTwoLocations(Vector first, Vector second, Vector check){
        int fx = first.getBlockX(), fy = first.getBlockY(), fz = first.getBlockZ();
        int sx = second.getBlockX(), sy = second.getBlockY(), sz = second.getBlockZ();

        return Math.min(fx, sx) <= check.getBlockX() && check.getBlockX() <= Math.max(fx, sx) &&
                Math.min(fy, sy) <= check.getBlockY() && check.getBlockY() <= Math.max(fx, sx) &&
                Math.min(fz, sz) <= check.getBlockZ() && check.getBlockZ() <= Math.max(fz, sz);
    }

    public static String locationToString(Location location){
        String give = location.getWorld().getName();
        give += "," + String.valueOf(location.getX());
        give += "," + String.valueOf(location.getY());
        give += "," + String.valueOf(location.getZ());
        return give;
    }

    public static Location locationFromString(String loc){
        String[] temp = loc.split(",");
        return new Location(Bukkit.getWorld(temp[0]), Double.valueOf(temp[1]), Double.valueOf(temp[2]), Double.valueOf(temp[3]));
    }

}
