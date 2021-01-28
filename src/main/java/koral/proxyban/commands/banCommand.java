package koral.proxyban.commands;
import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


import static koral.proxyban.BanFunctions.isDateArg;
import static koral.proxyban.BanFunctions.setTempBanDate;

public class banCommand extends Command {
    //TODO: banowanie przez samo IP
    public banCommand() {
        super("proxyban", "proxyban.admin");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(args.length == 1) {
                    BanFunctions.setBan(player, args[0]);
                    player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " bez podawania powodu."));

            }
            else if(args.length >= 2 && !isDateArg(args[1])){
                StringBuilder builder = new StringBuilder();
                for(int i = 1; i< args.length; i++)
                    builder.append(args[i]).append(" ");
                BanFunctions.setBanReason(player, args[0], builder.toString());
                player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " za " + builder.toString()));
            }
            else if(args.length == 2){
                        BanFunctions.setBan(player, args[0], setTempBanDate(args[1]));
                        player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1])));
            }
            else if(args.length >= 3) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++)
                        builder.append(args[i]).append(" ");
                    BanFunctions.setBan(player, args[0], setTempBanDate(args[1]), builder.toString());
                    player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1]) + " za "
                            + builder.toString()));
            }
        }
    }





}
