package net.nevercast.minecraft.bot.world;

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
        int chunkX = location.X >> 4;
        int chunkZ = location.Z >> 4;
        if(!isChunkLoaded(chunkX, chunkZ)){
            initChunk(chunkX, chunkZ);
        }
        Chunk chunk = getChunk(chunkX, chunkZ);
        //entire chunk
        if(length == 81920){
            System.out.println("Full chunk update: " + chunkX + ", " + chunkZ);
            chunk.blockTypes = typeData;
            for(int i = 0; i < metaData.length; i++){
                chunk.blockData[i * 2] = (byte)(metaData[i] & 0x0F);
                chunk.blockData[i * 2 + 1] = (byte)(metaData[i] >> 4);
                chunk.blockLight[i * 2] = (byte)(lightData[i] & 0x0F);
                chunk.blockLight[i * 2 + 1] = (byte)(lightData[i] >> 4);
                chunk.skyLight[i * 2] = (byte)(skyData[i] & 0x0F);
                chunk.skyLight[i * 2 + 1] = (byte)(skyData[i] >> 4);
            }
        }else{
            System.out.println("Unsupported small chunk update: " + chunkX + ", " + chunkZ);
        }
    }
}
