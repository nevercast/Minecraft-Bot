package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet0APlayer implements IPacket {
    public byte getPacketId() {
        return 0x0A;
    }

    public Packet0APlayer(){}
    public Packet0APlayer(boolean onGround){
        this.onGround = onGround;
    }

    private boolean onGround;
    public boolean getOnGround(){
        return onGround;
    }

    public void setOnGround(boolean onGround){
        this.onGround = onGround;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeBoolean(onGround);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {

    }
}
