package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.network.PacketInputStream;
import net.nevercast.minecraft.bot.structs.Vector;

import javax.xml.bind.util.ValidationEventCollector;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 4:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet82UpdateSign implements IPacket{
    public byte getPacketId() {
        return (byte)0x82;
    }

    private Vector position;
    private String[] lines;

    public String[] getLines(){
        return lines;
    }

    public Vector getPosition(){
        return position;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        position = new Vector(objectInput.readInt(), objectInput.readShort(), objectInput.readInt());
        lines = new String[4];
        for(int i = 0; i < 4; i++)
            lines[i] = PacketInputStream.readString16(objectInput);
    }
}
