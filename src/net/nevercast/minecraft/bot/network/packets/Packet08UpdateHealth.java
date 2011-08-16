package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 4:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet08UpdateHealth implements IPacket{
    public byte getPacketId() {
        return 0x08;
    }

    private short health;
    public short getHealth(){
        return health;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        health = objectInput.readShort();
    }
}
