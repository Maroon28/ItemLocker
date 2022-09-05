package me.maroon28.itemlocker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import static me.maroon28.itemlocker.Utils.getMessage;
import static me.maroon28.itemlocker.Utils.isLocked;

public class ItemListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        Player player = event.getPlayer();
        if (isLocked(item)) {
            player.sendMessage(getMessage("cannot-drop-item"));
            event.setCancelled(true);
        }
    }
}
