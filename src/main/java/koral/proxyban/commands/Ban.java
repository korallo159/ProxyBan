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

public class Ban extends Command implements TabExecutor {
    //TODO: banowanie przez samo IP
    public Ban() {
        super("proxyban", "proxyban.admin");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxyBan.getProxyBan().getProxy().getScheduler().runAsync(ProxyBan.getProxyBan(), () -> {
            String banner;
            if(sender instanceof ProxiedPlayer) banner = sender.getName();
            else banner = "console";
            if(args.length == 0 ) sender.sendMessage(new TextComponent("§9§lUżycie: /proxyban §f§l<nick> §f§l<data> §f§l<powod> §9§llub /proxyban §f§l<nick> §f§l<powód>" ));

            if(args.length == 1) {
                BanFunctions.setBan(banner, args[0]);
                sender.sendMessage(new TextComponent("§9§lZbanowałeś gracza §f§l" + args[0] + " §9§lbez podawania powodu."));
                sender.sendMessage(new TextComponent("§9§lIP: §f§l" + BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    String ip = ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).getPendingConnection().getAddress().getAddress().getHostAddress();
                    kickPlayers(ip);
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
                    String ip = ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).getPendingConnection().getAddress().getAddress().getHostAddress();
                    kickPlayers(ip);
                }
            }
            else if(args.length == 2){
                BanFunctions.setBan(banner, args[0], setTempBanDate(args[1]));
                sender.sendMessage(new TextComponent("§9§lZbanowałeś gracza §f§l" + args[0] + "§9§l do §f§l" +setTempBanDate(args[1])));
                sender.sendMessage(new TextComponent("§9§lIP: §f§l" + BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    String ip = ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).getPendingConnection().getAddress().getAddress().getHostAddress();
                    kickPlayers(ip);
                }
            }
            else if(args.length >= 3) {
                StringBuilder builder = new StringBuilder();
                for (int i = 2; i < args.length; i++)
                    builder.append(args[i]).append(" ");
                BanFunctions.setBan(banner, args[0], setTempBanDate(args[1]), builder.toString());
                sender.sendMessage(new TextComponent("§9§lZbanowałeś gracza §f§l" + args[0] + "§9§l do §f§l" + setTempBanDate(args[1]) + "§9§l za §f§l"
                        + builder));
                sender.sendMessage(new TextComponent("§9§lIP: §f§l"+ BanFunctions.getUserByName(args[0]).getIp()));
                if(ProxyBan.getProxyBan().getProxy().getPlayer(args[0]) != null) {
                    String ip = ProxyBan.getProxyBan().getProxy().getPlayer(args[0]).getPendingConnection().getAddress().getAddress().getHostAddress();
                    kickPlayers(ip);
                }
            }
        });

    }


    void kickPlayers(String ip){
        ProxyBan.getProxyBan().getProxy().getPlayers().forEach(proxiedPlayer -> {
            if(proxiedPlayer.getPendingConnection().getAddress().getAddress().getHostAddress().equals(ip))
                if(proxiedPlayer.isConnected())
                    proxiedPlayer.disconnect(new TextComponent("§9§lZostałeś zbanowany. Relognij, aby zobaczyć dlaczego."));
        });
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> players = new HashSet<>();
        if(args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(search)) {
                    players.add(player.getName());
                }
            }
            return players;
        }
      if(args.length == 2){
          List<String> complete1 = new ArrayList<>();
          complete1.add("30m");
          complete1.add("1h");
          complete1.add("8d");
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
