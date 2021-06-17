package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import koral.proxyban.model.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static koral.proxyban.ProxyBan.bansFile;

public class BanFunctions {

    final static String perm = "2101-01-12 23:59";
    final static String noReason = "Administrator nie podał powodu zbanowania.";

    private static void writeJson(File file , String json) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter (new FileOutputStream (file) , StandardCharsets.UTF_8);
            writer.write (json);
            writer.flush ();
            writer.close ();
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }
/*


    public static void setBanNick(String banner, String player) {
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (player , null)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);
                    //tutaj bedzie && i wtedy jak nick i ip nie bedzie sie zgadzalo, to doda nowego bana
                    if (user.getName ().equalsIgnoreCase (player)) {
                        user.setExpiring (perm);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (player , null , perm , banner , noReason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }

            if (json != null) writeJson (bansFile , json);

        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public static void setBanNick(String banner, String player, String date){
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;

        try {
            if (isBanned (player , null)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getName ().equalsIgnoreCase (player)) {
                        user.setExpiring (date);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (player , null , date , banner , noReason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }

            if (json != null) writeJson (bansFile , json);
        } catch (IOException ex) {
            ex.printStackTrace ();
        }

    }

    public static void setBanNick(String banner, String player, String date, String reason){
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (player , null)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getName ().equalsIgnoreCase (player)) {
                        user.setExpiring (date);
                        user.setReason (reason);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (player , null , date , banner , reason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            if (json != null) writeJson (bansFile , json);

        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public static void setBanNickReason(String banner, String player, String reason){
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;

        try {
            if (isBanned (player , null)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getName ().equalsIgnoreCase (player)) {
                        user.setExpiring (perm);
                        user.setReason (reason);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (player , null , perm , banner , reason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            if (json != null) writeJson (bansFile , json);
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

 */
//jest zbanowany,
    public static void setBan(String banner , String player) {
        String ip = CacheFunctions.getCacheIp (player);
            if (isBanned (player , ip))
                rewriteBan(player, ip, new User(player, ip, perm, banner, noReason));
             else
                writeBan(new User(player, ip, perm, banner, noReason));


    }


    public static void setBan(String banner , String player , String date) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip)) {
            rewriteBan(player, ip, new User(player, ip, date, banner, noReason));
        } else
            writeBan(new User(player, ip, date, banner, noReason));
    }

    public static void setBan(String banner , String player , String date , String reason) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip))
            rewriteBan(player, ip, new User(player, ip, date, banner, reason));
         else
            writeBan(new User(player, ip, date, banner, reason));
    }


    public static void setBanReason(String banner , String player , String reason) {
        String ip = CacheFunctions.getCacheIp (player);
        if (isBanned (player , ip))
            rewriteBan(player, ip, new User(player, ip, perm, banner, reason));
         else
            writeBan(new User(player, ip, perm, banner, reason));
    }

    /*

    public static void setBanOnlyIp(String banner , String playerIp) {
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (null , playerIp)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getIp ().equalsIgnoreCase (playerIp)) {
                        user.setExpiring (perm);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (null , playerIp , perm , banner , noReason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            if (json != null) writeJson (bansFile , json);
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public static void setBanOnlyIp(String banner , String playerIp , String date) {
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (null , playerIp)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getIp ().equalsIgnoreCase (playerIp)) {
                        user.setExpiring (date);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (null , playerIp , date , banner , noReason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            OutputStreamWriter writer = new OutputStreamWriter (new FileOutputStream (bansFile) , StandardCharsets.UTF_8);
            writer.write (json);
            writer.flush ();
            writer.close ();
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public static void setBanOnlyIp(String banner , String playerIp , String date , String reason) {
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (null , playerIp)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getIp ().equalsIgnoreCase (playerIp)) {

                        user.setExpiring (date);
                        user.setReason (reason);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (null , playerIp , date , banner , reason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            OutputStreamWriter writer = new OutputStreamWriter (new FileOutputStream (bansFile) , StandardCharsets.UTF_8);
            writer.write (json);
            writer.flush ();
            writer.close ();
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public static void setBanOnlyIpReason(String banner , String playerIp , String reason) {
        ObjectMapper objectMapper = new ObjectMapper ();
        String json = null;
        try {
            if (isBanned (null , playerIp)) {
                ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
                for (int i = 0 ; i < array.size () ; i++) {
                    User user = objectMapper.readValue (array.get (i).toString () , User.class);

                    if (user.getIp ().equalsIgnoreCase (playerIp)) {
                        user.setExpiring (perm);
                        array.remove (i);
                        array.insertPOJO (i , user);
                        json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (array);
                        break;
                    }
                }
            } else {
                ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
                User user = new User (null , playerIp , perm , banner , reason);
                arrayNode1.addPOJO (user);
                json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
            }
            if (json != null) {
                OutputStreamWriter writer = new OutputStreamWriter (new FileOutputStream (bansFile) , StandardCharsets.UTF_8);
                writer.write (json);
                writer.flush ();
                writer.close ();
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }


     */

    public static User getUserByName(String name) {
        User user = null;
        ObjectMapper objectMapper = new ObjectMapper ();
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
        } catch (JsonParseException e) {
            e.printStackTrace ();
        } catch (JsonMappingException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return user;
    }


    public static User getUserByIp(String ip) {
        User user = null;
        ObjectMapper objectMapper = new ObjectMapper ();
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
        } catch (JsonParseException e) {
            e.printStackTrace ();
        } catch (JsonMappingException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return user;
    }

