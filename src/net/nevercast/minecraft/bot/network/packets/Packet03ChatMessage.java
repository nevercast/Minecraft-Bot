package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet03ChatMessage implements IPacket{

    public Packet03ChatMessage(){}
    public Packet03ChatMessage(String message){
        this.message = message;
    }

    public byte getPacketId() {
        return 0x03;
    }

    private String message;

    public String getMessage(){
        return message;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeShort(message.length());
        objectOutput.write(message.getBytes("UTF-16BE"));
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        byte[] bytes = new byte[objectInput.readShort() * 2];
        objectInput.read(bytes);
        message = new String(bytes, "UTF-16BE");
    }
}
