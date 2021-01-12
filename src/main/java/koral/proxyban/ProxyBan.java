package koral.proxyban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.listeners.ServerConnect;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;


import java.io.*;
import java.util.Map;

public final class ProxyBan extends Plugin {
    public static File bansFile;
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new ServerConnect());
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


    private void banFileCreateDefaults(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode user1 = mapper.createObjectNode();
            user1.put("uuid", "6c1967d0438f11ebb3780242ac130002");
            user1.put("name", "moral");
            user1.put("ip", "178.36.214.12");
            user1.put("expiring", "2024.01.12");
            user1.put("admin", "koral");
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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
