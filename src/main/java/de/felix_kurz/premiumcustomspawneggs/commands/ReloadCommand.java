package de.felix_kurz.premiumcustomspawneggs.commands;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("pcse.reload")) {
            sender.sendMessage(Main.PRE + "§cYou don't have the permission");
            return false;
        }
        Main.getPlugin().reloadConfig();
        return false;
    }

}
