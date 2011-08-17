package net.nevercast.minecraft.bot;

import net.nevercast.minecraft.bot.web.MinecraftLogin;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class NeverCastsBot {
    public static void main(String[] args) {
        String loginName = "Bot";
        if(args.length > 0){
            loginName = args[0];
        }
        MinecraftLogin login = new MinecraftLogin(loginName);
        /*if(!login.getLoggedIn()){
            System.out.println("Login failed!");
            if(login.getErrorMessage() != null){
                System.out.println(login.getErrorMessage());
            }
            return;
        }*/
        MinecraftClient client = new MinecraftClient(login);
        try {
            client.connect("localhost");
            while(client.isAlive()){
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
