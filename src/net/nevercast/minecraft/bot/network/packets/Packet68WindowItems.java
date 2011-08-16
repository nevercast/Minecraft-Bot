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
 * Time: 3:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet68WindowItems implements IPacket{
    public byte getPacketId() {
        return 0x68;
    }

    private byte wid;
    private short count;

    public byte getWid() {
        return wid;
    }

    public short getCount() {
        return count;
    }

    public ItemStack[] getItemStack() {
        return itemStack;
    }

    private ItemStack[] itemStack;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        wid = objectInput.readByte();
        count = objectInput.readShort();
        itemStack = new ItemStack[count];
        for(int i = 0; i < count; i++){
            short id = objectInput.readShort();
            if(id == -1){
                itemStack[i] = ItemStack.EMPTY;
            }else{
                itemStack[i] = new ItemStack(id, objectInput.readByte(), objectInput.readShort());
            }
        }
    }
}
