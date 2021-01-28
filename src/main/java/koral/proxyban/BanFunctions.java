package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import koral.proxyban.model.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static koral.proxyban.ProxyBan.bansFile;
public class BanFunctions {
    public static void setBan(ProxiedPlayer banner, String player) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        String ip = CacheFunctions.getCacheIp(player);
    try {
        if (isBanned(player, ip)) {
                ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
                for (int i = 0; i < arrayNode2.size(); i++) {
                    ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);

                    if (objectNode.get("name").toString().replace("\"", "").equalsIgnoreCase(player)) {
                        User user = objectMapper.readValue(objectNode.toString(), User.class);
                        user.setExpiring("2101-01-12 23:59");
                        arrayNode2.remove(i);
                        arrayNode2.insertPOJO(i, user);
                        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                        break;
                    }
                }
            }
        else {
                ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
                User user = new User(player, ip, "2100-01-12 23:59", banner.getName(), "Administrator nie podal powodu banicji");
                arrayNode1.addPOJO(user);
                json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
        }
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
        writer.write(json);
        writer.flush();
        writer.close();
        }
            catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    //TODO: Wykryc duplikacje bana i zastapic date
    public static void setBan(ProxiedPlayer banner, String player, String date) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        String ip = CacheFunctions.getCacheIp(player);
        try {
            if (isBanned(player, ip)) {
                   ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
                    for (int i = 0; i < arrayNode2.size(); i++) {
                        ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);

                        if (objectNode.get("name").toString().replace("\"", "").equalsIgnoreCase(player)) {
                            User user = objectMapper.readValue(objectNode.toString(), User.class);
                            user.setExpiring(date);
                            arrayNode2.remove(i);
                            arrayNode2.insertPOJO(i, user);
                            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                            break;
                        }
                    }
            }
            else {
                    ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
                    User user = new User(player, ip,  date, banner.getName(), "Administrator nie podał powodu bana.");
                    arrayNode1.addPOJO(user);
                    json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }



    public static void setBan(ProxiedPlayer banner, String player, String date, String reason) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        String ip = CacheFunctions.getCacheIp(player);
        try {
            if (isBanned(player, ip)) {
                ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
                for (int i = 0; i < arrayNode2.size(); i++) {
                    ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);

                    if (objectNode.get("name").toString().replace("\"", "").equalsIgnoreCase(player)) {
                        User user = objectMapper.readValue(objectNode.toString(), User.class);
                        user.setExpiring(date);
                        user.setReason(reason);
                        arrayNode2.remove(i);
                        arrayNode2.insertPOJO(i, user);
                        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                        break;
                    }
                }
            }
            else {
                ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
                User user = new User(player, ip,  date, banner.getName(), reason);
                arrayNode1.addPOJO(user);
                json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public static void setBanReason(ProxiedPlayer banner, String player, String reason) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        String ip = CacheFunctions.getCacheIp(player);
        try {
            if (isBanned(player, ip)) {
                ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
                for (int i = 0; i < arrayNode2.size(); i++) {
                    ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);

                    if (objectNode.get("name").toString().replace("\"", "").equalsIgnoreCase(player)) {
                        User user = objectMapper.readValue(objectNode.toString(), User.class);
                        user.setExpiring("2101-01-12 23:59");
                        arrayNode2.remove(i);
                        arrayNode2.insertPOJO(i, user);
                        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                        break;
                    }
                }
            }
            else {
                ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
                User user = new User(player, ip, "2100-01-12 23:59", banner.getName(), reason);
                arrayNode1.addPOJO(user);
                json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    public static User getBanDetailsByName(String name) {
        User user = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("name");
                if(id.toString().replace("\"", "").equalsIgnoreCase(name)){
                    Reader reader = new StringReader(objectNode.toString());
                    user = objectMapper.readValue(reader, User.class);
                    break;
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getBanDetailsByIp(String ip) {
        User user = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("ip");
                if(id.toString().replace("\"", "").equalsIgnoreCase(ip)){
                    Reader reader = new StringReader(objectNode.toString());
                    user = objectMapper.readValue(reader, User.class);
                    break;
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean isBanned(String nick, String playerip){
        String gotIp = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for(int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("name");
                JsonNode ip = objectNode.get("ip");
                String gotName = id.toString().replace("\"", "");
                try {
                    gotIp = ip.toString().replace("\"", "");
                }
                catch (Exception e){
                }
                if(gotName.equalsIgnoreCase(nick) || playerip != null && playerip.equalsIgnoreCase(gotIp)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    JsonNode playerDate = objectNode.get("expiring");
                    Date date = sdf.parse(playerDate.toString().replace("\"", ""));
                    Date today = new Date();
                    if(date.after(today))
                        return true;
                    else {
                        return false;      //todo: zrobic zeby usuwalo fielda jak juz nie ma bana
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void removeBan(String playerArgs){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode2.size(); i++){
                ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);
                if(objectNode.get("name").toString().replace("\"", "").equalsIgnoreCase(playerArgs)
                        || objectNode.get("ip").toString().replace("\"", "").equalsIgnoreCase(playerArgs)){
                    arrayNode2.remove(i);
                    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String setTempBanDate(String playerArgs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date today = new Date();
        Date newDate = null;
        String formatedNewDate;
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
            newDate = DateUtils.addYears(today, czas);
        }
        formatedNewDate = sdf.format(newDate);
        return formatedNewDate;
    }

    public static boolean isDateArg(String arg){
        CharMatcher digits = CharMatcher.inRange('0','9').precomputed();
        String formated = digits.retainFrom(arg);
        if(arg.equals(formated + "m") || arg.equals(formated + "h") || arg.equals(formated + "d") || arg.equals(formated + "y")){
            return true;
        }
        else return false;
    }
}
