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

public class unbanCommand extends Command implements TabExecutor {
    public unbanCommand() {
        super("proxyunban", "proxyban.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
                if(args.length == 1){
                    BanFunctions.removeBan(args[0]);
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Odbanowałeś gracza " + args[0]));
                }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> players = new HashSet<>();
        if(args.length == 1) {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                players.add(player.getName());
            }
        }
        return players;
    }
}
