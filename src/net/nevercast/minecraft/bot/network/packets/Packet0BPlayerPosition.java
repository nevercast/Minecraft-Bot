package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.Location;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet0BPlayerPosition implements IPacket {

    public Packet0BPlayerPosition(){}
    public Packet0BPlayerPosition(Location location){
        x = location.X;
        y = location.Y;
        z = location.Z;
        stance = location.Stance;
        onGround = location.OnGround;
    }

    public byte getPacketId() {
        return 0x0B;
    }

    private double  x;
    private double y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setStance(double stance) {
        this.stance = stance;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    private double stance;
    private double z;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getStance() {
        return stance;
    }

    public double getZ() {
        return z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean onGround;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeDouble(x);
        objectOutput.writeDouble(y);
        objectOutput.writeDouble(stance);
        objectOutput.writeDouble(z);
        objectOutput.writeBoolean(onGround);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {

    }
}