//jezeli ip jest rozne,
    public static boolean isBanned(String nick , String playerip) {
        JsonNode gotIp;
        JsonNode gotName;
        ObjectMapper objectMapper = new ObjectMapper ();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < arrayNode1.size () ; i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get (i);
                gotName = objectNode.get ("name");
                gotIp = objectNode.get ("ip");
                if (gotName != null && gotName.asText ().equalsIgnoreCase (nick) ||
                        gotIp != null && gotIp.asText ().equalsIgnoreCase (playerip)) {
                    SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
                    JsonNode playerDate = objectNode.get ("expiring");
                    Date date = sdf.parse (playerDate.asText ());
                    Date today = new Date ();
                    if (date.after (today))
                        return true;
                    else {
                        arrayNode1.remove (i);
                        String json = objectMapper.writerWithDefaultPrettyPrinter ().writeValueAsString (arrayNode1);
                        OutputStreamWriter writer = new OutputStreamWriter (new FileOutputStream (bansFile) , StandardCharsets.UTF_8);
                        writer.write (json);
                        writer.flush ();
                        writer.close ();
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
        ObjectMapper objectMapper = new ObjectMapper ();
        boolean isEdited = false;
        try {
            ArrayNode array = objectMapper.readValue (bansFile , ArrayNode.class);
            for (int i = 0 ; i < array.size () ; i++) {
                ObjectNode objectNode = (ObjectNode) array.get (i);
                if (objectNode.get ("name").asText ().equalsIgnoreCase (playerArgs) || objectNode.get ("ip").asText ().equalsIgnoreCase (playerArgs)) {
                    array.remove (i);
                    i--;
                    isEdited = true;
                }
            }
            if(isEdited) {
                writeJson(bansFile,objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array));
                return true;
            }
        } catch (JsonParseException e) {
            e.printStackTrace ();
        } catch (JsonMappingException e) {
            e.printStackTrace ();
        } catch (IOException exception) {
            exception.printStackTrace ();
        }
        return false;
    }

    public static List<String> getBannedPlayers() {
        JsonNode gotName;
        ObjectMapper objectMapper = new ObjectMapper ();
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
        int czas = Integer.valueOf (digits.retainFrom (playerArgs));
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
        if (arg.equals (formated + "m") || arg.equals (formated + "h") || arg.equals (formated + "d") || arg.equals (formated + "y")) {
            return true;
        } else return false;
    }

    public static boolean isIpArg(String arg) {
        if (Pattern.matches ("[0-9][0-9.]*[0-9]" , arg)) // LICZBA LICZBA .LICZBA
            return true;
        else
            return false;
    }


    private static void rewriteBan(String player, String ip, User user){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode array = objectMapper.readValue(bansFile,ArrayNode.class);
            for ( int i = 0 ; i < array.size() ; i++ ) {
                User search = objectMapper.readValue(array.get(i).toString(),User.class);

                if ( search.getName().equalsIgnoreCase(player) && search.getIp() != null && search.getIp().equalsIgnoreCase(ip) ) {
                    array.remove(i);
                    array.insertPOJO(i,user);
                    writeJson(bansFile,objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array));
                    return;
                }
            }

         nullNickOrIpBeforeUser(user);
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
    }

    private static void writeBan(User user){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode array = objectMapper.readValue(bansFile,ArrayNode.class);
            array.addPOJO(user);
            writeJson(bansFile,objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array));
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
    }
    //gdy nick bedzie nullem, lub ip bedzie nullem, to osoba jest zbanowana, ale przechodzi przez bramke wiec musi ustawic bana
    private static void nullNickOrIpBeforeUser(User newUser){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode array = objectMapper.readValue(bansFile,ArrayNode.class);
            String json = null;
            for ( int i = 0 ; i < array.size() ; i++ ) {
                User user = objectMapper.readValue(array.get(i).toString(),User.class);
                //tutaj bedzie && i wtedy jak nick i ip nie bedzie sie zgadzalo, to doda nowego bana

                //gdy gosc jest banowany na nick, a jego ip jest nullem

                if ( user.getName() == null ) {
                    array.addPOJO(newUser);
                    json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array);
                    break;
                }
                if ( user.getIp() == null ) {
                    array.addPOJO(newUser);
                    json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array);
                    break;
                }
            }
            writeJson(bansFile,json);
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
    }

    private boolean nickOrIpHaveMoreBans(String nick, String ip){
        int banNick = 0;
        int banIp = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode array = objectMapper.readValue(bansFile,ArrayNode.class);
            for ( int i = 0 ; i < array.size() ; i++ ){
                User user = objectMapper.readValue(array.get(i).toString(),User.class);

                if(user.getName()!= null && user.getName().equalsIgnoreCase(nick)){
                    banNick++;
                }
                if(user.getIp()!= null && user.getIp().equalsIgnoreCase(ip)){
                    banIp++;
                }
                if(banNick > 1 || banIp > 1) return true;
            }
        }catch ( IOException ex ){
            ex.printStackTrace();
        }

        return false;
    }

}
