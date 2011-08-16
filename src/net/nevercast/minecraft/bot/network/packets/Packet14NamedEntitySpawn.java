package net.nevercast.minecraft.bot.network.packets;

import net.nevercast.minecraft.bot.entities.NamedGameEntity;
import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.network.PacketInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.nevercast.minecraft.bot.structs.Location;
import net.nevercast.minecraft.bot.structs.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Packet14NamedEntitySpawn implements IPacket {

    public byte getPacketId() {
        return 0x14;
    }

    private NamedGameEntity entity;

    public NamedGameEntity getEntity(){
        return entity;
    }

    public void writeExternal(DataOutputStream objectOutput) throws IOException {

    }

    public void readExternal(DataInputStream objectInput) throws IOException {
        int id = objectInput.readInt();
        entity = new NamedGameEntity(id, PacketInputStream.readString16(objectInput));
        entity.setLocation(
                Location.fromAbsoluteInteger(
                        new Vector(objectInput.readInt(), objectInput.readInt(), objectInput.readInt())
                )
        );
        entity.getLocation().setRotationPacked(objectInput.readByte(), objectInput.readByte());
        entity.setHoldingItem(objectInput.readShort());
    }
}
