package koral.proxyban.listeners;
import koral.proxyban.BanFunctions;
import koral.proxyban.model.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import static koral.proxyban.CacheFunctions.cachePlayer;
import static koral.proxyban.CacheFunctions.shouldCache;


public class ServerConnect implements Listener {
    @Deprecated
    @EventHandler
    public void onProxyConnect(LoginEvent event){
        String name = event.getConnection().getName();
        String ip = event.getConnection().getAddress().getAddress().getHostAddress();
        if(shouldCache(name))
            cachePlayer(name, ip);
        if(BanFunctions.isBanned(name, ip)){
                event.setCancelled(true);
                User userByIp = BanFunctions.getUserByIp(ip);
                if(userByIp != null)
                    event.setCancelReason(new TextComponent(ChatColor.RED + "§lZostałeś zbanowany za: §f§l" + userByIp.getReason() +
                            ChatColor.YELLOW + "\n §lData odbanowania: §f§l" + userByIp.getExpiring()));
                else {
                    User userByName = BanFunctions.getUserByName(name);
                    event.setCancelReason(new TextComponent(ChatColor.RED + "§lZostałeś zbanowany za: §f§l" + userByName.getReason() +
                            ChatColor.YELLOW + "\n §lData odbanowania: §f§l" + userByName.getExpiring()));
                }

        }
    }
// no i kruszom i wogle


}
