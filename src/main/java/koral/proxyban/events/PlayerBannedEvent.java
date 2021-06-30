package koral.proxyban.events;

import koral.proxyban.model.User;
import net.md_5.bungee.api.plugin.Event;

public class PlayerBannedEvent extends Event{

    private User user;


    public PlayerBannedEvent(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
