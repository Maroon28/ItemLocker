package me.maroon28.itemlocker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Utils {
    public static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static boolean lock(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        var container = meta.getPersistentDataContainer();
        var key = getLockedKey();
        if (isLocked(item)) return false;

        container.set(key, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return true;
    }

    public static boolean unlock(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        var container = meta.getPersistentDataContainer();
        var key = getLockedKey();
        if (container.has(key)) {
            if (!isLocked(item)) return false;
        }
        container.set(key, PersistentDataType.INTEGER, 0);
        item.setItemMeta(meta);
        return true;
    }

    public static boolean isLocked(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        var container = meta.getPersistentDataContainer();
        var key = getLockedKey();
        if (!container.has(key)) return false;
        return container.get(key, PersistentDataType.INTEGER) == 1;
    }

    private static NamespacedKey getLockedKey() {
        return new NamespacedKey("itemlocker", "locked");
    }

    @NotNull
    public static Component getMessage(String message) {
        FileConfiguration config = getPlugin().getConfig();
        String configuredMessage = config.getString("messages." + message);
        return miniMessage.deserialize(Objects.requireNonNullElseGet(configuredMessage, () -> "<red>Message <yellow>" + message + "<red> could not be found in the config file!"));
    }

    private static ItemLocker getPlugin() {
        return (ItemLocker) Bukkit.getServer().getPluginManager().getPlugin("ItemLocker");
    }
}
