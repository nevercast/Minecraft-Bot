package net.nevercast.minecraft.bot.world;

import net.nevercast.minecraft.bot.structs.BlockInfo;
import net.nevercast.minecraft.bot.structs.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Block {

    public Block(World world, Chunk chunk, Vector location, BlockInfo info){
        this.world = world;
        this.chunk = chunk;
        this.location = location;
        this.info = info;
    }

    public BlockInfo getInfo() {
        return info;
    }

    public void setInfo(BlockInfo info) {
        this.info = info;
    }

    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public World getWorld() {
        return world;
    }

    private BlockInfo info;
    private Vector location;
    private Chunk chunk;
    private World world;
}
