package de.deniz.command;

import org.bukkit.Bukkit;
import de.deniz.util.Starting;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class REAL implements CommandExecutor
{
    FileConfiguration Config;
    
    public REAL() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.contains(p.getName())) {
                if (args.length == 0) {
                    final int Timer = Starting.getTime();
                    if (Timer < 3600 && Timer > 0) {
                        Main.getPlugin().getConfig().set("Timer.Zeit", (Object)Timer);
                        Main.getPlugin().saveConfig();
                    }
                    else {
                        Main.getPlugin().getConfig().set("Timer.Zeit", (Object)0);
                        Main.getPlugin().saveConfig();
                    }
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "rl");
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