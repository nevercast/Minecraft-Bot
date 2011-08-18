package net.nevercast.minecraft.bot.world;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;
import net.nevercast.minecraft.bot.structs.BlockInfo;
import net.nevercast.minecraft.bot.structs.Vector;
import sun.awt.image.BytePackedRaster;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Chunk {

    private World world;
    public byte[] blockTypes = new byte[16 * 128 * 16];
    public byte[] blockData = new byte[16 * 128 * 16];
    public byte[] blockLight = new byte[16 * 128 * 16];
    public byte[] skyLight = new byte[16 * 128 * 16];

    // Chunk indexes.
    private int x, z;
    public int getX(){
        return x;
    }
    public int getZ(){
        return z;
    }

    public World getWorld(){
        return world;
    }

    public Chunk(World world, int x, int z){
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public boolean isChunkAt(int x, int z) {
        return (this.x == x && this.z == z);
    }

    public Vector getAbsoluteLocation(){
        return new Vector(x * 16, 0 , z * 16);
    }

    public Vector getAbsoluteLocation(Vector offset){
        Vector abs = getAbsoluteLocation();
        return new Vector(
                abs.X + offset.X,
                offset.Y,
                abs.Z + offset.Z);
    }

    private BlockInfo getInfo(Vector location){
        if(location.X < 1 || location.X > 16) return null;
        if(location.Z < 1 || location.Z > 16) return null;
        if(location.Y < 1 || location.Y > 128) return null;
        int index = (location.Y - 1) + ((location.Z - 1) * 128) + ((location.X - 1) * 128 * 16);
        return new BlockInfo(blockTypes[index], blockData[index], blockLight[index], skyLight[index]);
    }

    public Block getBlock(Vector location) {
        return new Block(this.world, this, getAbsoluteLocation(location), getInfo(location));
    }
}
