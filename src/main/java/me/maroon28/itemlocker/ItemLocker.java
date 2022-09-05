package me.maroon28.itemlocker;

import me.maroon28.itemlocker.commands.LockCommand;
import me.maroon28.itemlocker.listeners.ItemListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemLocker extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("locker").setExecutor(new LockCommand());
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
