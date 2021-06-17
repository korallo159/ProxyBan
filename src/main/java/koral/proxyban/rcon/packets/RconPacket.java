package koral.proxyban.rcon.packets;

public class RconPacket{

    private int requestId;
    private PacketType type;
    private String message;

    public RconPacket(int requestId,PacketType type, String message){
        this.requestId = requestId;
        this.type = type;
        this.message = message;
    }

    public int getRequestId(){
        return requestId;
    }

    public PacketType getType(){
        return type;
    }

    public String getMessage(){
        return message;
    }
}
