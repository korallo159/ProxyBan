package koral.proxyban.model;

public class Cache {
    private String name;
    private String ip;

    public Cache(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public Cache(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
