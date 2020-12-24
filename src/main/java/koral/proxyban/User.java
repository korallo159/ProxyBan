package koral.proxyban;

public class User {

    String uuid;
    String nick;
    String databana;
    String banujacy;
    String wygasa;
    String powod;

    public User(){

    }

    public User(String uuid, String nick, String banujacy) {
        this.uuid = uuid;
        this.nick = nick;
        this.banujacy = banujacy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBanujacy() {
        return banujacy;
    }

    public void setBanujacy(String banujacy) {
        this.banujacy = banujacy;
    }
}
