package me.maroon28.itemlocker.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static me.maroon28.itemlocker.Utils.*;

public class LockCommand implements CommandExecutor {

    private Player player;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cannot execute from the console!");
            return true;
        }

        this.player = player;

        if (!player.hasPermission("itemlocker.use")) {
            player.sendMessage(getMessage("no-permission"));
            return true;
        }

        if (args.length == 1) {
            if (args[0].equals("lock")) {
                return tryLock();
            }
            if (args[0].equals("unlock")) {
                return tryUnlock();
            }
            player.sendMessage(getMessage("usage"));
            return true;
        }
        player.sendMessage(getMessage("usage"));
        return true;
    }

    private boolean tryUnlock() {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType().equals(Material.AIR)) {
            player.sendMessage(getMessage("cannot-unlock-air"));
            return true;
        }
        if (unlock(itemInHand)) {
            player.sendMessage(getMessage("item-unlocked"));
        } else {
            player.sendMessage(getMessage("item-already-unlocked"));
        }
        return true;
    }

    private boolean tryLock() {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType().equals(Material.AIR)) {
            player.sendMessage(getMessage("cannot-lock-air"));
            return true;
        }
        if (lock(itemInHand)) {
            player.sendMessage(getMessage("item-locked"));
        } else {
            player.sendMessage(getMessage("item-already-locked"));
        }
        return true;
    }
}
