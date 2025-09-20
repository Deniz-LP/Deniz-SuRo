// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import java.util.Iterator;
import de.deniz.util.Starting;
import org.bukkit.Bukkit;
import de.deniz.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class cancel implements CommandExecutor
{
    static int time;
    static int taskid;
    
    static {
        cancel.time = 0;
        cancel.taskid = 0;
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (Main.Gamemaster.contains(player) || Main.Publisher.equals(player.getName())) {
                if (args.length == 1) {
                    final int timea = cancel.time = Integer.parseInt(args[0]);
                    if (Main.gamestart) {
                        for (final Player player2 : Bukkit.getOnlinePlayers()) {
                            player2.sendMessage(String.valueOf(Main.pr) + "§cSpielzeit wurde ge\u00e4ndert.");
                        }
                        Starting.startTimer(cancel.time, player);
                    }
                    else {
                        for (final Player player2 : Bukkit.getOnlinePlayers()) {
                            player2.sendMessage(String.valueOf(Main.pr) + "§cSpielstart wurde beendet.");
                        }
                        player.sendMessage(String.valueOf(Main.pr) + "§cDer Spielstart wurde abgebrochen.");
                        Starting.cancelTask();
                    }
                }
                else {
                    player.sendMessage("Zahl vergessen");
                }
            }
            else {
                player.sendMessage(Main.np);
            }
        }
        else {
            System.out.println("Der Command ist nur als SPieler nutzbar!");
        }
        return false;
    }
}
