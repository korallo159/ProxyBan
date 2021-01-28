package koral.proxyban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.commands.banCommand;
import koral.proxyban.listeners.ServerConnect;
import koral.proxyban.model.Cache;
import koral.proxyban.model.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class ProxyBan extends Plugin {
    public static File bansFile;
    public static ProxyBan proxyBan;
    public static File cacheFile;
    //TODO: cache ip
    //TODO: threading
    @Override
    public void onEnable() {
        proxyBan = this;
        getProxy().getPluginManager().registerListener(this, new ServerConnect());
        createAndImplProxyBansFiles();
        createAndImplCacheFile();
        getProxy().getPluginManager().registerCommand(this, new banCommand());
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
    private void createAndImplCacheFile(){
        cacheFile = new File(ProxyServer.getInstance().getPluginsFolder(), "/ProxyBan/users.json");
        try {
            if(!cacheFile.exists()){
                cacheFile.createNewFile();
                setCacheFileDefaults();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void banFileCreateDefaults(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = new User("Koralik", "178.36.214.12", "2100-01-12 23:59", "korallo", "Administrator nie podał powodu");
            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addPOJO(user);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setCacheFileDefaults(){
        ObjectMapper mapper = new ObjectMapper();
        Cache cache = new Cache("testowy", "123.123.123.123");
        try {
            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addPOJO(cache);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cacheFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
    }

    public static ProxyBan getProxyBan() {
        return proxyBan;
    }
}
