package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.MinecraftClient;
import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.web.MinecraftLogin;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Packet01LoginRequest implements IPacket{

    public Packet01LoginRequest(){}

    public Packet01LoginRequest(String username){
        this.versionAndEntity = MinecraftLogin.CLIENT_VERSION;
        this.username = username;
        this.mapSeed = 0;
        this.dimension = 0;
    }

    public Packet01LoginRequest(int entID, long mapSeed, byte dimension){
        this.versionAndEntity = entID;
        this.mapSeed = mapSeed;
        this.dimension = dimension;
        this.username = "";
    }

    public byte getPacketId() {
        return 0x01;
    }

    public int getVersionAndEntity() {
        return versionAndEntity;
    }

    public void setVersionAndEntity(int versionAndEntity) {
        this.versionAndEntity = versionAndEntity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getMapSeed() {
        return mapSeed;
    }

    public void setMapSeed(long mapSeed) {
        this.mapSeed = mapSeed;
    }

    public byte getDimension() {
        return dimension;
    }

    public void setDimension(byte dimension) {
        this.dimension = dimension;
    }

    private int versionAndEntity;
    private String username;
    private long mapSeed;
    private byte dimension;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {
        objectOutput.writeInt(versionAndEntity);
        objectOutput.writeShort(username.length());
        objectOutput.write(username.getBytes("UTF-16BE"));
        objectOutput.writeLong(mapSeed);
        objectOutput.writeByte(dimension);
    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        versionAndEntity = objectInput.readInt();
        byte[] bytes = new byte[objectInput.readShort() * 2];
        objectInput.read(bytes);
        username = new String(bytes, "UTF-16BE");
        mapSeed = objectInput.readLong();
        dimension = objectInput.readByte();
    }
}
