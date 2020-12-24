package koral.proxyban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public final class ProxyBan extends Plugin {
    File bansFile;
    @Override
    public void onEnable() {
        createAndImplProxyBansFiles();
    }
    private void createAndImplProxyBansFiles(){
        File bansDir = new File(ProxyServer.getInstance().getPluginsFolder() + "/ProxyBan") ;
        bansFile = new File(ProxyServer.getInstance().getPluginsFolder(), "/ProxyBan/bans.json");
        try {
            if(!bansDir.exists()){
                bansDir.mkdir();
            }
            if(!bansFile.exists()){
                bansFile.createNewFile();
                banFileCreateDefaults();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBan(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.readValue(bansFile, String.class);
            Gson gson = new Gson();
            JsonObject inputObj  = gson.fromJson(json, JsonObject.class);
            JsonObject newObject = new JsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void banFileCreateDefaults(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode user1 = mapper.createObjectNode();
            user1.put("uuid", "6c1967d0438f11ebb3780242ac130002");
            user1.put("name", "moral");
            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.add(user1);

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

            FileWriter fileWriter = new FileWriter(bansFile);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User createUserBan(){
        User user = new User();
        user.setBanujacy("korallo");
        user.setNick("koralik");
        user.setUuid("11111111111111");
        return user;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
