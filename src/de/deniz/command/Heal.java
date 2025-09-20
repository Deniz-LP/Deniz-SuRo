package de.deniz.command;

import org.bukkit.Bukkit;
import de.deniz.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class Heal implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (Main.Gamemaster.contains(player.getName())) {
                if (args.length != 0) {
                    if (args.length == 1) {
                        Bukkit.getPlayer(args[0]);
                    }
                    else {
                        System.out.println("ok");
                    }
                }
            }
            else {
                player.sendMessage(Main.np);
            }
        }
        else {
            System.out.println("Der Command ist nur als Spieler nutzbar!");
        }
        return false;
    }
}