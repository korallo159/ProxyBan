package koral.proxyban;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import koral.proxyban.model.Cache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import static koral.proxyban.ProxyBan.cacheFile;

public class CacheFunctions {
    public static void cachePlayer(String name, String ip){
        ObjectMapper mapper = new ObjectMapper();
        Cache cache = new Cache(name, ip);
        try {
            ArrayNode arrayNode = mapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++) {
                ObjectNode objectNode = (ObjectNode) arrayNode.get(i);
                if(objectNode.get("name").asText().contains(name) && !objectNode.get("ip").asText().contains(ip)) {
                    arrayNode.remove(i);
                    arrayNode.insertPOJO(i, cache);
                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cacheFile), StandardCharsets.UTF_8);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                    return;
                }
            }

            arrayNode.addPOJO(cache);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cacheFile), StandardCharsets.UTF_8);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    static boolean isChangedIp(String name, String ip){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++){
                ObjectNode objectNode = (ObjectNode) arrayNode.get(i);
                if(objectNode.get("name").asText().contains(name) && !objectNode.get("ip").asText().contains(ip)){

                    return true;
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public static boolean shouldCache(String name, String ip){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++){
                ObjectNode objectNode = (ObjectNode) arrayNode.get(i);
                if(objectNode.get("name").asText().contains(name) && objectNode.get("ip").asText().contains(ip)){

                    return false;
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public static String getCacheIp(String name){
        ObjectMapper objectMapper = new ObjectMapper();
        String ip = null;
        try{
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++){
                ObjectNode objectNode = (ObjectNode) arrayNode.get(i);
                if(objectNode.get("name").asText().equalsIgnoreCase(name)){
                    ip = objectNode.get("ip").asText();
                    break;
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return ip;
    }
}
