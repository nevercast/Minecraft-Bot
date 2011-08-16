package net.nevercast.minecraft.bot.entities;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/16/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovingEntity extends GameEntity {
    private int vehicleId;

    public MovingEntity(int eid) {
        super(eid);
    }

    public int getAttachedVehicle(){
        return vehicleId;
    }

    public void setAttachedVehicle(int vid){
        vehicleId = vid;
    }
}
