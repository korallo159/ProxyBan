package koral.proxyban.commands;

import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class unbanCommand extends Command {
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
}
