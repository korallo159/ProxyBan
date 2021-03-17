package koral.proxyban.commands;
import koral.proxyban.BanFunctions;
import koral.proxyban.ProxyBan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static koral.proxyban.BanFunctions.isDateArg;
import static koral.proxyban.BanFunctions.setTempBanDate;

public class banCommand extends Command implements TabExecutor {
    //TODO: banowanie przez samo IP
    public banCommand() {
        super("proxyban", "proxyban.admin");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        String banner;
        if(sender instanceof ProxiedPlayer)
            banner = sender.getName();
        else
            banner = "console";
        if(args.length == 0 )
            sender.sendMessage(new TextComponent("§4Użycie: /proxyban §f<nick> §f<data> §f<powod> §4lub /proxyban §f<nick> §f<powód>" ));
            if(args.length == 1) {
                    BanFunctions.setBan(banner, args[0]);
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " bez podawania powodu."));
                    sender.sendMessage(new TextComponent(ChatColor.RED + "IP: " + ChatColor.YELLOW + BanFunctions.getUserByName(args[0]).getIp()));
                    if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                        String ip = ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).getPendingConnection().getVirtualHost().getAddress().getHostName();
                        ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).disconnect(new TextComponent(ChatColor.RED + " Zostałeś zbanowany"));

                    }

            }
            else if(args.length >= 2 && !isDateArg(args[1])){
                StringBuilder builder = new StringBuilder();
                for(int i = 1; i< args.length; i++)
                    builder.append(args[i]).append(" ");
                BanFunctions.setBanReason(banner, args[0], builder.toString());
                sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " za " + builder.toString()));
                sender.sendMessage(new TextComponent(ChatColor.RED + "IP: " + ChatColor.YELLOW + BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).disconnect(new TextComponent(ChatColor.RED + " Zostałeś zbanowany"));

                }
            }
            else if(args.length == 2){
                        BanFunctions.setBan(banner, args[0], setTempBanDate(args[1]));
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1])));
                sender.sendMessage(new TextComponent(ChatColor.RED + "IP: " + ChatColor.YELLOW + BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).disconnect(new TextComponent(ChatColor.RED + " Zostałeś zbanowany"));

                }
            }
            else if(args.length >= 3) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++)
                        builder.append(args[i]).append(" ");
                    BanFunctions.setBan(banner, args[0], setTempBanDate(args[1]), builder.toString());
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1]) + " za "
                            + builder.toString()));
                sender.sendMessage(new TextComponent(ChatColor.RED + "IP: " + ChatColor.YELLOW + BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).disconnect(new TextComponent(ChatColor.RED + " Zostałeś zbanowany"));

                }
            }
    }

    void kickMatchingPlayers(String ip) {
        for (ProxiedPlayer player : ProxyBan.getProxyBan().getProxy().getPlayers())
            if (player.getPendingConnection().getVirtualHost().getAddress().getHostName().equals(ip)) {
                ProxyBan.getProxyBan().getProxy().getPlayer(player.getName()).disconnect(new TextComponent(ChatColor.RED + " Zostałeś zbanowany"));
            }
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> players = new HashSet<>();
        if(args.length == 1) {
            final String search = args[0].toLowerCase();
            for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search)) {
                    players.add(player.getName());
                }
            }
            return players;
        }
      if(args.length == 2){
          List<String> complete1 = new ArrayList<>();
          complete1.add("30m");
          complete1.add("15h");
          complete1.add("7d");
          complete1.add("1y");
          return complete1;
      }
        if(args.length == 3){
            List<String> complete2 = new ArrayList<>();
            complete2.add("powód bana");
            return complete2;
        }
      return players;
    }
}
