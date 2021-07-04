package koral.proxyban;

import com.fasterxml.jackson.databind.node.ArrayNode;
import koral.proxyban.model.Cache;
import java.io.IOException;
import static koral.proxyban.ProxyBan.cacheFile;

public class CacheFunctions extends ProxyWriter {
    public static void cachePlayer(String name, String ip){

        Cache cache = new Cache(name, ip);
        try {
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++) {
                Cache search = objectMapper.readValue(arrayNode.get(i).toString(), Cache.class);
                if(search.getName().contains(name) && !search.getIp().contains(ip)) {
                    arrayNode.remove(i);
                    arrayNode.insertPOJO(i, cache);
                    writeJson(cacheFile, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
                    return;
                }
            }
            arrayNode.addPOJO(cache);

            writeJson(cacheFile, objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean shouldCache(String name, String ip){
        try{
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++){
                Cache search = objectMapper.readValue(arrayNode.get(i).toString(), Cache.class);
                if(search.getName().contains(name) && search.getIp().contains(ip))
                    return false;
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String getCacheIp(String name){
        String ip = null;
        try{
            ArrayNode arrayNode = objectMapper.readValue(cacheFile, ArrayNode.class);
            for(int i =0; i< arrayNode.size(); i++){
                Cache search = objectMapper.readValue(arrayNode.get(i).toString(), Cache.class);
                if(search.getName().equalsIgnoreCase(name)){
                    ip = search.getIp();
                    break;
                }
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
