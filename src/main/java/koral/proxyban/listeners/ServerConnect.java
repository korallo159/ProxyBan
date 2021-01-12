package koral.proxyban.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;

import static koral.proxyban.ProxyBan.bansFile;

public class ServerConnect implements Listener {

    //todo wstawic ladne wiadomosci i sprawdzic czy dziala
    @EventHandler
    public void onProxyConnect(LoginEvent event){
        String uuid = event.getConnection().getUniqueId().toString().replace("-", "");
        String ip = event.getConnection().getSocketAddress().toString();
            if(checkUUIDBan(uuid, ip)){
                event.setCancelled(true);
                event.setCancelReason(new TextComponent("Zostales zbanowany na tym serwerze!"));
                BanFunctions.getBanDetailsByUUID(uuid).get("nick");
        }
    }

    private boolean checkUUIDBan(String uuid, String playerip){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for(int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("uuid");
                JsonNode ip = objectNode.get("ip");
                String gotUuid = id.toString().replace("\"", "");
                String gotIp = ip.toString().replace("\"", "");
                if(gotUuid.equals(uuid) || gotIp.equals(playerip)){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
