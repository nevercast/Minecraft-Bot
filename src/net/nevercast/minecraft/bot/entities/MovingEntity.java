package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.structs.Vector;
import net.nevercast.minecraft.bot.structs.VectorD;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/16/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovingEntity extends GameEntity {
    private int vehicleId;

    public VectorD getVelocity() {
        return velocity;
    }

    public void setVelocity(VectorD velocity) {
        this.velocity = velocity;
    }

    private VectorD velocity;

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
