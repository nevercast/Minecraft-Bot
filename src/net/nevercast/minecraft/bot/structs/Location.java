package net.nevercast.minecraft.bot.structs;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

import javax.swing.text.ZoneView;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Location {

    public Location(){}

    public Location(double X, double Y, double Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public void setPosition(double x, double y, double z){
        this.X = x; this.Y = y; this.Z = z;
    }

    public void setRotation(float yaw, float pitch){
        this.Yaw = yaw;
        this.Pitch = pitch;
    }

    public void setRotationPacked(byte yaw, byte pitch){
        this.Yaw = (float)(yaw*2*Math.PI/256);
        this.Pitch = (float)(pitch*2*Math.PI/256);
    }

    public void setRotation(float yaw, float pitch, float roll){
        this.Yaw = yaw;
        this.Pitch = pitch;
        this.Roll = roll;
    }

    public void setRotationPacked(byte yaw, byte pitch, float roll){
        this.Yaw = (float)(yaw*2*Math.PI/256);
        this.Pitch = (float)(pitch*2*Math.PI/256);
        this.Roll = (float)(roll*2*Math.PI/256);
    }

    public double X, Y, Stance, Z;
    public float Yaw,Pitch, Roll;
    public boolean OnGround;

    public static Location fromAbsoluteInteger(Vector vector){
        return fromAbsoluteInteger(vector.X,  vector.Y, vector.Z);
    }

    public static Location fromAbsoluteInteger(int x, int y, int z){
        return new Location(
                x / 32.0,
                y / 32.0,
                z / 32.0
        );
    }

    public Vector toVector(){
        /* Truncating is no good, we gotta round this data */
        /* Data needs to be rounded down for block positions, which is what I use this for */
        return new Vector((int)Math.floor(X), (int)Math.floor(Y), (int)Math.floor(Z));
    }
}
