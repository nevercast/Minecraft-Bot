package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 4:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PacketFFDisconnect implements IPacket{
    public byte getPacketId() {
        return (byte)0xFF;
    }

    public PacketFFDisconnect() {}
    public PacketFFDisconnect(String reason){
        this.reason = reason;
    }

    private String reason;

    public String getReason(){
        return reason;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeShort(reason.length());
        objectOutput.write(reason.getBytes("UTF16-BE"));
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        short length = objectInput.readShort();
        byte[] data = new byte[length*2];
        objectInput.readFully(data);
        reason = new String(data, "UTF16-BE");
    }
}
