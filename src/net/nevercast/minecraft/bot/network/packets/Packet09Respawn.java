package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet09Respawn implements IPacket {

    public Packet09Respawn(){}

    public Packet09Respawn(byte dimension) {
        this.dimension = dimension;
    }

    public byte getPacketId() {
        return 0x09;
    }

    private byte dimension;
    public byte getDimension(){
        return dimension;
    }

    public void setDimension(byte dimension){
        this.dimension = dimension;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeByte(dimension);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        this.dimension = objectInput.readByte();
    }
}
