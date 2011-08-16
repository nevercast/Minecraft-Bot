package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.ItemStack;
import sun.java2d.pipe.OutlineTextRenderer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet67SetSlot implements IPacket {
    public byte getPacketId() {
        return 0x67;
    }

    private byte wid;
    private short slot;
    private ItemStack item;

    public byte getWid(){
        return wid;
    }

    public short getSlot(){
        return slot;
    }

    public ItemStack getItem(){
        return item;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        wid = objectInput.readByte();
        slot = objectInput.readShort();
        short id = objectInput.readShort();
        if(id == -1){
            item = ItemStack.EMPTY;
        }else{
            item = new ItemStack(id, objectInput.readByte(), objectInput.readShort());
        }
    }
}
