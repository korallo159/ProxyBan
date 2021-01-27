package koral.proxyban.model;

public class User {

    public User(String name, String ip, String expiring, String admin, String reason) {
        this.name = name;
        this.ip = ip;
        this.expiring = expiring;
        this.admin = admin;
        this.reason = reason;
    }

    public User(String name, String expiring, String admin, String reason) {
        this.name = name;
        this.expiring = expiring;
        this.admin = admin;
        this.reason = reason;
    }

    public User(){

    }

    private String name;
    private String ip;
    private String expiring;
    private String admin;
    private String reason;

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

    public String getExpiring() {
        return expiring;
    }

    public void setExpiring(String expiring) {
        this.expiring = expiring;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
