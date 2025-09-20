// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class Vanish implements CommandExecutor
{
    FileConfiguration Config;
    
    public Vanish() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.equals(p.getName())) {
                if (args.length == 0) {
                    if (!Main.Vanished.contains(p)) {
                        Main.Vanished.add(p);
                        p.sendMessage(String.valueOf(Main.pr) + "§aDu ist nun unsichtbar.");
                        for (final Player player : Bukkit.getOnlinePlayers()) {
                            if (!Main.Gamemaster.contains(player)) {
                                player.hidePlayer(p);
                            }
                        }
                    }
                    else {
                        Main.Vanished.remove(p);
                        p.sendMessage(String.valueOf(Main.pr) + "§aDu bist nun wieder sichtbar.");
                        p.showPlayer(p);
                    }
                }
                else if (args.length == 1) {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!Main.Vanished.contains(p)) {
                            Main.Vanished.add(target);
                            p.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §b" + target.getName() + "§a ist nun unsichtbar.");
                            for (final Player player2 : Bukkit.getOnlinePlayers()) {
                                if (!Main.Gamemaster.contains(player2)) {
                                    player2.hidePlayer(target);
                                }
                            }
                        }
                        else {
                            Main.Vanished.remove(target);
                            p.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §b" + target.getName() + "§a ist nun wieder sichtbar.");
                            p.showPlayer(target);
                        }
                    }
                }
                else {
                    p.sendMessage(String.valueOf(Main.pr) + "§aNutze /vanish [Spieler]1");
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
