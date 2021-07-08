package koral.proxyban;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import koral.proxyban.model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static koral.proxyban.ProxyBan.bansFile;

public abstract class ProxyWriter{

    static ObjectMapper objectMapper = new ObjectMapper();

    static void writeJson(File file,String json) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(file) , StandardCharsets.UTF_8));
            writer.write (json);
            writer.flush();
            writer.close ();
        } catch ( IOException ex) {
            ex.printStackTrace ();
        }
    }

    static void writeBan(User user){
        try {
            ArrayNode array = objectMapper.readValue(bansFile,ArrayNode.class);
            array.addPOJO(user);
            writeJson(bansFile,objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(array));
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
    }

    static void rewriteBan(String player, String ip, User user){
        try {
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

    //gdy nick bedzie nullem, lub ip bedzie nullem, to osoba jest zbanowana, ale przechodzi przez bramke wiec musi ustawic bana
    private static void nullNickOrIpBeforeUser(User newUser){
        try {
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



}
