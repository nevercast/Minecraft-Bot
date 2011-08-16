package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.entities.Metadata;
import net.nevercast.minecraft.bot.entities.MobGameEntity;
import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.structs.Vector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet18MobSpawned implements IPacket{
    public byte getPacketId() {
        return 0x18;
    }

    public MobGameEntity getEntity() {
        return entity;
    }
    private MobGameEntity entity;

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        entity = new MobGameEntity(objectInput.readInt());
        entity.setType(objectInput.readByte());
        entity.setPosition(
                new Vector(
                        objectInput.readInt(),
                        objectInput.readInt(),
                        objectInput.readInt()
                )
        );
        entity.setYaw(objectInput.readByte());
        entity.setPitch(objectInput.readByte());
        entity.setData(Metadata.createFromStream(objectInput));
    }
}
