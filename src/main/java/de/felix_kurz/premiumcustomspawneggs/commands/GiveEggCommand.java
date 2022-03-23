package de.felix_kurz.premiumcustomspawneggs.commands;

import de.felix_kurz.premiumcustomspawneggs.main.Main;
import de.felix_kurz.premiumcustomspawneggs.recipes.CustomEgg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class GiveEggCommand implements CommandExecutor {

    private Main plugin = Main.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command, @NotNull String label, @NotNull String[] args) {
         if (!sender.hasPermission("giveegg.give")) {
             sender.sendMessage("§cYou don't have the permission");
             return false;
         }
         if (args.length < 2) {
             sender.sendMessage("§cPlease use §6/giveegg <§eplayer§6> <§eegg§6> <§eamount§>");
         }
         new BukkitRunnable() {
            @Override
            public void run() {
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage("§cPlayer not found");
                    return;
                }
                if (!p.isOnline()) {
                    sender.sendMessage("§cPlayer isn't online");
                    return;
                }
                if (args.length == 2) p.getInventory().addItem(CustomEgg.getEggItem(args[1], 1));
                else
                    try {
                        p.getInventory().addItem(CustomEgg.getEggItem(args[1], Integer.parseInt(args[2])));
                    } catch (Exception ignore) {
                        sender.sendMessage("§cPlease use §6/giveegg <§eplayer§6> <§eegg§6> <§eamount§>");
                    }
            }
        }.runTaskAsynchronously(plugin);
        return false;
    }
}
