package koral.proxyban.commands;


import koral.proxyban.ProxyBan;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoMessage extends Command implements TabExecutor{

    private ScheduledTask autoMessage;

    public AutoMessage(){
        super("proxyam", "proxyban.admin");
        reloadAutoMessage();
    }

    @Override
    public void execute(CommandSender sender,String[] args){
        if(args.length == 0)  {
            sender.sendMessage(new TextComponent("Poprawne użycie: /proxyam <argument>"));
            return;
        }
            switch(args[0]){
                case"reload":
                    reloadAutoMessage();
                    sender.sendMessage(new TextComponent("Config automessage przeładowany"));
                    break;
                case"messages":
                    printMessages(sender);
                    break;
                case"addmessage":
                    StringBuilder builder = new StringBuilder();
                    for(int i =1; i< args.length; i++)
                        builder.append(args[i]).append(" "); builder.setLength(builder.length() - 1);
                    addMessage(builder.toString());

                    sender.sendMessage(new TextComponent("§9§l Dodałeś nową wiadomość do automessage."));
                    break;
                case"removemessages":
                    printRemoveEditor(sender);
                    break;
                case"remove":
                    if(args.length == 2) {
                        removeMessage(args[1]);
                        printRemoveEditor(sender);
                    }
                    else sender.sendMessage(new TextComponent("§9§lWybierz §f§lid §9§lwiadomości którą chcesz usunąć /proxyam messages"));
                    break;
            }
    }

    private void printMessages(CommandSender sender){
        int id = 0;
        for(String s: ProxyBan.config.getStringList("automessage.messages")){
            sender.sendMessage(new TextComponent(TextComponent.fromLegacyText(format("§9§l[§f§l" + id +"§9§l] " +  s))));
            id++;
        }
    }

    private void printRemoveEditor(CommandSender sender){
        int id = 0;
        sender.sendMessage(new TextComponent("§9§lKliknij w wiadomość którą chcesz usunąć, lub użyj §f§l/proxyam remove {ID}"));
        for(String s: ProxyBan.config.getStringList("automessage.messages")){
           TextComponent text =  new TextComponent(TextComponent.fromLegacyText(format("§9§l[§f§l" + id +"§9§l]§f " +  s)));
           text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/proxyam remove " + id));
           text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Kliknij aby usunąć")));
           sender.sendMessage(text);
            id++;
        }
    }

    private void addMessage(String message){
        List<String> list =  ProxyBan.config.getStringList("automessage.messages");
        list.add(message);
        ProxyBan.config.set("automessage.messages", list);
        ProxyBan.getProxyBan().saveConfig();
        reloadAutoMessage();
    }

    private void removeMessage(String id){
        try{
            List<String> messageList = ProxyBan.config.getStringList("automessage.messages");
            messageList.remove(Integer.parseInt(id));
            ProxyBan.config.set("automessage.messages", messageList);
            ProxyBan.getProxyBan().saveConfig();
        } catch (IndexOutOfBoundsException e){

        }

    }
    private void reloadAutoMessage(){
        if(autoMessage != null) autoMessage.cancel();
        ProxyBan.getProxyBan().reloadConfig();
        List<String> messages = ProxyBan.config.getStringList("automessage.messages");
        int interval = ProxyBan.config.getInt("automessage.interval");
        int size = messages.size();
        if(size == 0) return;
        AtomicInteger counter = new AtomicInteger(0);
        autoMessage = ProxyBan.getProxyBan().getProxy().getScheduler().schedule(ProxyBan.getProxyBan(), () -> {
            String message = messages.get(counter.getAndIncrement()); if(counter.get() == size) counter.set(0);
            TextComponent text = new TextComponent(net.md_5.bungee.api.chat.TextComponent.fromLegacyText(format(message)));

            ProxyBan.getProxyBan().getProxy().broadcast(text);
      }, interval, interval, TimeUnit.SECONDS);

    }

    private static final Pattern pattern = Pattern.compile("(?<!\\\\)(&#[a-fA-F0-9]{6})");
    private String format(String message) {
        Matcher matcher = pattern.matcher(message); // Creates a matcher with the given pattern & message

        while (matcher.find()) { // Searches the message for something that matches the pattern
            String color = message.substring(matcher.start(), matcher.end()); // Extracts the color from the message
            System.out.println(color);
            color = color.replace("&", "");
            message = message.replace("&" + color, "" + ChatColor.of(color)); // Places the color in the message
        }

        return ChatColor.translateAlternateColorCodes('&', message); // Returns the message
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender,String[] args){
        return args.length == 1 ? Arrays.asList("reload", "messages", "addmessage", "removemessages") : Arrays.asList("");
    }
}
