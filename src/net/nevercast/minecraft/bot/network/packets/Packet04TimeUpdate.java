package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet04TimeUpdate implements IPacket {

    public byte getPacketId() {
        return 0x04;
    }

    private long time;
    public long getTicks(){
        return time;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeLong(time);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        time = objectInput.readLong();
    }
}
