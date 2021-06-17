package koral.proxyban.rcon;


import koral.proxyban.rcon.rconclient.RconClient;

import java.util.concurrent.ExecutionException;


public class BungeeRcon{
    public static void main(String args[]) throws InterruptedException, ExecutionException{
       new RconClient("137.74.4.197", 26016, "12345q");

    }

}
