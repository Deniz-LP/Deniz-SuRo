// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import de.deniz.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class Live implements CommandExecutor
{
    String Livee;
    
    public Live() {
        this.Livee = "§f§l[§3§lLIVE§f§l] ";
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (args.length == 0) {
                if (Main.Live.contains(player)) {
                    Main.Live.remove(player);
                    player.sendMessage(String.valueOf(Main.pr) + "§cDu wirst nicht mehr als Live angezeigt.");
                    if (Main.Gamemaster.contains(player)) {
                        player.setPlayerListName("§4§lGMaster | " + player.getName());
                    }
                    else if (Main.AlleSpieler.contains(player)) {
                        player.setPlayerListName("§f" + player.getName());
                    }
                }
                else {
                    Main.Live.add(player);
                    player.sendMessage(String.valueOf(Main.pr) + "§3Du wirst nun als Live angezeigt.");
                    if (Main.Gamemaster.contains(player)) {
                        player.setPlayerListName(String.valueOf(this.Livee) + "§4§lGMaster | " + player.getName());
                    }
                    else if (Main.AlleSpieler.contains(player)) {
                        player.setPlayerListName(String.valueOf(this.Livee) + "§f" + player.getName());
                    }
                }
            }
            else {
                player.sendMessage(String.valueOf(Main.pr) + "§cBitte benutze nur /live");
            }
        }
        else {
            System.out.println("Der Command ist nur als SPieler nutzbar!");
        }
        return false;
    }
}
