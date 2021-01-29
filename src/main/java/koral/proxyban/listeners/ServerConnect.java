package koral.proxyban.listeners;
import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import static koral.proxyban.CacheFunctions.cachePlayer;
import static koral.proxyban.CacheFunctions.shouldCache;


public class ServerConnect implements Listener {
//TODO: zapakowac usera do zmiennej, zeby nie czytac go N razy
    @Deprecated
    @EventHandler
    public void onProxyConnect(LoginEvent event){
        String name = event.getConnection().getName();
        String ip = event.getConnection().getAddress().getAddress().getHostAddress();
        if(shouldCache(name))
            cachePlayer(name, ip);
        if(BanFunctions.isBanned(name, ip)){
                event.setCancelled(true);
                if(BanFunctions.getBanDetailsByIp(ip) != null)
                    event.setCancelReason(new TextComponent(ChatColor.RED + "§lZostałeś zbanowany za: §7" + BanFunctions.getBanDetailsByIp(ip).getReason() +
                            ChatColor.YELLOW + "\n §lData odbanowania §7" + BanFunctions.getBanDetailsByIp(ip).getExpiring()));
                else
                    event.setCancelReason(new TextComponent(ChatColor.RED + "§lZostałeś zbanowany za: §7" + BanFunctions.getBanDetailsByName(name).getReason() +
                            ChatColor.YELLOW + "\n §lData odbanowania §7" + BanFunctions.getBanDetailsByName(name).getExpiring()));

        }
    }
// no i kruszom i wogle


}
