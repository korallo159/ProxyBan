package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.listeners.ServerConnect;
import koral.proxyban.model.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import static koral.proxyban.ProxyBan.bansFile;
public class BanFunctions {
    //TODO: Wykryc duplikacje bana
    public static void setBan(ProxiedPlayer banner, String player) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
    try {
        if (isBanned(player, "")) {
            try {
                ArrayNode arrayNode2 = objectMapper.readValue(bansFile, ArrayNode.class);
                for (int i = 0; i < arrayNode2.size(); i++) {
                    ObjectNode objectNode = (ObjectNode) arrayNode2.get(i);

                    if (objectNode.get("name").toString().replace("\"", "").equals(player)) {
                        User user = objectMapper.readValue(objectNode.toString(), User.class);
                        user.setExpiring("2101-01-12 23:59");
                        arrayNode2.remove(i);
                        arrayNode2.insertPOJO(i, user);
                        json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode2);
                        break;
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        else {
            try {
                ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
                User user = new User(player, "2100-01-12 23:59", banner.getName(), "Administrator nie podal powodu banicji");
                try {
                    String hostname = CacheFunctions.getCacheIp(player); ////Sprawdzic w cache czy gracz kiedys gral
                    System.out.println(hostname);
                    if (hostname != null)
                        user.setIp(hostname);
                } catch (Exception e) {
                    ProxyServer.getInstance().getLogger().warning("Taki gracz nigdy nie był na serwerze - zbanowano bez ip!");
                }
                arrayNode1.addPOJO(user);

                json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
        writer.write(json);
        writer.flush();
        writer.close();
    }
    catch (Exception ex){
        ex.printStackTrace();
    }

    }

    //TODO: Wykryc duplikacje bana i zastapic date
    public static void setBan(ProxiedPlayer banner, String player, String date) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            User user = new User(player, date, banner.getName(), "Administrator nie podal powodu banicji");
            try {
                String hostname = ProxyBan.getProxyBan().getProxy().getPlayer(player).getAddress().getHostName();   //Sprawdzic w cache czy gracz kiedys gral
                if(hostname != null)
                    user.setIp(ProxyBan.getProxyBan().getProxy().getPlayer(player).getAddress().getHostName());
            }
            catch (Exception e){
                ProxyServer.getInstance().getLogger().warning("Gracz nie był online - zbanowano bez ip!");
            }
            arrayNode1.addPOJO(user);

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bansFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void setBan() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            ObjectNode user1 = objectMapper.createObjectNode();
            user1.put("name", "moral");
            user1.put("ip", "178.36.214.12");
            user1.put("expiring", "2024.01.12");
            user1.put("admin", "koral");
            user1.put("reason", "Uzywanie cheatow na kazdym serwerze");
            arrayNode1.add(user1);

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode1);
            FileWriter fileWriter = new FileWriter(bansFile);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static HashMap<String, String> getBanDetailsByName(String name) {
        HashMap<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("name");
                if(id.toString().replace("\"", "").equals(name)){
                    JsonNode nick = objectNode.get("name");
                    JsonNode ip = objectNode.get("ip");
                    JsonNode expiring = objectNode.get("expiring");
                    JsonNode admin = objectNode.get("admin");
                    JsonNode reason = objectNode.get("reason");
                    map.put("name", nick.toString().replace("\"", ""));
                    try {
                        map.put("ip", ip.toString().replace("\"", ""));
                    }
                    catch (Exception e){
                    }
                    map.put("expiring", expiring.toString().replace("\"", ""));
                    map.put("admin", admin.toString().replace("\"", ""));
                    map.put("reason", reason.toString().replace("\'", ""));
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
        return map;
    }

    public static HashMap<String, String> getBanDetailsByIp(String ip) {
        HashMap<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("ip");
                if(id.toString().replace("\"", "").equals(ip)){
                    JsonNode nick = objectNode.get("name");
                    JsonNode expiring = objectNode.get("expiring");
                    JsonNode admin = objectNode.get("admin");
                    JsonNode reason = objectNode.get("reason");
                    map.put("name", nick.toString().replace("\"", ""));
                    map.put("expiring", expiring.toString().replace("\"", ""));
                    map.put("admin", admin.toString().replace("\"", ""));
                    map.put("reason", reason.toString().replace("\'", ""));
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
        return map;
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
                if(gotName.equals(nick) || playerip.equals(gotIp)){
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

}
