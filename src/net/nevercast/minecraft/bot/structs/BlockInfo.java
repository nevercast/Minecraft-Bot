package net.nevercast.minecraft.bot.structs;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class BlockInfo {

    public BlockInfo(){}

    public BlockInfo(byte type){
        this.blockType = type;
    }

    public BlockInfo(byte type, byte data){
        this.blockType = type;
        this.blockData = data;
    }

    public BlockInfo(byte type, byte data, byte light, byte skyLight){
        this.blockType = type;
        this.blockData = data;
        this.blockLight = light;
        this.skyLight = skyLight;
    }

    public byte blockType;
    public byte blockData;
    public byte blockLight;
    public byte skyLight;
}
