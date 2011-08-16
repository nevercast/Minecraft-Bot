package net.nevercast.minecraft.bot.web;

import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class MinecraftLogin {

    private String username;
    private String latestVersion;
    private String downloadTicket;
    private String sessionId;
    private boolean isLoggedIn;
    private String errorMessage = null;

    public static final int CLIENT_VERSION = 14;

    // Offline mode
    public MinecraftLogin(String username){
        this.username = username;
        isLoggedIn = false;
    }

    public MinecraftLogin(String username, String password){
        try {
            String parameters = "user=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&version=" + CLIENT_VERSION;
            String result = WebUtil.excutePost("https://login.minecraft.net/", parameters);
            if (result == null) {
                setErrorMessage("Can't connect to minecraft.net");
                return;
            }
            if (!result.contains(":")) {
                if (result.trim().equals("Bad login")) {
                    setErrorMessage("Login failed");
                } else if (result.trim().equals("Old version")) {
                    setErrorMessage("Outdated!");
                } else {
                    setErrorMessage(result);
                }
                return;
            }
            String[] values = result.split(":");

            this.username = values[2].trim();
            this.latestVersion = values[0].trim();
            this.downloadTicket = values[1].trim();
            this.sessionId = values[3].trim();
            isLoggedIn = true;
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(e.toString());
        }
    }

    public String getLatestVersion(){
        return latestVersion;
    }

    public String getDownloadTicket(){
        return downloadTicket;
    }

    public String getSessionId() {
        return sessionId;
    }


    private void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }


    public String getUsername(){
        return username;
    }

    public boolean getLoggedIn(){
        return isLoggedIn;
    }
}
