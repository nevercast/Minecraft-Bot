package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.ItemStack;
import net.nevercast.minecraft.bot.structs.Vector;
import net.nevercast.minecraft.bot.world.BlockFace;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet0FPlayerBlockPlacement implements IPacket {
    public byte getPacketId() {
        return 0x0F;
    }

    public Vector getBlockPosition() {
        return blockPosition;
    }

    public void setBlockPosition(Vector blockPosition) {
        this.blockPosition = blockPosition;
    }

    public BlockFace getDirection() {
        return direction;
    }

    public void setDirection(BlockFace direction) {
        this.direction = direction;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    private Vector blockPosition;
    private BlockFace direction;
    private ItemStack item;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        blockPosition = new Vector(
                objectInput.readInt(),
                objectInput.readByte(),
                objectInput.readInt()
        );
        direction = BlockFace.values()[objectInput.readByte()];
        short id = objectInput.readByte();
        if(id == -1){
            item = ItemStack.EMPTY;
        }else{
            byte amount = objectInput.readByte();
            item = new ItemStack(id, amount, objectInput.readShort());
        }
    }
}
