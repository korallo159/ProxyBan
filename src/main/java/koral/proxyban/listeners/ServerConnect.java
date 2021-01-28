package koral.proxyban.listeners;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.BanFunctions;
import koral.proxyban.model.Cache;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import static koral.proxyban.CacheFunctions.cachePlayer;
import static koral.proxyban.CacheFunctions.shouldCache;
import static koral.proxyban.ProxyBan.bansFile;
import static koral.proxyban.ProxyBan.cacheFile;


public class ServerConnect implements Listener {

    @EventHandler
    public void onProxyConnect(LoginEvent event){
        String name = event.getConnection().getName();
        String ip = String.valueOf(event.getConnection().getVirtualHost().getHostName());
        if(shouldCache(name))
            cachePlayer(name, ip);
        if(BanFunctions.isBanned(name, ip)){
                event.setCancelled(true);
                if(BanFunctions.getBanDetailsByName(name).get("ip") != null)
                    event.setCancelReason(new TextComponent(BanFunctions.getBanDetailsByIp(event.getConnection().getName()).get("reason")));
                else
                    event.setCancelReason(new TextComponent(BanFunctions.getBanDetailsByName(name).get("reason")));
        }
    }



}
