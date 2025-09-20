package de.deniz.command;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class GetLoc implements CommandExecutor
{
    FileConfiguration Config;
    
    public GetLoc() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p)) {
                if (args.length == 0) {
                    p.sendMessage("X:" + p.getLocation().getX());
                    p.sendMessage("Y:" + p.getLocation().getY());
                    p.sendMessage("Z:" + p.getLocation().getZ());
                    p.sendMessage("Yaw:" + p.getLocation().getYaw());
                    p.sendMessage("Pitch:" + p.getLocation().getPitch());
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