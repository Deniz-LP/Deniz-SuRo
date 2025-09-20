// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class Setup implements CommandExecutor
{
    FileConfiguration Config;
    
    public Setup() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final int SpielerMax = this.Config.getInt("Anzahl.Spieler");
            if (Main.Gamemaster.contains(player) || Main.Publisher.equals(player.getName())) {
                if (args.length == 1) {
                    final int Nummer = Integer.parseInt(args[0]);
                    if (Nummer <= SpielerMax) {
                        if (this.Config.contains("Startposition." + Nummer)) {
                            player.sendMessage(String.valueOf(Main.pr) + "§aStartpunkt Nummer " + Nummer + " neu gesetzt!");
                            this.Config.set("Startposition." + Nummer + ".X", (Object)player.getLocation().getBlockX());
                            Main.getPlugin().saveConfig();
                            this.Config.set("Startposition." + Nummer + ".Y", (Object)player.getLocation().getBlockY());
                            Main.getPlugin().saveConfig();
                            this.Config.set("Startposition." + Nummer + ".Z", (Object)player.getLocation().getBlockZ());
                            Main.getPlugin().saveConfig();
                        }
                        else {
                            player.sendMessage(String.valueOf(Main.pr) + "§aStartpunkt Nummer " + Nummer + " wurde gesetzt!");
                            this.Config.set("Startposition." + Nummer + ".X", (Object)player.getLocation().getBlockX());
                            Main.getPlugin().saveConfig();
                            this.Config.set("Startposition." + Nummer + ".Y", (Object)player.getLocation().getBlockY());
                            Main.getPlugin().saveConfig();
                            this.Config.set("Startposition." + Nummer + ".Z", (Object)player.getLocation().getBlockZ());
                            Main.getPlugin().saveConfig();
                        }
                    }
                    else {
                        player.sendMessage(String.valueOf(Main.pr) + "§cBitte benutze /setup (1-" + SpielerMax + ")");
                    }
                }
                else {
                    player.sendMessage(String.valueOf(Main.pr) + "§cBitte benutze /setup (1-" + SpielerMax + ")");
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
