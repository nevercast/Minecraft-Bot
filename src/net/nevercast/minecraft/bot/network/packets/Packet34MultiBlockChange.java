package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.BlockInfo;
import net.nevercast.minecraft.bot.structs.Vector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet34MultiBlockChange implements IPacket{
    public byte getPacketId() {
        return 0x34;
    }

    private Vector chunkPosition;
    private Vector[] positions;
    private BlockInfo[] blockInfos;

    public Vector getChunkPosition(){
        return chunkPosition;
    }

    public Vector[] getPositions(){
        return positions;
    }

    public BlockInfo[] getBlockInfo(){
        return blockInfos;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        chunkPosition = new Vector(objectInput.readInt(), 0, objectInput.readInt());
        short size = objectInput.readShort();
        positions = new Vector[size];
        blockInfos = new BlockInfo[size];
        for(int i =0; i < size; i++){
            short cord = objectInput.readShort();
            positions[i] = new Vector(cord >> 12 & 0x15, cord >> 8 & 0x15, cord & 0x31);
        }
        byte[] types = new byte[size];
        byte[] meta = new byte[size];
        objectInput.readFully(types);
        objectInput.readFully(meta);
        for(int i = 0; i < size; i++){
            blockInfos[i] = new BlockInfo();
            blockInfos[i].BlockType = types[i];
            blockInfos[i].BlockData = meta[i];
        }
    }
}
