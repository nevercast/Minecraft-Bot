package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.structs.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class MobGameEntity extends MovingEntity {
    public MobGameEntity(int eid) {
        super(eid);
    }

    private byte type;
    private Vector position;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public byte getYaw() {
        return yaw;
    }

    public void setYaw(byte yaw) {
        this.yaw = yaw;
    }

    public byte getPitch() {
        return pitch;
    }

    public void setPitch(byte pitch) {
        this.pitch = pitch;
    }

    public Metadata getData() {
        return data;
    }

    public void setData(Metadata data) {
        this.data = data;
    }

    private byte yaw;
    private byte pitch;
    private Metadata data;
}
