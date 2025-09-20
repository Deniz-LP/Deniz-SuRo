// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import java.util.Iterator;
import de.deniz.util.Starting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class StopTimer implements CommandExecutor
{
    FileConfiguration Config;
    
    public StopTimer() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p)) {
                if (args.length == 0) {
                    if (!Main.stopped) {
                        if (Main.gamestart) {
                            Main.stopped = true;
                            for (final Player player : Bukkit.getOnlinePlayers()) {
                                player.sendMessage(String.valueOf(Main.pr) + "§cSpiel pausiert.");
                            }
                            final int time = Main.TimeSpeichern = Starting.getTime();
                            Starting.cancelTask();
                        }
                        else {
                            p.sendMessage(String.valueOf(Main.pr) + "§cSpiel ist noch nicht gestartet.");
                        }
                    }
                    else if (Main.stopped) {
                        Main.stopped = false;
                        for (final Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(String.valueOf(Main.pr) + "§aSpiel geht weiter.");
                        }
                        Starting.startTimer(Main.TimeSpeichern, p);
                    }
                }
                else {
                    p.sendMessage(String.valueOf(Main.pr) + "/pause");
                }
            }
            else {
                p.sendMessage(Main.np);
            }
        }
        else {
            System.out.println("Der Command ist nur als SPieler nutzbar!");
        }
        return false;
    }
}
