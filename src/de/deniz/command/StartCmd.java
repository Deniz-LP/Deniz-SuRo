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

public class StartCmd implements CommandExecutor
{
    static int time;
    static int taskid;
    static int time1;
    FileConfiguration Config;
    
    static {
        StartCmd.time = Main.countdownTime;
        StartCmd.taskid = 0;
    }
    
    public StartCmd() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                System.out.println("Test");
                Starting.startTimer(5400, Bukkit.getPlayer("Deniz_LP"));
                System.out.println("Test");
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("timer")) {
                    if (Main.getPlugin().getConfig().contains("Timer.Zeit")) {
                        final int timer = Main.getPlugin().getConfig().getInt("Timer.Zeit");
                        if (timer != 0 && timer > 0 && timer < 3600) {
                            Starting.startTimer(timer, Bukkit.getPlayer("Deniz_LP"));
                            for (final Player current : Bukkit.getOnlinePlayers()) {
                                if (timer >= 120) {
                                    final int starteInMin = timer / 60;
                                    final String starten = new StringBuilder(String.valueOf(starteInMin)).toString();
                                    final int starteInMinn = Integer.parseInt(starten);
                                    current.sendMessage(String.valueOf(Main.pr) + "§cZeit \u00fcbrig: ca. §a" + starteInMinn + "§c Minuten.");
                                }
                            }
                        }
                    }
                    return false;
                }
                final int starttimer = Integer.parseInt(args[0]);
                final int numma = starttimer + 3600;
                Starting.startTimer(numma, Bukkit.getPlayer("Deniz_LP"));
            }
            else {
                System.out.println(String.valueOf(Main.pr) + "§cBitte nutze NUR §a§l/start <Nummer>§c.");
            }
        }
        else {
            System.out.println("Der Command ist nur in der Konsole nutzbar!");
        }
        return false;
    }
}
