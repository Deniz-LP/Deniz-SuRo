// 
// Decompiled by Procyon v0.5.36
// 

package de.deniz.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandExecutor;

public class Groups implements CommandExecutor
{
    private String Error;
    FileConfiguration Range;
    
    public Groups() {
        this.Error = String.valueOf(Main.pr) + "§cBitte benutze /groups add/remove/info <Player> [Gamemaster/Spieler] !";
        this.Range = Main.getPlugin().getConfig();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (args.length == 3) {
                final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (args[0].equalsIgnoreCase("add")) {
                    if (Main.Gamemaster.contains(player) || Main.Publisher.equals(player.getName())) {
                        if (Main.Gamemaster.contains(target.getPlayer()) || Main.AlleSpieler.contains(target.getPlayer())) {
                            player.sendMessage(String.valueOf(Main.pr) + "§cDer Spieler ist schon in einer Gruppe");
                            return false;
                        }
                        if (args[2].equalsIgnoreCase("gamemaster")) {
                            if (!Main.Gamemaster.contains(target.getPlayer())) {
                                Main.Gamemaster.add(target.getPlayer());
                                player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §5§l" + target.getName() + " §aist nun ein §4§lGamemaster§f.");
                                if (target.isOnline()) {
                                    target.getPlayer().sendMessage(String.valueOf(Main.pr) + "§aDu bist nun ein §4§lGamemaster§f.");
                                }
                                this.Range.set("Range.Rang Spieler" + target.getName(), (Object)"gamemaster");
                                Main.getPlugin().saveConfig();
                                target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§a§lNeuer Rang! §4§lGamemaster");
                            }
                            else {
                                player.sendMessage(String.valueOf(Main.pr) + "§cDer Spieler §5§l" + target.getName() + " §cist schon ein §4§lGamemaster§f!");
                            }
                        }
                        else if (args[2].equalsIgnoreCase("spec")) {
                            if (!Main.Specs.contains(target.getPlayer())) {
                                Main.Specs.add(target.getPlayer());
                                player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §7" + target.getName() + " §aist nun Spectator.");
                                this.Range.set("Range.Rang Spieler" + target.getName(), (Object)"spec");
                                Main.getPlugin().saveConfig();
                                if (target.isOnline()) {
                                    target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "Neuer Rang! Spectator.");
                                }
                            }
                        }
                        else if (args[2].equalsIgnoreCase("Spieler")) {
                            if (!Main.AlleSpieler.contains(target.getPlayer())) {
                                Main.AlleSpieler.add(target.getPlayer());
                                player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §b§l" + target.getName() + " §aist nun im Spiel.");
                                this.Range.set("Range.Rang Spieler" + target.getPlayer().getName(), (Object)"Default");
                                Main.getPlugin().saveConfig();
                                final int AnzahlSPieler = Main.ANzahlSpieler = this.Range.getInt("Anzahl.Spieler") + 1;
                                this.Range.set("Anzahl.Spieler", (Object)AnzahlSPieler);
                                Main.getPlugin().saveConfig();
                                if (target.isOnline()) {
                                    target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "Neuer Rang! Mitspieler.");
                                }
                            }
                        }
                        else {
                            player.sendMessage(this.Error);
                        }
                    }
                    else {
                        player.sendMessage(Main.np);
                    }
                }
            }
            else if (args.length == 2) {
                final OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (args[0].equalsIgnoreCase("remove")) {
                    if (Main.Gamemaster.contains(player) || Main.Publisher.equals(player.getName())) {
                        if (!Main.Gamemaster.contains(target.getPlayer()) && !Main.AlleSpieler.contains(target.getPlayer())) {
                            player.sendMessage(String.valueOf(Main.pr) + "§cDer Spieler ist noch in keiner Gruppe");
                            return false;
                        }
                        if (Main.Gamemaster.contains(target.getPlayer())) {
                            Main.Gamemaster.remove(target.getPlayer());
                            player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §9" + target.getName() + " §aist kein §4§lGamemaster§f mehr.");
                            if (target.isOnline()) {
                                target.getPlayer().sendMessage(String.valueOf(Main.pr) + "§cDu bist kein §4§lGamemaster§f mehr.");
                                target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§cDein Rang als §4§lGamemaster§c wurde entfernt.");
                            }
                            this.Range.set("Range.Rang Spieler" + target.getName(), (Object)null);
                            Main.getPlugin().saveConfig();
                        }
                        else if (Main.Specs.contains(target.getPlayer())) {
                            Main.Specs.remove(target.getPlayer());
                            sender.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §9" + target.getName() + " §aist kein Spectator mehr.");
                            if (target.isOnline()) {
                                target.getPlayer().sendMessage(String.valueOf(Main.pr) + "§cDu bist kein Spectator mehr.");
                                target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§cDein Rang als §7Spectator§c wurde entfernt.");
                            }
                            final int AnzahlSPieler = Main.ANzahlSpieler = this.Range.getInt("Anzahl.Spieler") - 1;
                            this.Range.set("Anzahl.Spieler", (Object)AnzahlSPieler);
                            Main.getPlugin().saveConfig();
                            this.Range.set("Range.Rang Spieler" + target.getName(), (Object)null);
                            Main.getPlugin().saveConfig();
                        }
                        else if (Main.AlleSpieler.contains(target.getPlayer())) {
                            Main.AlleSpieler.remove(target.getPlayer());
                            sender.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §9" + target.getName() + " §aist kein Mitspieler mehr.");
                            if (target.isOnline()) {
                                target.getPlayer().sendMessage(String.valueOf(Main.pr) + "§cDu bist kein Mitspieler mehr.");
                                target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§cDein Rang als §aMitspieler§c wurde entfernt.");
                            }
                            final int AnzahlSPieler = Main.ANzahlSpieler = this.Range.getInt("Anzahl.Spieler") - 1;
                            this.Range.set("Anzahl.Spieler", (Object)AnzahlSPieler);
                            Main.getPlugin().saveConfig();
                            this.Range.set("Range.Rang Spieler" + target.getName(), (Object)null);
                            Main.getPlugin().saveConfig();
                        }
                        else if (this.Range.getString("Range.Rang Spieler" + target.getName()).equalsIgnoreCase("ausgeschieden")) {
                            sender.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §9" + target.getName() + " §aist nicht mehr ausgeschieden.");
                            if (target.isOnline()) {
                                target.getPlayer().sendMessage(String.valueOf(Main.pr) + "§cDu bist kein Mitspieler mehr.");
                                target.getPlayer().kickPlayer(String.valueOf(Main.pr) + "§cDein Rang als §aMitspieler§c wurde entfernt.");
                            }
                            this.Range.set("Range.Rang Spieler" + target.getName(), (Object)null);
                            Main.getPlugin().saveConfig();
                        }
                    }
                    else {
                        player.sendMessage(Main.np);
                    }
                }
                else if (args[0].equalsIgnoreCase("info")) {
                    if (Main.Gamemaster.contains(target.getPlayer())) {
                        player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §6" + target.getName() + "§a ist in der Gruppe §4§lGamemaster.");
                    }
                    else if (Main.AlleSpieler.contains(target.getPlayer())) {
                        player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §6" + target.getName() + "§a ist als Spieler angemeldet.");
                    }
                    else {
                        player.sendMessage(String.valueOf(Main.pr) + "§aDer Spieler §6" + target.getName() + "§a ist in keiner Gruppe!");
                    }
                }
                else {
                    sender.sendMessage(this.Error);
                }
            }
            else {
                player.sendMessage(Main.nf);
            }
        }
        else {
            sender.sendMessage(this.Error);
        }
        return false;
    }
}
