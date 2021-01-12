package koral.proxyban;

public class User {
    String uuid;
    String ip;
    String nick;
    String banujacy;
    String expiring;

    public User(){

    }



    public User(String uuid, String nick, String ip, String banujacy, String expiring) {
        this.uuid = uuid;
        this.ip = ip;
        this.nick = nick;
        this.banujacy = banujacy;
        this.expiring = expiring;
    }

    public String getNick() {
        return nick;
    }

    public String getBanujacy() {
        return banujacy;
    }

    public void setBanujacy(String banujacy) {
        this.banujacy = banujacy;
    }
}
