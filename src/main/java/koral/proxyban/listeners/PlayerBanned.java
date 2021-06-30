package koral.proxyban.listeners;

import koral.proxyban.BanFunctions;
import koral.proxyban.events.PlayerBannedEvent;
import koral.proxyban.ProxyBan;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerBanned implements Listener{

    @EventHandler
    public void onPlayerBanned(PlayerBannedEvent event){
        ProxyBan.getProxyBan().getProxy().getPlayers().forEach(player -> {
            if(player.hasPermission("proxyban.admin")){
                String playerString;
                if(BanFunctions.isIpArg(event.getUser().getName())) playerString = " §9§lzbanował ip §f§l";
                else playerString = " §9§lzbanował gracza §f§l";


               player.sendMessage(player.getUniqueId(), new TextComponent("§9§l!ADMIN ALERT! §f§l" + event.getUser().getAdmin()
                       + playerString
                       + event.getUser().getName() + "§9§l za §f§l" + event.getUser().getReason()));
            }
        });
    }


}
