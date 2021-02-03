package koral.proxyban.commands;

import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class banIpOnlyCommand extends Command {
    public banIpOnlyCommand() {
        super("proxybaniponly");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String banner = null;
        if(sender instanceof ProxiedPlayer)
            banner = sender.getName();
        else banner = "console";

        if(args.length == 1){
            if(BanFunctions.isIpArg(args[0])) {
                BanFunctions.setBanOnlyIp(banner, args[0]);
                sender.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś ip " + args[0]));
            }
            else sender.sendMessage(new TextComponent(ChatColor.YELLOW + "Wpisałeś niepoprawnie IP, /proxybaniponly służy do banowania tylko, " +
                    "jeśli chcesz zbanować samo ip."));
        }
        if (args.length == 2) {
                BanFunctions.setBanOnlyIp(banner, args[0]);

        }
    }
}
