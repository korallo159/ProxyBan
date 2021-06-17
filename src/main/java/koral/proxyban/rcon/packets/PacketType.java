package koral.proxyban.rcon.packets;

public enum PacketType{


    HANDSHAKE(3), COMMAND(2), PAYLOAD(0);
    private final int value;
    PacketType(final int value){
        this.value = value;

    }
    public int getValue(){
        return value;
    }

    public static PacketType fromInt(int i){
        for(PacketType packetType : PacketType.values()){
            if(packetType.value == i)
                return packetType;
        }
        return null;
    }

}
