package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 1:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet06SpawnLocation implements IPacket{
    public byte getPacketId() {
        return 0x06;
    }

    private int x,y,z;
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getZ(){
        return z;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeInt(x);
        objectOutput.writeInt(y);
        objectOutput.writeInt(z);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        x = objectInput.readInt();
        y = objectInput.readInt();
        z = objectInput.readInt();
    }
}
