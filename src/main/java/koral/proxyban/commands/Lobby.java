package koral.proxyban.commands;

import koral.proxyban.ProxyBan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.Random;

public class Lobby extends Command {
    public Lobby() {
        super("lobby", "command.lobby", "hub");
    }

    @Override
    public void execute(CommandSender commandSender,String[] strings) {
        if (commandSender instanceof ProxiedPlayer ) {
            List<String> servers = ProxyBan.config.getStringList("lobby");
            ServerInfo server = ProxyServer.getInstance().getServers().get(servers.get(new Random().nextInt(servers.size())));
            if (server == null)
                commandSender.sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Nie można odnaleźć serwera"));
            else
                ((ProxiedPlayer) commandSender).connect(server);
        } else
            commandSender.sendMessage(TextComponent.fromLegacyText("Musisz być graczem aby tego użyć"));
    }


}