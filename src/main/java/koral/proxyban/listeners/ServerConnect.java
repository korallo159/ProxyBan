package koral.proxyban.listeners;
import koral.proxyban.BanFunctions;
import koral.proxyban.model.User;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import static koral.proxyban.CacheFunctions.cachePlayer;
import static koral.proxyban.CacheFunctions.shouldCache;


public class ServerConnect implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProxyConnect(LoginEvent event){
        if(event.isCancelled()) return;
        String name = event.getConnection().getName();
        String ip = event.getConnection().getAddress().getAddress().getHostAddress(); //stare zcachowane ip, przez to nie bylo bana.
        if(BanFunctions.isBanned(name, ip)){
                event.setCancelled(true);
                User userByIp = BanFunctions.getUserByIp(ip);
                if(userByIp != null)
                    event.setCancelReason(new TextComponent("§4§l>>§c§lJBWM.PL§4§l<< \n\n" + "§9§lZostałeś zbanowany za: §f§l" + userByIp.getReason() +
                             "§9§l\n Data odbanowania: §f§l" + userByIp.getExpiring() + "\n §9§lZbanowanał Cię administrator: §f§l" + userByIp.getAdmin()
                    + "\n\n§9§lJeżeli twierdzisz, że ban jest niesłuszny zgłoś się do osoby banującej. Oszukiwanie administratora wiążę się z blacklistą."));
                else {
                    User userByName = BanFunctions.getUserByName(name);
                    event.setCancelReason(new TextComponent("§4§l>>§c§lJBWM.PL§4§l<< \n\n" + "§9§lZostałeś zbanowany za: §f§l" + userByName.getReason() +
                            "§9§l\n Data odbanowania: §f§l" + userByName.getExpiring() + "\n §9§lZbanowanał Cię administrator: §f§l" + userByName.getAdmin()
                            + "\n\n§9§lJeżeli twierdzisz, że ban jest niesłuszny zgłoś się do osoby banującej. Oszukiwanie administratora wiążę się z blacklistą."));
                }

              return;
        }

        if(shouldCache(name, ip))
            cachePlayer(name, ip);


    }


}
