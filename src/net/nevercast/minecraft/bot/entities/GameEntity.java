package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.structs.Location;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 2:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameEntity {

    public GameEntity(int eid){
        this.eid = eid;
    }

    private Location location;

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    private int eid;
    public int getEid(){
        return eid;
    }

    public double distance3D(GameEntity entity){
        return distance3D(entity.getLocation());
    }

    private double distance3D(Location location) {
        if(location == null || this.location == null)
            return -1;
        else{
            return Math.sqrt(
                            Math.pow(location.X - this.location.X, 2) +
                            Math.pow(location.Y - this.location.Y, 2) +
                            Math.pow(location.Z - this.location.Z, 2)
            );
        }
    }

    public double distance2D(GameEntity entity){
        return distance2D(entity.getLocation());
    }

    public double distance2D(Location location){
        if(location == null || this.location == null)
            return -1;
        else{
            return Math.sqrt(
                            Math.pow(location.X - this.location.X, 2) +
                            Math.pow(location.Z - this.location.Z, 2)
            );
        }
    }

    public String toString(){
        return getEid() + ", " + this.getClass().getName();
    }
}
