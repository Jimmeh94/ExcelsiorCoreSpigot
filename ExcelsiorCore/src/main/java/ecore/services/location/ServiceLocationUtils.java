package ecore.services.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

    public static Vector getOffsetBetween(Location start, Location end){
        if(start == null || end == null)
            return new Vector(0.0, 0.0, 0.0);

        double deltaX, deltaY, deltaZ;
        deltaX = Math.max(start.getX(), end.getX()) - Math.min(start.getX(), end.getX());
        if(deltaX > 0)
            deltaX = Math.max(start.getX(), end.getX()) == start.getX() ? deltaX *-1 : deltaX * 1;

        deltaY = Math.max(start.getY(), end.getY()) - Math.min(start.getY(), end.getY());
        if(deltaY > 0)
            deltaY = Math.max(start.getY(), end.getY()) == start.getY() ? deltaY *-1 : deltaY * 1;

        deltaZ = Math.max(start.getZ(), end.getZ()) - Math.min(start.getZ(), end.getZ());
        if(deltaZ > 0)
            deltaZ = Math.max(start.getZ(), end.getZ()) == start.getZ() ? deltaZ *-1 : deltaZ * 1;

        return new Vector(deltaX, deltaY, deltaZ);
    }

    public static List<Location> getConnectingLine(Location start, Location end){
        List<Location> give = new ArrayList<>();
        give.add(start);

        int deltaX = Math.max(start.getBlockX(), end.getBlockX()) - Math.min(start.getBlockX(), end.getBlockX());
        int deltaY = Math.max(start.getBlockY(), end.getBlockY()) - Math.min(start.getBlockY(), end.getBlockY());
        int deltaZ = Math.max(start.getBlockZ(), end.getBlockZ()) - Math.min(start.getBlockZ(), end.getBlockZ());

        int xCoefficient = start.getBlockX() > end.getBlockX() ? -1 : 1;
        if(start.getBlockX() == end.getBlockX())
            xCoefficient = 0;

        int zCoefficient = start.getBlockZ() > end.getBlockZ() ? -1 : 1;
        if(start.getBlockZ() == end.getBlockZ())
            zCoefficient = 0;

        int yCoefficient = start.getBlockY() > end.getBlockY() ? -1 : 1;
        if(start.getBlockY() == end.getBlockY())
            yCoefficient = 0;

        Location temp = start.clone();
        do{
            if(deltaX == 0)
                xCoefficient = 0;
            if(deltaY == 0)
                yCoefficient = 0;
            if(deltaZ == 0)
                zCoefficient = 0;

            temp = temp.add(xCoefficient, yCoefficient, zCoefficient);
            give.add(temp);

            deltaX--;
            deltaY--;
            deltaZ--;
        } while(deltaX > 0 && deltaY > 0&& deltaZ> 0);

        give.add(end);

        return give;
    }

}
