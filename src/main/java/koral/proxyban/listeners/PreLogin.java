package koral.proxyban.listeners;

import koral.proxyban.ProxyBan;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLogin implements Listener{

    @EventHandler
    public void onPlayerPreLogin(PreLoginEvent ev){
        System.out.println(ev.getConnection().getSocketAddress());
        //getPendingConnection().getVirtualHost().getAddress().getHostName();
    }
}
