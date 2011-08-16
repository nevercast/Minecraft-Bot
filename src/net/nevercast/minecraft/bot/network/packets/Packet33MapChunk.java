package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet33MapChunk implements IPacket{
    public byte getPacketId() {
        return 0x33;
    }

    private int X;
    private short Y;
    private int Z;
    private byte Size_X, Size_Y, Size_Z;
    private byte[] compressedData;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeInt(X);
        objectOutput.writeShort(Y);
        objectOutput.writeInt(Z);
        objectOutput.write(new byte[]{Size_X, Size_Y, Size_Z});
        objectOutput.writeInt(compressedData.length);
        objectOutput.write(compressedData);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        X = objectInput.readInt();
        Y = objectInput.readShort();
        Z = objectInput.readInt();
        Size_X = objectInput.readByte();
        Size_Y = objectInput.readByte();
        Size_Z = objectInput.readByte();
        compressedData = new byte[objectInput.readInt()];
        objectInput.readFully(compressedData);
    }
}
