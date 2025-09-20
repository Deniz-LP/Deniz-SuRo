// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class AcidRain implements CommandExecutor
{
    FileConfiguration Config;
    static int taskid;
    static int time;
    
    static {
        AcidRain.taskid = 0;
    }
    
    public AcidRain() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.contains(p.getName())) {
                AcidRain.time = 151;
                if (args.length == 0) {
                    AcidRain.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getPlugin(), (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (AcidRain.time == 150) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cHalte dich in 30 Sekunden nicht mehr auf der Oberfl\u00e4che auf!");
                                }
                            }
                            else if (AcidRain.time == 135) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cIn 15 Sekunden werden Gewitterwolken aus Tschernobyl ankommen!");
                                }
                            }
                            else if (AcidRain.time == 125) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cIn 5 Sekunden werden giftige Regenf\u00e4lle niederschlagen!");
                                }
                            }
                            else if (AcidRain.time == 120) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§4§lDer Regen ist giftig!");
                                    Main.acid = true;
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "weather thunder");
                                }
                            }else if (AcidRain.time == 119) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "weather thunder");
                                }
                            }
                            else if (AcidRain.time == 60) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§1In 60 Sekuden ist der Spuk vorbei.");
                                }
                            }
                            else if (AcidRain.time == 10) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cNur noch 10 Sekunden!");
                                }
                            }
                            else if (AcidRain.time == 0) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§aDie Oberfl\u00e4che ist nun wieder sicher!");
                                    Main.acid = false;
                                    player.removePotionEffect(PotionEffectType.WITHER);
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "weather clear");
                                }
                            }
                            --AcidRain.time;
                        }
                    }, 0L, 20L);
                }
                else {
                    p.sendMessage(new StringBuilder(String.valueOf(Main.pr)).toString());
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
