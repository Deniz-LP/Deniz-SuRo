// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class TPSpec implements CommandExecutor
{
    FileConfiguration Config;
    
    public TPSpec() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Specs.contains(p) || Main.Publisher.equals(p.getName())) {
                if (args.length == 1) {
                    final Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        p.teleport(target.getLocation());
                        p.sendMessage(String.valueOf(Main.pr) + "§aDu wurdest zu §b" + target.getName() + "§a teleportiert.");
                    }
                    else {
                        p.sendMessage(Main.nf);
                    }
                }
                else {
                    p.sendMessage(String.valueOf(Main.pr) + "§cBitte benutze /spec <Spieler>.");
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
