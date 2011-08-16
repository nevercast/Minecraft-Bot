package net.nevercast.minecraft.bot.structs;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Vector {
    public int X,Y,Z;

    public Vector(){}

    public Vector(int x, int y, int z){
        X = x; Y = y; Z = z;
    }

    public Location toLocation(){
        return new Location(X,Y,Z);
    }
}
