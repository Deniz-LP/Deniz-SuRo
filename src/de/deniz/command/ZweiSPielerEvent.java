// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class ZweiSPielerEvent implements CommandExecutor
{
    FileConfiguration Config;
    static int taskid;
    static int time;
    public static ArrayList<Player> ArraylistPlayer;
    
    static {
        ZweiSPielerEvent.taskid = 0;
        ZweiSPielerEvent.ArraylistPlayer = new ArrayList<Player>();
    }
    
    public ZweiSPielerEvent() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.contains(p.getName())) {
                ZweiSPielerEvent.time = 60;
                if (args.length == 0) {
                    for (final Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(String.valueOf(Main.pr) + "§cIn 60 Sekunden m\u00fcssen 2 zuf\u00e4llig ausgew\u00e4hlte Spieler gegeneinander k\u00e4mpfen!");
                    }
                    ZweiSPielerEvent.taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getPlugin(), (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (ZweiSPielerEvent.time == 30) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    if (Main.OnlinePlayer.contains(player)) {
                                        ZweiSPielerEvent.ArraylistPlayer.add(player);
                                    }
                                }
                                Collections.shuffle(ZweiSPielerEvent.ArraylistPlayer);
                                final Player Sp1 = Main.Spieler1Vom2SpielerEvent = ZweiSPielerEvent.ArraylistPlayer.get(0);
                                final Player Sp2 = Main.Spieler2Vom2SpielerEvent = ZweiSPielerEvent.ArraylistPlayer.get(1);
                                for (final Player player2 : Bukkit.getOnlinePlayers()) {
                                    player2.sendMessage(String.valueOf(Main.pr) + "§aBitte macht euch bereit, §8" + Sp1.getName() + "§a und §8" + Sp2.getName() + "§a. In 30 Sekunden k\u00e4mpft ihr bis zum Tode!");
                                }
                            }
                            else if (ZweiSPielerEvent.time == 60) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cIn 60 Sekunden k\u00e4mpfen 2 Spieler gegeneinander!");
                                }
                            }
                            else if (ZweiSPielerEvent.time == 10) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cIn 10 Sekunden geht der Kampf los!");
                                }
                            }
                            else if (ZweiSPielerEvent.time == 0) {
                                for (final Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(String.valueOf(Main.pr) + "§cDer Kampf beginnt! Viel Gl\u00fcck an beide Spieler.");
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler1", (Object)Main.Spieler1Vom2SpielerEvent.getName());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler1LocX", (Object)Main.Spieler1Vom2SpielerEvent.getLocation().getBlockX());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler1LocY", (Object)Main.Spieler1Vom2SpielerEvent.getLocation().getBlockY());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler1LocZ", (Object)Main.Spieler1Vom2SpielerEvent.getLocation().getBlockZ());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler2LocX", (Object)Main.Spieler2Vom2SpielerEvent.getLocation().getBlockX());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler2LocY", (Object)Main.Spieler2Vom2SpielerEvent.getLocation().getBlockY());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler2LocZ", (Object)Main.Spieler2Vom2SpielerEvent.getLocation().getBlockZ());
                                    ZweiSPielerEvent.this.Config.set("Event2Spieler.Spieler2", (Object)Main.Spieler2Vom2SpielerEvent.getName());
                                    Main.ZweiSpielerEventGestartet = true;
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "tp " + Main.Spieler1Vom2SpielerEvent.getName() + " 20 200 20");
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "tp " + Main.Spieler2Vom2SpielerEvent.getName() + " 20 200 60");
                                    Bukkit.getScheduler().cancelTask(ZweiSPielerEvent.taskid);
                                }
                            }
                            --ZweiSPielerEvent.time;
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
