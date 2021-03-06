package koral.proxyban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import koral.proxyban.commands.*;
import koral.proxyban.database.DatabaseConnection;
import koral.proxyban.listeners.PlayerBanned;
import koral.proxyban.listeners.ServerConnect;
import koral.proxyban.model.Cache;
import koral.proxyban.model.User;
import koral.proxyban.rcon.rconserver.RconServer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class ProxyBan extends Plugin {
    public static File bansFile;
    public static File cacheFile;
    public static ProxyBan proxyBan;
    public static Configuration config;
    @Override
    public void onEnable() {
        proxyBan = this;
        reloadConfig();
        createAndImplProxyBansFiles();
        createAndImplCacheFile();

        getProxy().getPluginManager().registerListener(this, new ServerConnect());
        getProxy().getPluginManager().registerListener(this, new PlayerBanned());

        getProxy().getPluginManager().registerCommand(this, new Ban());
        getProxy().getPluginManager().registerCommand(this, new Unban());
        getProxy().getPluginManager().registerCommand(this, new BanDetails());
        getProxy().getPluginManager().registerCommand(this, new Lobby());
        getProxy().getPluginManager().registerCommand(this, new ProxyTeleport());
        getProxy().getPluginManager().registerCommand(this, new AutoMessage());
        new Thread(() -> new RconServer(config.getInt("rcon.port"), config.getString("rcon.password"))).start();
    }

    @Override
    public void onDisable() {
        if(config.getBoolean("db.enabled")) {
            DatabaseConnection.configureDbConnection();
            DatabaseConnection.deleteRecords();
            DatabaseConnection.reloadBansInDatabase();
        }
    }

    public static ProxyBan getProxyBan() {
        return proxyBan;
    }

    public void reloadConfig(){
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.yml");
        try {
            if (!file.exists())
                Files.copy(getResourceAsStream("config.yml"), file.toPath());

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(ProxyBan.config, new File(getDataFolder(), "config.yml"));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
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
            User user = new User("TestowyBan_", "178.36.214.12", "2100-01-12 23:59", "korallo", "Administrator nie podał powodu");
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


}
