package koral.proxyban.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;


public class ProxyTeleport extends Command {
    public ProxyTeleport(){
        super("proxytp","sectorbungee.proxytp");
    }

    @Override
    public void execute(CommandSender sender,String[] args){
        if ( sender instanceof ProxiedPlayer ) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if ( target != null ) {
                Server server = ProxyServer.getInstance().getPlayer(args[0]).getServer();
                if ( server.getInfo().getName().equals((( ProxiedPlayer ) sender).getServer().getInfo().getName()) ) {
                    sender.sendMessage(new TextComponent("Ten gracz jest na tym serwerze co ty, uzyj komendy minecraft:tp"));
                    return;
                }
                ((ProxiedPlayer) sender).connect(server.getInfo());

            } else target.sendMessage(new TextComponent(ChatColor.RED + "Taki gracz nie jest online w sieci"));

        }
    }
}