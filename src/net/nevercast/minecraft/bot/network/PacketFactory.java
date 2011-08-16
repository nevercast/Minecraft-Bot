package net.nevercast.minecraft.bot.network;

import net.nevercast.minecraft.bot.network.packets.*;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class PacketFactory {

    private static HashMap<Byte, Class> supportedPackets = new HashMap<Byte, Class>();
    private static HashMap<Byte, Integer> unsupportedPackets = new HashMap<Byte, Integer>();

    static{
        supportedPackets.put((byte)0x00, Packet00KeepAlive.class);
        supportedPackets.put((byte)0x01, Packet01LoginRequest.class);
        supportedPackets.put((byte)0x02, Packet02Handshake.class);
        supportedPackets.put((byte)0x03, Packet03ChatMessage.class);
        supportedPackets.put((byte)0x04, Packet04TimeUpdate.class);
        supportedPackets.put((byte)0x05, Packet05EntityEquipment.class);
        supportedPackets.put((byte)0x06, Packet06SpawnLocation.class);
        supportedPackets.put((byte)0x08, Packet08UpdateHealth.class);
        supportedPackets.put((byte)0x09, Packet09Respawn.class);
        supportedPackets.put((byte)0x0A, Packet0APlayer.class);
        supportedPackets.put((byte)0x0B, Packet0BPlayerPosition.class);
        supportedPackets.put((byte)0x0C, Packet0CPlayerLook.class);
        supportedPackets.put((byte)0x0D, Packet0DPlayerPositionAndLook.class);
        supportedPackets.put((byte)0x0F, Packet0FPlayerBlockPlacement.class);
        supportedPackets.put((byte)0x1D, Packet1DEntityDestroyed.class);
        supportedPackets.put((byte)0x14, Packet14NamedEntitySpawn.class);
        supportedPackets.put((byte)0x15, Packet15ItemSpawned.class);
        supportedPackets.put((byte)0x18, Packet18MobSpawned.class);
        supportedPackets.put((byte)0x28, Packet28EntityMetadata.class);
        supportedPackets.put((byte)0x33, Packet33MapChunk.class);
        supportedPackets.put((byte)0x34, Packet34MultiBlockChange.class);
        supportedPackets.put((byte)0x35, Packet35BlockChange.class);
        supportedPackets.put((byte)0x46, Packet46StateInfo.class);
        supportedPackets.put((byte)0x67, Packet67SetSlot.class);
        supportedPackets.put((byte)0x68, Packet68WindowItems.class);
        supportedPackets.put((byte)0x82, Packet82UpdateSign.class);
        supportedPackets.put((byte)0xFF, PacketFFDisconnect.class);


        unsupportedPackets.put((byte)0x0E, 11); //Digging
        unsupportedPackets.put((byte)0x32, 9); // Pre-Chunk
        unsupportedPackets.put((byte)0x1C, 10); // Entity velocity
        unsupportedPackets.put((byte)0x1F, 7); // Entity Relative Move
        unsupportedPackets.put((byte)0x20, 6); // Entity Look
        unsupportedPackets.put((byte)0x21, 9); // Entity Look/Move
        unsupportedPackets.put((byte)0x22, 18); // Entity Teleport
        unsupportedPackets.put((byte)0x26, 5); // Entity state
        unsupportedPackets.put((byte)0x36, 12); //Block Action
        unsupportedPackets.put((byte)0x3D, 17); //Effect
        unsupportedPackets.put((byte)0x47, 17); //Thunderbolt
        unsupportedPackets.put((byte)0x65, 1); //Close window
        unsupportedPackets.put((byte)0xC8, 5); //Increment stats
        unsupportedPackets.put((byte)0x12, 5); //Animation
        unsupportedPackets.put((byte)0x16, 8); //Collect item
    }

    public static boolean getSupportsPacketId(byte id){
        return supportedPackets.containsKey(id);
    }

    public static boolean getCanEatPacket(byte id){
        return unsupportedPackets.containsKey(id);
    }

    public static int getPacketLength(byte id){
        return unsupportedPackets.get(id);
    }

    public static IPacket getPacket(byte id){
        Class clazz = supportedPackets.get(id);
        try{
            Object o = clazz.newInstance();
            if(!(o instanceof IPacket)){
                System.out.print(o + " Isn't a packet type!");
                return null;
            }else{
                IPacket packet = (IPacket)o;
                return packet;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
