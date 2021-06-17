package koral.proxyban.commands;

import koral.proxyban.BanFunctions;
import koral.proxyban.model.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class BanDetails extends Command implements TabExecutor {

    public BanDetails() {
        super("proxybandetails", "proxyban.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length == 0){
            sender.sendMessage(new TextComponent("§9§lPoprawne użycie: proxybandetails §f§l<nick osoby zbanowanej>"));
            return;
        }

        User user = BanFunctions.getUserByName(args[0]);
        if(user == null) sender.sendMessage(new TextComponent(ChatColor.RED + "Taki gracz nie ma bana"));
        else{
            sender.sendMessage(new TextComponent("§9§lInformacje o zbanowanym graczu §f§l" + user.getName()));
            sender.sendMessage(new TextComponent("§9§lOsoba banująca: §f§l" + user.getAdmin()));
            sender.sendMessage(new TextComponent("§9§lPowód bana: §f§l" + user.getReason()));
            sender.sendMessage(new TextComponent("§9§lWygasa w: §f§l" + user.getExpiring()));
            sender.sendMessage(new TextComponent("§9§lIP: §f§l" + user.getIp()));
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
