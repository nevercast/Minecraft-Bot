package net.nevercast.minecraft.bot;

import java.nio.channels.ClosedByInterruptException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/15/11
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class GamePulser extends Thread {
    private IGamePulserReceptor receptor;
    private double delay;
    private double offset = 0;

    private int exceptionCounter = 0;
    public GamePulser(IGamePulserReceptor receptor, long delay){
        this.receptor = receptor;
        this.delay = delay;
    }

    public void run(){
        long time = System.currentTimeMillis();
        long split = (long)delay / 5;
        while(!isInterrupted()){
            try{
                long elapsed = System.currentTimeMillis() - time;
                if(elapsed >= ( delay + offset )){
                    time = System.currentTimeMillis();
                    receptor.tick(elapsed);
                    long timeTaken = System.currentTimeMillis() - time;
                    if(timeTaken > 4){
                        offset -= 2;
                    }else if(offset < 0){
                        offset++;
                    }
                    if(offset < -10){
                        offset = -10;
                    }
                }
                if(split >= 10){
                    Thread.sleep((long)(delay + offset / 5));
                }else{
                    Thread.sleep(5);
                }
            }catch (Exception e){
                exceptionCounter++;
                System.out.println("Pulser exception!");
                e.printStackTrace();
                if(exceptionCounter < 3){
                    continue;
                }else{
                    System.out.println("Too many errors! Bugger this shit homie.");
                    return;
                }
            }
        }
    }

    public interface IGamePulserReceptor{
        void tick(long elapsedTime) throws Exception;
    }
}
