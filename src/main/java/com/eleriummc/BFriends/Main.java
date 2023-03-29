package com.eleriummc.BFriends;

import com.eleriummc.BFriends.storage.DBConnection;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Plugin {
    public static Plugin pluginInstance;
    public static Configuration configuration;


    @Override
    public void onEnable() {
        pluginInstance = this;
        try {
            configuration = loadConfig();
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Open database connection and create the table if it doesn't exist.
        DBConnection.init();
        if(!DBConnection.isOpen()) {
            // If the database failed to open, don't run anything else.
            return;
        }


    }

    @Override
    public void onDisable() {

    }

    public Configuration loadConfig() throws IOException {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File con = new File(getDataFolder(), "config.yml");

        if(!con.exists()) {
            con.createNewFile();
            FileWriter fw = new FileWriter(con);
            fw.write("# Database location that stores friends and all data like that.");
            fw.write("mysql-host: localhost");
            fw.write("mysql-port: 3306");
            fw.write("mysql-database: database");
            fw.write("mysql-username: root");
            fw.write("mysql-password: password");
        }

        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(con);
    }

}
