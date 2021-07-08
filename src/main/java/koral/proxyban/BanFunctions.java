package koral.proxyban;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import koral.proxyban.events.PlayerBannedEvent;
import koral.proxyban.model.User;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static koral.proxyban.ProxyBan.bansFile;

public class BanFunctions extends ProxyWriter {
    //TODO: work in memory, perfromance test vs writing in file
    final static String perm = "2101-01-12 23:59";
    final static String noReason = "Administrator nie podał powodu zbanowania.";


    public static void setBan(String banner , String player) {
        String ip = CacheFunctions.getCacheIp (player);
            if (isBanned (player , ip))
                rewriteBan(player, ip, new User(player, ip, perm, banner, noReason));
             else
                writeBan(new User(player, ip, perm, banner, noReason));

        ProxyBan.getProxyBan().getProxy().getPluginManager().callEvent(new PlayerBannedEvent(new User(player, ip, perm, banner, noReason)));
    }

    public static void setBan(String banner , String player , String date) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip)) {
            rewriteBan(player, ip, new User(player, ip, date, banner, noReason));
        } else
            writeBan(new User(player, ip, date, banner, noReason));

        ProxyBan.getProxyBan().getProxy().getPluginManager().callEvent(new PlayerBannedEvent(new User(player, ip, date, banner, noReason)));
    }

    public static void setBan(String banner , String player , String date , String reason) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip))
            rewriteBan(player, ip, new User(player, ip, date, banner, reason));
         else
            writeBan(new User(player, ip, date, banner, reason));

         ProxyBan.getProxyBan().getProxy().getPluginManager().callEvent(new PlayerBannedEvent(new User(player, ip, date, banner, reason)));
    }

    public static void setBanReason(String banner , String player , String reason) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip))
            rewriteBan(player, ip, new User(player, ip, perm, banner, reason));
        else
            writeBan(new User(player, ip, perm, banner, reason));
    }

    public static boolean isBanned(String nick , String playerip) {
        try {
            ArrayNode arrayNode = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < arrayNode.size () ; i++) {
                User user = objectMapper.readValue(arrayNode.get(i).toString(), User.class);

                if (user.getName() != null && user.getName().equalsIgnoreCase (nick) ||
                        user.getIp() != null && user.getIp ().equalsIgnoreCase (playerip)) {
                    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
                    Date date = sdf.parse (user.getExpiring());
                    Date today = new Date ();
                    if (date.after (today))
                        return true;
                    else {
                        arrayNode.remove (i);
                        writeJson(bansFile, objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode));
                        return false;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace ();
        }
        return false;
    }

    public static boolean removeBan(String playerArgs) {
        boolean isEdited = false;
        try {
            ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < array.size () ; i++) {
                User user = objectMapper.readValue(array.get(i).toString(), User.class);
                if (user.getName ().equalsIgnoreCase (playerArgs) || user.getIp() != null && user.getIp ().equalsIgnoreCase (playerArgs)) {
                    array.remove (i);
                    i--;
                    isEdited = true;
                }
            }
            if(isEdited) {
                writeJson(bansFile,objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array));
                return true;
            }
        } catch ( IOException e) {
            e.printStackTrace ();
        }
        return false;
    }

    public static User getUserByName(String name) {
        User user = null;
        try {
            ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < array.size () ; i++) {
                ObjectNode objectNode = (ObjectNode) array.get (i);
                JsonNode id = objectNode.get ("name");
                if (id.asText ().equalsIgnoreCase (name)) {
                    Reader reader = new StringReader (objectNode.toString ());
                    user = objectMapper.readValue (reader , User.class);
                    break;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace ();
        }
        return user;
    }

    public static User getUserByIp(String ip) {
        User user = null;
        try {
            ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < arrayNode1.size () ; i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get (i);
                JsonNode id = objectNode.get ("ip");
                if (id.asText ().equalsIgnoreCase (ip)) {
                    Reader reader = new StringReader (objectNode.toString ());
                    user = objectMapper.readValue (reader , User.class);
                    break;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace ();
        }
        return user;
    }

    public static List<String> getBannedPlayers() {
        JsonNode gotName;
        List<String> bannedPlayers = new ArrayList<> ();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < arrayNode1.size () ; i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get (i);
                gotName = objectNode.get ("name");
                if (gotName != null) {
                    bannedPlayers.add (gotName.asText ());
                }
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return bannedPlayers;
    }

    public static String setTempBanDate(String playerArgs) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        Date today = new Date ();
        Date newDate = null;
        String formatedNewDate;
        CharMatcher digits = CharMatcher.inRange ('0' , '9').precomputed ();
        int czas = Integer.parseInt(digits.retainFrom (playerArgs));
        String czasString = String.valueOf (digits.retainFrom (playerArgs));
        if (playerArgs.equals (czasString + "m")) {
            newDate = DateUtils.addMinutes (today , czas);
        }
        if (playerArgs.equals (czasString + "h")) {
            newDate = DateUtils.addHours (today , czas);
        }
        if (playerArgs.equals (czasString + "d")) {
            newDate = DateUtils.addDays (today , czas);
        }
        if (playerArgs.equals (czasString + "y")) {
            newDate = DateUtils.addYears (today , czas);
        }
        formatedNewDate = sdf.format (newDate);
        return formatedNewDate;
    }

    public static boolean isDateArg(String arg) {
        CharMatcher digits = CharMatcher.inRange ('0' , '9').precomputed ();
        String formated = digits.retainFrom (arg);
        return arg.equals(formated + "m") || arg.equals(formated + "h") || arg.equals(formated + "d") || arg.equals(formated + "y");
    }

    public static boolean isIpArg(String arg) {
        return Pattern.matches("[0-9][0-9.]*[0-9]",arg);
    }


}
