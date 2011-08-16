package net.nevercast.minecraft.bot.entities;

import net.nevercast.minecraft.bot.structs.Location;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/16/11
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityPool {
    HashMap<Integer, GameEntity> entities = new HashMap<Integer, GameEntity>();

    public void addEntity(GameEntity entity){
        int id = entity.getEid();
        if(!entities.containsKey(id)){
            entities.put(id, entity);
            System.out.println("Added ent " + entity);
        }
    }

    public void removeEntity(GameEntity entity){
        if(entities.containsKey(entity.getEid())){
            entities.remove(entity.getEid());
            System.out.println("Removed ent " + entity);
        }
    }

    public void setEntity(GameEntity entity){
        if(entities.containsKey(entity.getEid())){
            entities.remove(entity.getEid());
        }
        entities.put(entity.getEid(), entity);
        System.out.println("Reset ent " + entity);
    }

    public void removeEntity(int id){
        if(entities.containsKey(id)){
            System.out.println("Remove ent " + entities.remove(id));
        }
    }

    public GameEntity getEntity(int id){
        if(entities.containsKey(id)){
            return entities.get(id);
        } return null;

    }

    public GameEntity[] getEntities(){
        GameEntity[] entArray = new GameEntity[0];
        entArray = entities.values().toArray(entArray);
        return entArray;
    }

    public NamedGameEntity[] getPlayers(){
        ArrayList<GameEntity> players = new ArrayList<GameEntity>();
        GameEntity[] ents = getEntities();
        for(GameEntity ent : ents){
            if(ent instanceof NamedGameEntity)
                players.add(ent);
        }
        return players.toArray(new NamedGameEntity[0]);
    }

    public MobGameEntity[] getMobs(){
        ArrayList<GameEntity> players = new ArrayList<GameEntity>();
        GameEntity[] ents = getEntities();
        for(GameEntity ent : ents){
            if(ent instanceof MobGameEntity)
                players.add(ent);
        }
        return players.toArray(new MobGameEntity[0]);
    }

    public GameEntity[] filterToRadius(Location location, GameEntity[] entities, double radius){
        ArrayList<GameEntity> inRange = new ArrayList<GameEntity>();
        for(GameEntity ent : entities){
            if(ent.distance2D(location) <= radius){
                inRange.add(ent);
            }
        }
        return inRange.toArray(entities);
    }

    public MobGameEntity[] getMobsInRadius(Location location, double radius){
        return (MobGameEntity[])filterToRadius(location, getMobs(), radius);
    }

    public NamedGameEntity[] getPlayersInRadius(Location location, double radius){
        return (NamedGameEntity[])filterToRadius(location, getPlayers(), radius);
    }


}
