package net.nevercast.minecraft.bot.structs;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ItemStack {

    public final static ItemStack EMPTY = new ItemStack((short)0, (byte)0, (short)0);

    public ItemStack(){}
    public ItemStack(short itemid, byte count ,short damage){
        id = itemid;
        this.count = count;
        this.damage = damage;
    }

    public short id;
    public byte count;
    public short damage;
}
