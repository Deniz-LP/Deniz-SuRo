package de.deniz.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import de.deniz.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class Alert implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Der Command ist nur als Spieler nutzbar!");
            return true;
        }

        Player p = (Player) sender;

        if (!Main.Gamemaster.contains(p) && !Main.Publisher.equals(p.getName())) {
            p.sendMessage(Main.np);
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(Main.pr + "§7/alert §Nachricht§r• §aSende eine Nachricht an alle Spieler");
            return true;
        }

        StringBuilder msgBuilder = new StringBuilder("&7");
        for (String arg : args) {
            msgBuilder.append(arg).append(" ");
        }
        String toSend = ChatColor.translateAlternateColorCodes('&', msgBuilder.toString().trim());

        Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(""));
        Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(Main.pr + toSend));
        Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(""));

        return true;
    }
}
