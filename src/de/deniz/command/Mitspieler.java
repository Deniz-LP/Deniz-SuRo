package de.deniz.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class Mitspieler implements CommandExecutor {
    private final FileConfiguration config;

    public Mitspieler() {
        this.config = Main.getPlugin().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Der Command ist nur als Spieler nutzbar!");
            return false;
        }

        Player p = (Player) sender;

        if ((Main.Gamemaster.contains(p) || Main.Publisher.equals(p.getName()))
                && args.length == 1 && args[0].equalsIgnoreCase("all")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Main.pr + "§6§lListe der Mitspieler:");
                for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
                    if (Main.AlleSpieler.contains(off)) {
                        player.sendMessage("§fSpieler: §a" + off.getName());
                    }
                }
            }
            return false;
        }

        p.sendMessage(Main.pr + "§6§lListe der Mitspieler:");
        for (OfflinePlayer off : Bukkit.getOfflinePlayers()) {
            String key = "Range.Rang Spieler" + off.getName();
            if (config.contains(key)) {
                String rang = config.getString(key);
                if ("Default".equals(rang)) {
                    p.sendMessage("§fSpieler: §a" + off.getName());
                }
            }
        }
        return false;
    }
}
