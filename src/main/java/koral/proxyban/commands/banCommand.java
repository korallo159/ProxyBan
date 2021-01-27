package koral.proxyban.commands;
import com.google.common.base.CharMatcher;
import koral.proxyban.BanFunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.lang3.time.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class banCommand extends Command {
    public banCommand() {
        super("proxyban", "proxyban.admin");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(args.length == 1) {
                    BanFunctions.setBan(player, args[0]);
                    player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " bez podawania powodu"));

            }
            if(args.length == 2){
                    BanFunctions.setBan(player, args[0], setTempBanDate(args[1]));
                    player.sendMessage(new TextComponent(ChatColor.RED + "Zbanowałeś gracza " + args[0] + " do " + ChatColor.YELLOW + setTempBanDate(args[1])));


            }
            if(args.length == 3){

            }
        }
    }
    public String setTempBanDate(String playerArgs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date today = new Date();
        Date newDate = null;
        CharMatcher digits = CharMatcher.inRange('0','9').precomputed();
        int czas = Integer.valueOf(digits.retainFrom(playerArgs));
        String czasString = String.valueOf(digits.retainFrom(playerArgs));
        if(playerArgs.equals(czasString + "m")){
            newDate = DateUtils.addMinutes(today, czas);
        }
        if(playerArgs.equals(czasString + "h")){
            newDate = DateUtils.addHours(today, czas);
        }
        if(playerArgs.equals(czasString + "d")){
            newDate = DateUtils.addDays(today, czas);
        }
        if(playerArgs.equals(czasString + "y")){
            newDate = DateUtils.addDays(today, czas);
        }

        return sdf.format(newDate);
    }




}
