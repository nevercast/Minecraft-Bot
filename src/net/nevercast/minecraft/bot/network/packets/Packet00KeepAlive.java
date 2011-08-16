package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet00KeepAlive implements IPacket {
    public byte getPacketId() {
        return 0x00;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
    }
}
