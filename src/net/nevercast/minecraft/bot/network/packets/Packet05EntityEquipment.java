package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet05EntityEquipment implements IPacket{
    public byte getPacketId() {
        return 0x05;
    }

    private int eid;
    private short slot;
    private ItemStack item;

    public int getEntityId(){
        return eid;
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
        eid = objectInput.readInt();
        slot = objectInput.readShort();
        short item  = objectInput.readShort();
        short info = objectInput.readShort();
        if(item == -1)
            this.item = ItemStack.EMPTY;
        else
            this.item = new ItemStack(item, (byte)1, info);

    }
}
