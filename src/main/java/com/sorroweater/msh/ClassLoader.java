package com.sorroweater.msh;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;

public class ClassLoader {
    public static void main(String[] args) throws Exception {
        File jarFile = new File("server.jar");
         if (!jarFile.exists()) {
            InputStream input = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json").openStream();
            InputStreamReader isReader = new InputStreamReader(input);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder buffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(buffer.toString());
            JSONObject versions = (JSONObject) parser.parse(root.get("versions").toString());
             System.out.println(parser.parse(root.get("versions").getClass().toString()));
            JSONObject zero = (JSONObject) parser.parse(versions.get("0").toString());
            JSONObject url = (JSONObject) parser.parse(zero.get("url").toString());
            input = new URL(url.toString()).openStream();
            isReader = new InputStreamReader(input);
            reader = new BufferedReader(isReader);
            buffer.delete(0, buffer.length());
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            root = (JSONObject) parser.parse(buffer.toString());
            JSONObject downloads = (JSONObject) parser.parse(root.get(2).toString());
            JSONObject server = (JSONObject) parser.parse(downloads.get("server").toString());
             try (BufferedInputStream inputStream = new BufferedInputStream(new URL(server.get("url").toString()).openStream());
                  FileOutputStream file = new FileOutputStream(jarFile.toString())) {
                 byte[] data = new byte[1024];
                 int byteContent;
                 while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                     file.write(data, 0, byteContent);
                 }
             } catch (IOException e) {
                 // handles IO exceptions
                 System.out.println(IOException.class);
                 System.exit(1);
             }
        }
    }
}
