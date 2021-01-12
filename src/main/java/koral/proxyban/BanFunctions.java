package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static koral.proxyban.ProxyBan.bansFile;

public class BanFunctions {
    //TODO: ROZNE BANY
    private void setBan() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            ObjectNode user1 = objectMapper.createObjectNode();
            user1.put("uuid", "6c1967d0438f11ebb3780242ac130002");
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
    public static HashMap<String, String> getBanDetailsByUUID(String uuid) {
        HashMap<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayNode arrayNode1 = objectMapper.readValue(bansFile, ArrayNode.class);
            for (int i = 0; i < arrayNode1.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode1.get(i);
                JsonNode id = objectNode.get("uuid");
                if(id.toString().replace("\"", "").equals(uuid)){
                    JsonNode nick = objectNode.get("nick");
                    JsonNode expiring = objectNode.get("expiring");
                    JsonNode admin = objectNode.get("admin");
                    JsonNode reason = objectNode.get("reason");
                    map.put("nick", nick.toString().replace("\"", ""));
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

}
