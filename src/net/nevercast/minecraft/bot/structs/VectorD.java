package net.nevercast.minecraft.bot.structs;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 3:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class VectorD {
    public double X,Y,Z;

    public VectorD(){}

    public VectorD(double x, double y, double z){
        X = x; Y = y; Z = z;
    }

    public Location toLocation(){
        return new Location(X,Y,Z);
    }
}
