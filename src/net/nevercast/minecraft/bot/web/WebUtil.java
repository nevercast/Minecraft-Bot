package net.nevercast.minecraft.bot.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: Josh
 * Date: 8/14/11
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebUtil {
    public static String excutePost(String targetURL, String urlParameters)
     {
       HttpsURLConnection connection = null;
       try
       {
         URL url = new URL(targetURL);
         connection = (HttpsURLConnection)url.openConnection();
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

         connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
         connection.setRequestProperty("Content-Language", "en-US");

         connection.setUseCaches(false);
         connection.setDoInput(true);
         connection.setDoOutput(true);

         connection.connect();
         Certificate[] certs = connection.getServerCertificates();

         byte[] bytes = new byte[294];

         /* minecraft.key can be found in the Linux launcher jar file from minecraft.net
            I'm not sure what the rights are for uploading they key, so you will have to download it
            yourself.
          */
         DataInputStream dis = new DataInputStream(WebUtil.class.getResourceAsStream("minecraft.key"));
         dis.readFully(bytes);
         dis.close();

         Certificate c = certs[0];
         PublicKey pk = c.getPublicKey();
         byte[] data = pk.getEncoded();

         for (int i = 0; i < data.length; i++) {
           if (data[i] == bytes[i]) continue; throw new RuntimeException("Public key mismatch");
         }

         DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
         wr.writeBytes(urlParameters);
         wr.flush();
         wr.close();

         InputStream is = connection.getInputStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));

         StringBuffer response = new StringBuffer();
         String line;
         while ((line = rd.readLine()) != null)
         {
           response.append(line);
           response.append('\r');
         }
         rd.close();

         String str1 = response.toString();
         return str1;
       }
       catch (Exception e)
       {
         e.printStackTrace();
         return null;
       }
       finally
       {
         if (connection != null)
           connection.disconnect();
       }
     }

}
