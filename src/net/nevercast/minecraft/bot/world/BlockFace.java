package net.nevercast.minecraft.bot.world;

import com.sun.istack.internal.FinalArrayList;
import net.nevercast.minecraft.bot.structs.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public enum BlockFace {
    DOWN(new Vector(0,-1,0)),
    UP(new Vector(0,1,0)),
    NORTH(new Vector(0,0,-1)),
    SOUTH(new Vector(0,0,1)),
    EAST(new Vector(-1,0,0)),
    WEST(new Vector(1,0,0));


    private final Vector direction;
    private BlockFace(Vector v){
        direction = v;
    }

    public Vector getDirection(){
        return direction;
    }
}
