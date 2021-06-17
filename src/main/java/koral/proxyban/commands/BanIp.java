package koral.proxyban.commands;

import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static koral.proxyban.BanFunctions.setTempBanDate;

public class BanIp extends Command {
    public BanIp() {
        super("proxybaniponly");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        /*
        String banner;
        if(sender instanceof ProxiedPlayer)
            banner = sender.getName();
        else
            banner = "console";
        if(args.length == 0 )
            sender.sendMessage(new TextComponent("§4Użycie: /proxyban §f<nick> §f<data> §f<powod> §4lub /proxyban §f<nick> §f<powód>" ));

        if(args.length == 1 && !BanFunctions.isIpArg(args[0])){
            sender.sendMessage(new TextComponent(ChatColor.YELLOW + "Wpisałeś niepoprawnie IP, /proxybaniponly służy do banowania tylko, " +
                    "gdy chcesz zbanować samo ip."));
            return;
        }


        if(args.length == 1){
                BanFunctions.setBanOnlyIp(banner, args[0]);
                sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś ip " + args[0]));
        }
        else if (args.length >= 2 && !BanFunctions.isDateArg(args[1])) {
            StringBuilder builder = new StringBuilder();
            for(int i = 1; i< args.length; i++)
                builder.append(args[i]).append(" ");
            BanFunctions.setBanOnlyIpReason(banner, args[0], builder.toString());
            sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś ip" + args[0] + " za " + builder.toString()));
        }
        else if(args.length == 2){
            BanFunctions.setBanOnlyIp(banner, args[0], BanFunctions.setTempBanDate(args[1]));
            sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś ip" + args[0] + " do " + BanFunctions.setTempBanDate(args[1])));
        }
        else if(args.length >= 3){
            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < args.length; i++)
                builder.append(args[i]).append(" ");
            BanFunctions.setBanOnlyIp(banner, args[0], setTempBanDate(args[1]), builder.toString());
            sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś ip " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1]) + " za "
                    + builder.toString()));
        }

         */
    }

}
