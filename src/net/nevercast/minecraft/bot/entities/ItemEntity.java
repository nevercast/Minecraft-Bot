package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.structs.ItemStack;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/16/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemEntity extends GameEntity {
    public ItemEntity(int eid) {
        super(eid);
    }

    private ItemStack item;
    public ItemStack getItem(){
        return item;
    }

    public void setItem(ItemStack item){
        this.item = item;
    }
}
