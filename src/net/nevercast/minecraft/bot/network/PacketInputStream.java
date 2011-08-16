package net.nevercast.minecraft.bot.network;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PacketInputStream {

    private DataInputStream inputStream;
    public  PacketInputStream(InputStream inputStream) throws IOException {
        if(!(inputStream instanceof DataInputStream)){
            this.inputStream = new DataInputStream(new BufferedInputStream((inputStream)));
        }else{
            this.inputStream = (DataInputStream)inputStream;
        }
    }

    public DataInputStream getUnderlayingStream(){
        return inputStream;
    }

    public static String readString16(DataInputStream dataInputStream) throws IOException {
        byte[] bytes = new byte[dataInputStream.readShort() * 2];
        dataInputStream.read(bytes);
        return new String(bytes, "UTF-16BE");
    }

    public IPacket readPacket() throws IOException {
        try{
            byte id = inputStream.readByte();
            if(id == -1){
                throw new IOException("This shit died!");
            }
            if (PacketFactory.getSupportsPacketId(id)) {
                IPacket packet = PacketFactory.getPacket(id);
                if(packet == null) throw new IOException(id + " was provided as a null packet, Death to input stream!");
                packet.readExternal(inputStream);
                return packet;
            }
            //Packet isn't supported
            //System.out.println("Unsupported packet " + id);
            // Is this packet a static length?
            if(!PacketFactory.getCanEatPacket(id)) throw new IOException("Couldn't eat unknown packet " + id + ", I'm bailing!");
            inputStream.skipBytes(PacketFactory.getPacketLength(id));
        }catch (IOException ioe){
            throw ioe;
        }catch (Exception e){
            throw new IOException(e);
        }
        return null;
    }
}
