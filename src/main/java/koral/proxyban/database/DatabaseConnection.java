package koral.proxyban.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import koral.proxyban.BanFunctions;
import koral.proxyban.ProxyBan;
import koral.proxyban.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection{
    
    public static HikariDataSource hikari;

    public static void configureDbConnection() {
        ProxyBan.getProxyBan().config.getString("password");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(ProxyBan.getProxyBan().config.getString("db.jdbcurl"));
        hikariConfig.setMaxLifetime(600000); // zeby uniknac wiekszy lifetime hikari niz mysql
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setMaximumPoolSize(50);
        hikariConfig.addDataSourceProperty("user", ProxyBan.getProxyBan().config.getString("db.username"));
        hikariConfig.addDataSourceProperty("password", ProxyBan.getProxyBan().config.getString("db.password"));
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" ); //pozwala lepiej wspolpracowac z prepared statements
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        hikari = new HikariDataSource(hikariConfig);
        createTable();
    }

    public static void createTable() {
        try (Connection connection = hikari.getConnection()) {
                String create = "CREATE TABLE IF NOT EXISTS Players(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, NICK TINYTEXT, IP TINYTEXT, EXPIRING TINYTEXT, ADMIN TINYTEXT, REASON TINYTEXT)";
                try (PreparedStatement statement = connection.prepareStatement(create)) {
                    statement.execute();
                } catch ( SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDbDifferent(){
        List<String> nicks = new ArrayList<>();
        try(Connection connection = hikari.getConnection()) {
            ResultSet resultSet;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PLAYERS");
            resultSet = statement.executeQuery();

            while(resultSet.next())
                if(resultSet.getString("NICK") != null) nicks.add(resultSet.getString("NICK"));

            if(!BanFunctions.getBannedPlayers().equals(nicks)) return true;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return false;
    }

    public static void updateBan(User user){
        try(Connection connection = hikari.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Players (NICK, IP, EXPIRING, ADMIN, REASON) VALUES (?, ?, ?, ?, ?)")){
                statement.setString(1, user.getName());
                statement.setString(2, user.getIp());
                statement.setString(3, user.getExpiring());
                statement.setString(4, user.getAdmin());
                statement.setString(5, user.getReason());
                statement.execute();
            } catch ( SQLException e ){
                e.printStackTrace();
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public static void deleteRecords(){
        try(Connection connection = hikari.getConnection()) {
            connection.prepareStatement("DELETE FROM Players").execute();

        } catch ( SQLException throwables ) {
            throwables.printStackTrace();
        }
    }

    public static void reloadBansInDatabase(){
        BanFunctions.getBannedPlayers().forEach(player -> {
            User user = BanFunctions.getUserByName(player);
            try(Connection connection = hikari.getConnection()) {
                ResultSet resultSet;
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Players WHERE NICK=?");
                statement.setString(1, user.getName());
                resultSet = statement.executeQuery();
                while(resultSet.next()) if(!resultSet.getString("NICK").isEmpty()) return;

                updateBan(user);
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        });
    }

    public static boolean isDb2Different(){
        List<User> bans = new ArrayList<>();
        try(Connection connection = hikari.getConnection()) {
            ResultSet resultSet;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM PLAYERS");
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                User user = new User(resultSet.getString("NICK"),
                        resultSet.getString("IP"),
                        resultSet.getString("EXPIRING"),
                        resultSet.getString("ADMIN"),
                        resultSet.getString("REASON"));
                bans.add(user);
            }


        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return false;
    }

}
