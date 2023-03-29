package com.eleriummc.BFriends.storage;

import com.eleriummc.BFriends.Main;

public class DBConnection {

    public static Database sql;

    private static String host;
    private static int port;
    private static String db;
    private static String user;
    private static String pass;
    private static boolean isOpen = false;

    public static void init() {
        host = Main.configuration.getString("mysql-host");
        port = Main.configuration.getInt("mysql-port");
        pass = Main.configuration.getString("mysql-password");
        db = Main.configuration.getString("mysql-database");
        user = Main.configuration.getString("mysql-username");
        sql = new MySQL(Main.pluginInstance.getLogger(), "Establishing MySQL Connection...", host, port, user, pass, db);
        if (((MySQL)sql).open() == null) {
            Main.pluginInstance.getLogger().severe("Disabling due to database error");
            return;
        }
        isOpen = true;
        Main.pluginInstance.getLogger().info("Database connection established.");
        if (!sql.tableExists("voting")) {
            Main.pluginInstance.getLogger().info("Creating voting table");
            String query = "CREATE TABLE `voting` (`uuid` varchar(36) NOT NULL, `normal` int, `ultra` int, `legendary` int, `player` varchar(16));";
            sql.modifyQuery(query, false);
        }
    }

    public static boolean isOpen() {
        return isOpen;
    }
}