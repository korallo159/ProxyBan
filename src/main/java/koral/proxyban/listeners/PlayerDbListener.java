package koral.proxyban.listeners;


import koral.proxyban.ProxyBan;
import koral.proxyban.database.DatabaseConnection;
import koral.proxyban.events.PlayerBannedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDbListener implements Listener{

    @EventHandler
    public void onPlayerBanned(PlayerBannedEvent ev){
        ProxyBan.getProxyBan().getProxy().getScheduler().runAsync(ProxyBan.getProxyBan(),() -> {
            if ( DatabaseConnection.isDbDifferent() ) DatabaseConnection.updateBan(ev.getUser());
        });

    }
}
