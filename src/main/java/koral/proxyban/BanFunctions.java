package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.CharMatcher;
import koral.proxyban.model.User;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static koral.proxyban.ProxyBan.bansFile;
public class BanFunctions {
//todo: banowanie przez ip
    /**
     *
     * @param banner nick banujacego
     * @param player osoba ktora bedzie miec bana
     */
    public static void setBan(String banner, String player) {
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
                User user = new User(player, ip, "2100-01-12 23:59", banner, "Administrator nie podal powodu banicji");
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

    /**
     *
     * @param banner - banujacy
     * @param player - osoba zbanowana
     * @param date - data bana
     */
    public static void setBan(String banner, String player, String date) {
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
                    User user = new User(player, ip,  date, banner, "Administrator nie podał powodu bana.");
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

    /**
     *
     * @param banner - osoba banujaca
     * @param player - osoba zbanowana
     * @param date - data jak dlugo gracz ma byc zbanowany
     * @param reason - powod bana
     */
    public static void setBan(String banner, String player, String date, String reason) {
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
                User user = new User(player, ip,  date, banner, reason);
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

    /**
     *
     * @param banner
     * @param player
     * @param reason
     */
    public static void setBanReason(String banner, String player, String reason) {
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
                User user = new User(player, ip, "2100-01-12 23:59", banner, reason);
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


    /**
     *
     * @param name - nick potrzebny do wyszukania gracza
     * @return zwraca calego Usera
     */
    public static User getUserByName(String name) {
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

    /**
     *
     * @param ip - ip potrzebne do wyszukania gracza
     * @return zwraca calego Usera
     */
    public static User getUserByIp(String ip) {
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

    /**
     *
     * @param nick - nick zbanowaego
     * @param playerip - ip zbanowanego
     * @return - zwraca czy gracz jest zbanowany czy nie
     */
    public static boolean isBanned(String nick, String playerip){
        JsonNode gotIp = null;
        JsonNode gotName = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for(int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                gotName = objectNode.get("name");
                gotIp = objectNode.get("ip");
                if(gotName != null && gotName.toString().replace("\"", "").equalsIgnoreCase(nick) || playerip != null && gotIp.toString().replace("\"", "").equalsIgnoreCase(playerip)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    JsonNode playerDate = objectNode.get("expiring");
                    Date date = sdf.parse(playerDate.toString().replace("\"", ""));
                    Date today = new Date();
                    if(date.after(today))
                        return true;
                    else {
                        arrayNode1.remove(i);
                        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
                        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                        return false;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param playerArgs - output gracza
     */
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

    /**
     *
     * @param playerArgs - wpisany specjalny ciag znakow, oznaczajacy tempbana
     * @return zwraca przerobiona date
     */
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

    /**
     *
     * @param arg - input
     * @return - zwraca czy data jest taka, ze poprawnie zostanie wpisany tempban.
     */
    public static boolean isDateArg(String arg){
        CharMatcher digits = CharMatcher.inRange('0','9').precomputed();
        String formated = digits.retainFrom(arg);
        if(arg.equals(formated + "m") || arg.equals(formated + "h") || arg.equals(formated + "d") || arg.equals(formated + "y")){
            return true;
        }
        else return false;
    }

    public static boolean isIpArg(String arg ){

        return false;
    }
}
