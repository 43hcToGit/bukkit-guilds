package net.juxyc.guilds.data.impl.region;

import lombok.Getter;
import lombok.Setter;
import net.juxyc.guilds.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
public class Region {

    private final int x;

    private final int z;

    private final int size;

    public Region(final int x, final int z, final int size){
        this.x = x;
        this.z = z;
        this.size = 50;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld("world"), this.getX(), 40, this.getZ());
    }

    public Location getCenter() {
        return new Location(Bukkit.getWorld("world"), this.x, 41.0D, this.z);
    }

    public boolean isInCuboid(final Location loc) {
        if (!loc.getWorld().equals(Bukkit.getWorld("world"))) {
            return false;
        }
        final int distanceX = Math.abs(loc.getBlockX() - this.x);
        final int distanceZ = Math.abs(loc.getBlockZ() - this.z);
        return distanceX <= this.getSize() && distanceZ <= this.getSize();
    }

    public boolean isInGuildCenter(Location loc, int top, int down, int wall) {
        final Location c = this.getLocation().clone();
        return c.getBlockY() - down <= loc.getBlockY() && c.getBlockY() + top >= loc.getBlockY() &&
                loc.getBlockX() <= c.getBlockX() + wall
                && loc.getBlockX() >= c.getBlockX() - wall
                && loc.getBlockZ() <= c.getBlockZ() + wall
                && loc.getBlockZ() >= c.getBlockZ() - wall;
    }
}
