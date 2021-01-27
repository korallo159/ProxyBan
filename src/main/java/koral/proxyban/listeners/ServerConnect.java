package koral.proxyban.listeners;
import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;


public class ServerConnect implements Listener {

    @EventHandler
    public void onProxyConnect(LoginEvent event){
        String name = event.getConnection().getName();
        String ip = String.valueOf(event.getConnection().getVirtualHost().getHostName());
            if(BanFunctions.isBanned(name, ip)){
                event.setCancelled(true);
                if(BanFunctions.getBanDetailsByName(name).get("ip") != null)
                    event.setCancelReason(new TextComponent(BanFunctions.getBanDetailsByIp(event.getConnection().getName()).get("reason")));
                else
                    event.setCancelReason(new TextComponent(BanFunctions.getBanDetailsByName(name).get("reason")));
        }
    }




}
