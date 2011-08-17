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

    public Block getRelativeBlockAt(int x, int y, int z){
        return new Block(world, this, getChunkBlockLocation(new Vector(x,y,z)), getRelativeInfoFor(x,y,z));
    }

    public Vector getChunkBlockLocation(Vector blockLocation){
        Vector chunkLoc = getFullLocation();
        return new Vector(chunkLoc.X + blockLocation.X, blockLocation.Y, chunkLoc.Z = blockLocation.Z);
    }

    public Vector getFullLocation(){
        return new Vector(x << 4, 0 , z << 4);
    }

    public BlockInfo getRelativeInfoFor(int x, int y, int z){
        if(x < 1 || x > 16) return null;
        if(z < 1 || z > 16) return null;
        if(y < 1 || y > 128) return null;
        x--;y--;z--;
        int index = y + (z * 128) + (x * 128 * 16);
        return new BlockInfo(blockTypes[index], blockData[index], blockLight[index], skyLight[index]);
    }
}
