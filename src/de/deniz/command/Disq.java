// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import java.util.Iterator;
import org.bukkit.OfflinePlayer;
//import de.deniz.util.TestForWinner;
import org.bukkit.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class Disq implements CommandExecutor
{
    FileConfiguration Config;
    FileConfiguration Range;
    static int taskid;
    static String message;
    
    static {
        Disq.taskid = 1;
        Disq.message = "";
    }
    
    public Disq() {
        this.Config = Main.getPlugin().getConfig();
        this.Range = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.contains(p.getName())) {
                for (int i = 1; i < args.length; ++i) {
                    Disq.message = String.valueOf(Disq.message) + args[i] + " ";
                }
                final OfflinePlayer tot = Bukkit.getOfflinePlayer(args[0]);
                if (tot != null) {
                    if (Main.AlleSpieler.contains(tot)) {
                        this.Range.set("Range.Rang Spieler" + tot.getName(), (Object)null);
                        Main.getPlugin().saveConfig();
                        this.Range.set("Range.Rang Spieler" + tot.getName(), (Object)"spec");
                        Main.getPlugin().saveConfig();
                        Main.AlleSpieler.remove(tot.getPlayer());
                        if (tot.isOnline()) {
                            tot.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§cDu bist aus dem Projekt ausgeschieden!  \n \n Grund: Disqualifiziert - " + Disq.message);
                        }
                        this.Range.set("Range.Rang Spieler" + tot.getName(), (Object)"spec");
                        Main.getPlugin().saveConfig();
                        final int AnzahlSPieler = Main.ANzahlSpieler = this.Range.getInt("Anzahl.SPieler") - 1;
                        this.Range.set("Anzahl.Spieler", (Object)AnzahlSPieler);
                        Main.getPlugin().saveConfig();
                        for (final Player p2 : Bukkit.getOnlinePlayers()) {
                            p2.sendMessage("§c\u271e §c" + tot.getName() + " §f» §aDisqualifikation - " + Disq.message);
                            p2.playSound(p2.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0f, 1.0f);
                        }
                        this.Range.set("Range.Rang.Grund Spieler" + tot.getName() + ".Grund", (Object)("Disqualifiziert - " + Disq.message));
                        Main.getPlugin().saveConfig();
                    
                    Disq.message = "";
                
                    /*if (TestForWinner.testForWinBoolean()) {
                        TestForWinner.WInningPLayer(Main.AlleSpieler.get(0));
                    }*/
                    }else {
                        Bukkit.broadcastMessage(String.valueOf(Main.pr) + "§f\u00dcbrig: §a" + Main.AlleSpieler.size() + "§f Spieler.");
                    }
                }
                else {
                    p.sendMessage(Main.nf);
                }
            }
            else {
                p.sendMessage(Main.np);
            }
        }
        else {
            System.out.println("Der Command ist nur als Spieler nutzbar!");
        }
        Disq.message = "";
        return false;
    }
}
