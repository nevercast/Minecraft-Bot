package net.nevercast.minecraft.bot.world;

import net.nevercast.minecraft.bot.structs.Location;
import net.nevercast.minecraft.bot.structs.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.*;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class World {

    /* Note: Because inbound packets are not queued, and are instead processed
    by the receiver thread. Syncronization issues could occur on classes such as this one.

    Thread sync should be considered in the future. But not until it actually matters.
     */
    ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();

    public boolean isChunkLoaded(int x, int z){
        for(Chunk c : loadedChunks){
            if(c.isChunkAt(x, z))
                return true;
        }
        return false;
    }

    public void initChunk(int x, int z){
        if(isChunkLoaded(x, z)) return;
        Chunk c = new Chunk(this, x,z);
        loadedChunks.add(c);
    }

    public Chunk getChunk(int x, int z){
        for(Chunk c : loadedChunks){
            if(c.isChunkAt(x, z)){
                return c;
            }
        }
        return null;
    }

    public void unloadChunk(int x, int z){
        if(!isChunkLoaded(x,z)) return;
        loadedChunks.remove(getChunk(x,z));
    }

    public int getChunkCount(){
        return loadedChunks.size();
    }

    public Block getBlockAt(Vector location){
        Chunk c = getChunkAt(location);
        return c.getBlock(getBlockRelativePosition(location));
    }

    public Vector getBlockRelativePosition(Vector location){
        return new Vector(Math.abs(location.X) % 16, location.Y, Math.abs(location.Z) % 16);
    }

    public Chunk getChunkAt(Vector location){
        return getChunk(location.X / 16, location.Z / 16);
    }

    public Chunk[] getLoadedChunks(){
        return loadedChunks.toArray(new Chunk[0]);
    }

    public void updateChunk(Vector location, Vector size, byte[] data) throws IOException, DataFormatException {
        byte[] decompressedData = new byte[(int)(size.X * size.Y * size.Z * 2.5)];
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        int length = inflater.inflate(decompressedData);
        byte[] typeData = new byte[size.X * size.Y * size.Z];
        System.arraycopy(decompressedData, 0, typeData, 0, typeData.length);
        byte[] metaData = new byte[(size.X * size.Y * size.Z) / 2];
        System.arraycopy(decompressedData, typeData.length, metaData, 0, metaData.length);
        byte[] lightData = new byte[metaData.length];
        System.arraycopy(decompressedData, typeData.length + metaData.length, lightData, 0, lightData.length);
        byte[] skyData = new byte[metaData.length];
        System.arraycopy(decompressedData, typeData.length + metaData.length + lightData.length, skyData, 0, skyData.length);
        int chunkX = location.X / 16;
        int chunkZ = location.Z / 16;
        if(!isChunkLoaded(chunkX, chunkZ)){
            initChunk(chunkX, chunkZ);
        }
        Chunk chunk = getChunk(chunkX, chunkZ);

        Vector rel = getBlockRelativePosition(location);
        int x1 = rel.X;
        int z1 = rel.Y;
        int xs = x1 + size.X;
        int zs = z1 + size.Z;
        for(int x = x1; x < xs; x++){
            for(int z = z1; z < zs; z++){
                int srcIdx = location.Y + (z * size.Y) + (x * size.Y * size.Z);
                int dstIdx = location.Y + (z * 128) + (x * 128 * 16);
                System.arraycopy(typeData, srcIdx, chunk.blockTypes, dstIdx, size.Y);
            }
        }
    }
}
