package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.network.PacketInputStream;
import net.nevercast.minecraft.bot.structs.ItemStack;
import net.nevercast.minecraft.bot.structs.Vector;
import sun.nio.cs.ext.MacDingbat;

import javax.xml.bind.util.ValidationEventCollector;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Metadata {

    public static Metadata createFromStream(DataInputStream dataInputStream) throws IOException {
        Metadata metadata = new Metadata();
        byte x = 0;
        while ((x = dataInputStream.readByte()) != 127){
            switch (x){
                case 0: metadata.appendField(dataInputStream.readByte()); break;
                case 1: metadata.appendField(dataInputStream.readShort()); break;
                case 2: metadata.appendField(dataInputStream.readInt()); break;
                case 3: metadata.appendField(dataInputStream.readFloat()); break;
                case 4: metadata.appendField(PacketInputStream.readString16(dataInputStream)); break;
                case 5:
                    ItemStack stack = new ItemStack();
                    stack.id = dataInputStream.readShort();
                    stack.count = dataInputStream.readByte();
                    stack.damage = dataInputStream.readShort();
                    metadata.appendField(stack); break;
                case 6:
                    Vector vector = new Vector();
                    vector.X = dataInputStream.readInt();
                    vector.Y = dataInputStream.readInt();
                    vector.Z = dataInputStream.readInt();
                    break;
            }
        }
        return  metadata;
    }

    private ArrayList<Object> fields = new ArrayList<Object>();

    public int length(){
        return  fields.size();
    }

    public void removeField(int index){
        fields.remove(index);
    }

    public void appendField(Object value){
        fields.add(value);
    }

    public void setField(int index, Object value){
        fields.set(index, value);
    }

    public Object getField(int index){
        return fields.get(index);
    }

    public Byte getByte(int index){
        return (Byte)getField(index);
    }

    public Short getShort(int index){
        return (Short)getField(index);
    }

    public Integer getInt(int index){
        return (Integer)getField(index);
    }

    public Integer getFloat(int index){
        return (Integer)getField(index);
    }

    public String getString(int index){
        return (String)getField(index);
    }

    public ItemStack getItem(int index){
        return (ItemStack)getField(index);
    }

    public Vector getVector(int index){
        return (Vector)getField(index);
    }
}
