package de.deniz.command;

import java.util.Iterator;
import de.deniz.util.Starting;
import org.bukkit.GameMode;
import org.bukkit.Location;
import java.util.List;
import java.util.Collections;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class TpToPlace implements CommandExecutor
{
    FileConfiguration Config;
    
    public TpToPlace() {
        this.Config = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (Main.Gamemaster.contains(p) || Main.Publisher.equals(p.getName())) {
                final ArrayList<Player> pla = new ArrayList<Player>();
                for (final Player pl : Bukkit.getOnlinePlayers()) {
                    if (Main.AlleSpieler.contains(pl)) {
                        pla.add(pl);
                    }
                }
                Collections.shuffle(pla);
                if (args.length == 0) {
                    for (int j = 1; j <= Main.ANzahlSpieler; ++j) {
                        final int X = this.Config.getInt("Startposition." + j + ".X");
                        final int Y = this.Config.getInt("Startposition." + j + ".Y");
                        final int Z = this.Config.getInt("Startposition." + j + ".Z");
                        final Location tport = new Location(p.getWorld(), X + 0.5, (double)Y, Z + 0.5);
                        final Player play = pla.get(j - 1);
                        play.getInventory().clear();
                        play.setGameMode(GameMode.ADVENTURE);
                        play.teleport(tport);
                        Starting.startTimer(3610, Bukkit.getPlayer("Deniz_LP"));
                    }
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
            final ArrayList<Player> pla2 = new ArrayList<Player>();
            for (final Player pl2 : Bukkit.getOnlinePlayers()) {
                if (Main.AlleSpieler.contains(pl2)) {
                    pla2.add(pl2);
                }
            }
            Collections.shuffle(pla2);
            if (args.length == 0) {
                for (int i = 1; i <= Main.ANzahlSpieler; ++i) {
                    final int X2 = this.Config.getInt("Startposition." + i + ".X");
                    final int Y2 = this.Config.getInt("Startposition." + i + ".Y");
                    final int Z2 = this.Config.getInt("Startposition." + i + ".Z");
                    final Location tport2 = new Location(Bukkit.getWorld("World"), X2 + 0.5, (double)Y2, Z2 + 0.5);
                    final Player play2 = pla2.get(i - 1);
                    play2.getInventory().clear();
                    play2.setGameMode(GameMode.ADVENTURE);
                    play2.teleport(tport2);
                    Starting.startTimer(3610, Bukkit.getPlayer("Deniz_LP"));
                }
            }
        }
        return false;
    }
}