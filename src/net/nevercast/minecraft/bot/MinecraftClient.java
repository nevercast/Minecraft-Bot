package net.nevercast.minecraft.bot;

import net.nevercast.minecraft.bot.entities.EntityPool;
import net.nevercast.minecraft.bot.entities.MobGameEntity;
import net.nevercast.minecraft.bot.network.PacketFactory;
import net.nevercast.minecraft.bot.structs.ItemStack;
import net.nevercast.minecraft.bot.structs.Location;
import net.nevercast.minecraft.bot.structs.Vector;
import net.nevercast.minecraft.bot.network.IPacket;
import net.nevercast.minecraft.bot.network.PacketInputStream;
import net.nevercast.minecraft.bot.network.PacketOutputStream;
import net.nevercast.minecraft.bot.network.packets.*;
import net.nevercast.minecraft.bot.web.MinecraftLogin;
import net.nevercast.minecraft.bot.world.Block;
import net.nevercast.minecraft.bot.world.Chunk;
import net.nevercast.minecraft.bot.world.World;

import java.io.IOException;
import java.net.Socket;
import java.util.zip.DataFormatException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinecraftClient extends Thread implements GamePulser.IGamePulserReceptor{

    private MinecraftLogin login;
    private Socket socket = null;
    private PacketInputStream packetInputStream;
    private PacketOutputStream packetOutputStream;
    private Location location = null;
    private Vector spawn = null;
    private long gameTicks;
    private ItemStack[] inventory;
    private GamePulser tickSource;
    private short health;
    private byte dimension;
    private EntityPool entityPool;
    private World world;

    private int myEntId;

    public MinecraftClient(MinecraftLogin loginInformation){
        this.login = loginInformation;
        tickSource = new GamePulser(this, 50);
        tickSource.start();
        initInventory();
        entityPool = new EntityPool();
        world = new World();
    }

    private void setInventoryItem(int slot, ItemStack itemStack){
        inventory[slot] = itemStack;
    }

    private ItemStack getInventoryItem(int slot){
        return inventory[slot];
    }

    private void initInventory() {
        inventory = new ItemStack[45];
        for(int i = 0; i < 45; i++){
            inventory[i] = ItemStack.EMPTY;
        }
    }

    public void connect(String address) throws IOException {
        connect(address, 25565);
    }

    public void connect(String address, int port) throws IOException {
        socket = new Socket(address, port);
        socket.setTcpNoDelay(true);
        packetInputStream = new PacketInputStream(socket.getInputStream());
        packetOutputStream = new PacketOutputStream(socket.getOutputStream());
        start(); //Start ye thread
    }

    // Receiver
    public void run(){
        try {
            packetOutputStream.writePacket(
                    new Packet02Handshake(login.getUsername())
            );
            while(socket.isConnected() && !isInterrupted()){
                IPacket mcPacket = packetInputStream.readPacket();
                if(mcPacket != null){
                    handlePacket(mcPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try { socket.shutdownInput(); } catch (Exception ex){}
            try { socket.close(); } catch(Exception ex){}
        }
    }

    private void handlePacket(IPacket mcPacket) throws IOException{
        //System.out.println("Handling packet " + mcPacket.getPacketId());
        switch (mcPacket.getPacketId()){
            case 0x02: handlerBeginLogin((Packet02Handshake)mcPacket); break;
            case 0x01: handlerFinishLogin((Packet01LoginRequest)mcPacket); break;
            case 0x00: handleAnnoyingKeepAlive(); break;
            case 0x0D: handlePositionAndLook((Packet0DPlayerPositionAndLook)mcPacket); break;
            case 0x06: handleSpawn((Packet06SpawnLocation)mcPacket); break;
            case 0x04: handleTime((Packet04TimeUpdate)mcPacket); break;
            case 0x08: handleHealthUpdate((Packet08UpdateHealth)mcPacket); break;
            case 0x68: handleWindowItems((Packet68WindowItems)mcPacket);break;
            case 0x03: handleChatMessage((Packet03ChatMessage)mcPacket); break;
            case 0x18: handleMobSpawned((Packet18MobSpawned)mcPacket); break;
            case 0x1D: handleEntDestroy((Packet1DEntityDestroyed)mcPacket); break;
            case 0x14: handlePlayerSpawned((Packet14NamedEntitySpawn)mcPacket); break;
            case 0x15: handleItemSpawn((Packet15ItemSpawned)mcPacket); break;
            case 0x28: handleEntMeta((Packet28EntityMetadata)mcPacket); break;
            case 0x33: handleMapChunk((Packet33MapChunk)mcPacket); break;
            case (byte)0xFF: handleDisconnect((PacketFFDisconnect)mcPacket); break;
        }
    }

    private void handleMapChunk(Packet33MapChunk packet) throws IOException{
        try {
            world.updateChunk(packet.getLocation(), packet.getSize(), packet.getCompressedData());
        } catch (IOException e) {
            throw e;
        } catch (DataFormatException e) {
            throw new IOException(e);
        }
    }

    private void handleEntMeta(Packet28EntityMetadata packet) {
        ((MobGameEntity)entityPool.getEntity(packet.getEid())).setData(packet.getData());
    }

    private void handleItemSpawn(Packet15ItemSpawned packet) {
        entityPool.addEntity(packet.getItem());
    }

    private void handlePlayerSpawned(Packet14NamedEntitySpawn packet) {
        entityPool.addEntity(packet.getEntity());
    }

    private void handleEntDestroy(Packet1DEntityDestroyed packet) {
        entityPool.removeEntity(packet.getEid());
    }

    private void handleMobSpawned(Packet18MobSpawned mcPacket) {
        MobGameEntity entity = mcPacket.getEntity();
        entityPool.addEntity(entity);
    }

    private void handleChatMessage(Packet03ChatMessage packet) {
        if(packet.getMessage().contains("<") && packet.getMessage().contains("> ")){
            String messageContent = packet.getMessage().split(" ", 2)[1].trim();
            if(messageContent.startsWith("~")){
                issueCommand(messageContent.substring(1));
            }
        }
    }

    private void issueCommand(String message) {
        if(message.equalsIgnoreCase("echo eid")){
            sendMessage("EID: " + this.myEntId);
        }else if(message.startsWith("lookat")){
            if(!message.contains(" "))
                return;
            String who = message.split(" ")[1];
        }else if(message.equalsIgnoreCase("echo mob count")){
            sendMessage("Mobs: " + entityPool.getMobs().length);
        }else if(message.equalsIgnoreCase("echo player count")){
            sendMessage("Players: " + entityPool.getPlayers().length);
        }else if(message.equalsIgnoreCase("echo loaded chunks")){
            sendMessage("Loaded chunks: " + world.getChunkCount());
        }else if(message.equalsIgnoreCase("echo location")){
            sendMessage("Location: " + location.X + ", " + location.Y + ", " + location.Z);
        }else if(message.equalsIgnoreCase("echo current chunk")){
            Chunk c = world.getChunkAt(location.toVector());
            if(c == null){
                sendMessage("Chunk not loaded");
            }else{
                sendMessage("Chunk: " + c.getX() + ", " + c.getZ());
            }
        }else if(message.equalsIgnoreCase("echo surface position")){
            Chunk c = world.getChunkAt(location.toVector());
            if(c == null){
                sendMessage("Chunk not loaded");
            }else{
                int y = (int)location.Y;
                Vector blockPosition = location.toVector();
                for(; y > 0; y--){
                    blockPosition.Y = y;
                    Block block = world.getBlockAt(blockPosition);
                    if(block.getInfo().blockType != 0){
                        Vector surfLoc = block.getLocation();
                        sendMessage("Surface: " + surfLoc.X + ", " + surfLoc.Y + ", " + surfLoc.Z + " (" + block.getInfo().blockType + ")");
                        return;
                    }
                }
                sendMessage("Failed to find surface!");
            }
        }
    }

    private void sendMessage(String message){
        Packet03ChatMessage m = new Packet03ChatMessage(message);
        try {
            packetOutputStream.writePacket(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleHealthUpdate(Packet08UpdateHealth packet) {
        health = packet.getHealth();
        if(health < 1){
            System.out.println("z0mg I'm dead! Respawn pl0x");
            try{
                packetOutputStream.writePacket(new Packet09Respawn(dimension));
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Health: " + health);
        }
    }

    private void handleDisconnect(PacketFFDisconnect packet) {
        interrupt();
        System.out.println("Disconnected: Reason: " + packet.getReason());
        System.exit(-1);
    }

    private void handleWindowItems(Packet68WindowItems packet) {
        if(packet.getWid() != 0){
            System.out.println("Window update for " + packet.getWid());
            return;
        }else{
            System.out.println("Receiving inventory!");
        }
        for(int i = 0; i < packet.getCount(); i++){
            setInventoryItem(i, packet.getItemStack()[i]);
        }
    }

    private void handleTime(Packet04TimeUpdate packet) {
        gameTicks = packet.getTicks();
    }

    private void handleSpawn(Packet06SpawnLocation packet) {
        spawn = new Vector();
        spawn.X = packet.getX();
        spawn.Y = packet.getY();
        spawn.Z = packet.getZ();
    }

    private void handlePositionAndLook(Packet0DPlayerPositionAndLook packet) {
        location = packet.getLocation();
    }

    private void handleAnnoyingKeepAlive() throws IOException {
        // Marco, Polo
        packetOutputStream.writePacket(new Packet00KeepAlive());
    }

    private void handlerFinishLogin(Packet01LoginRequest packet) {
        myEntId = packet.getVersionAndEntity();
        System.out.println("Oh cool! I'm Mr." + myEntId + ". Kinda unsocial really!");
        dimension = packet.getDimension();
    }

    private void handlerBeginLogin(Packet02Handshake packet) throws IOException {
        System.out.println("Handling handshake!");
        String hash = packet.getConnectionHash();
        if(hash.equalsIgnoreCase("-")){
            // Open server, login without check
            Packet01LoginRequest loginRequest = new Packet01LoginRequest(login.getUsername());
            packetOutputStream.writePacket(loginRequest);
        }else if(hash.length() > 1){
            // We have to confirm with hash, I don't support that yet. I'm lazy
            throw new IOException("Unsupported authentication scheme: Authentication.");
        }

    }


    public void tick(long elapsedTime) throws Exception{
        //System.out.println("Tick: " + elapsedTime + "ms");
        if(location != null){
            Packet0DPlayerPositionAndLook position = new Packet0DPlayerPositionAndLook(location);
            packetOutputStream.writePacket(position);
        }
    }
}
