package koral.proxyban.commands;

import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import java.util.HashSet;
import java.util.Set;

public class Unban extends Command implements TabExecutor {
    public Unban() {
        super("proxyunban", "proxyban.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
            if(args.length == 0){
                sender.sendMessage(new TextComponent("§9§lPoprawne użycie: proxyunban §f§l<nick osoby zbanowanej> §9§llub §f§l<ip osoby zbanowanej>"));
                return;
            }
            if (args.length == 1) {
                if (BanFunctions.removeBan(args[0]))
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Odbanowałeś gracza " + args[0]));
                else
                    sender.sendMessage(new TextComponent(ChatColor.RED + " Ta osoba nie jest zbanowana!"));
            }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> players = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (String banned : BanFunctions.getBannedPlayers()) {
                if (banned.toLowerCase().startsWith(search)) {
                    players.add(banned);
                }
            }
        }
        return players;
    }
}
