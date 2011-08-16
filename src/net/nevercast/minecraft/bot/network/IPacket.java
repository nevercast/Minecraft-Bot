package net.nevercast.minecraft.bot.network;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPacket {
    byte getPacketId();
    void writeExternal(DataOutputStream objectOutput) throws IOException;
    void readExternal(DataInputStream objectInput) throws IOException;
}
