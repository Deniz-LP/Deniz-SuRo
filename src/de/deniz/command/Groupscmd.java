package de.deniz.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class Groupscmd implements CommandExecutor {
    private String errorMsg;
    private FileConfiguration config;

    public Groupscmd() {
        this.errorMsg = Main.pr + "§cBitte benutze /groups add/remove/info <Player> [Gamemaster/Spieler/Spec] !";
        this.config = Main.getPlugin().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            String targetName = args[1];
            String action = args[0].toLowerCase();
            String group = args[2].toLowerCase();

            if ("add".equals(action)) {
                if (Main.Gamemaster.contains(targetName) || Main.AlleSpieler.contains(targetName)) {
                    System.out.println(Main.pr + "§cDer Spieler ist schon in einer Gruppe");
                    return false;
                }

                OfflinePlayer target = (Player) Bukkit.getOfflinePlayer(targetName);

                switch (group) {
                    case "gamemaster":
                        if (!Main.Gamemaster.contains(targetName)) {
                            Main.Gamemaster.add((Player) target);
                            System.out.println(Main.pr + "§aDer Spieler §5§l" + target.getName() + " §aist nun ein §4§lGamemaster§f.");
                            if (target.isOnline()) {
                                Player p = target.getPlayer();
                                p.sendMessage(Main.pr + "§aDu bist nun ein §4§lGamemaster§f.");
                                p.kickPlayer(Main.pr + "§a§lNeuer Rang! §4§lGamemaster");
                            }
                            config.set("Range.Rang Spieler" + target.getName(), "gamemaster");
                            Main.getPlugin().saveConfig();
                        } else {
                            System.out.println(Main.pr + "§cDer Spieler §5§l" + target.getName() + " §cist schon ein §4§lGamemaster§f!");
                        }
                        break;

                    case "spec":
                        if (!Main.Specs.contains(targetName)) {
                            Main.Specs.add((Player) target);
                            System.out.println(Main.pr + "§aDer Spieler §7" + target.getName() + " §aist nun Spectator.");
                            config.set("Range.Rang Spieler" + target.getName(), "spec");
                            Main.getPlugin().saveConfig();
                            if (target.isOnline()) {
                                target.getPlayer().kickPlayer(Main.pr + "Neuer Rang! Spectator.");
                            }
                        }
                        break;

                    case "spieler":
                        if (!Main.AlleSpieler.contains(targetName)) {
                            Main.AlleSpieler.add((Player) target);
                            System.out.println(Main.pr + "§aDer Spieler §b§l" + target.getName() + " §aist nun im Spiel.");
                            config.set("Range.Rang Spieler" + target.getName(), "Default");
                            int anzahlSpieler = config.getInt("Anzahl.Spieler") + 1;
                            Main.ANzahlSpieler = anzahlSpieler;
                            config.set("Anzahl.Spieler", anzahlSpieler);
                            Main.getPlugin().saveConfig();
                            if (target.isOnline()) {
                                target.getPlayer().kickPlayer(Main.pr + "Neuer Rang! Mitspieler.");
                            }
                        }
                        break;

                    default:
                        System.out.println(errorMsg);
                        break;
                }
            }
        } else if (args.length == 2) {
            String action = args[0].toLowerCase();
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if ("remove".equals(action)) {
                if (!Main.Gamemaster.contains(target) && !Main.AlleSpieler.contains(target)) {
                    sender.sendMessage(Main.pr + "§cDer Spieler ist noch in keiner Gruppe");
                    return false;
                }

                if (Main.Gamemaster.contains(target)) {
                    Main.Gamemaster.remove(target);
                    sender.sendMessage(Main.pr + "§aDer Spieler §9" + target.getName() + " §aist kein §4§lGamemaster§f mehr.");
                    if (target.isOnline()) {
                        Player p = target.getPlayer();
                        p.sendMessage(Main.pr + "§cDu bist kein §4§lGamemaster§f mehr.");
                        p.kickPlayer(Main.pr + "§cDein Rang als §4§lGamemaster§c wurde entfernt.");
                    }
                    config.set("Range.Rang Spieler" + target.getName(), null);
                    Main.getPlugin().saveConfig();
                } else if (Main.AlleSpieler.contains(target)) {
                    Main.AlleSpieler.remove(target);
                    sender.sendMessage(Main.pr + "§aDer Spieler §9" + target.getName() + " §aist kein Mitspieler mehr.");
                    if (target.isOnline()) {
                        Player p = target.getPlayer();
                        p.sendMessage(Main.pr + "§cDu bist kein Mitspieler mehr.");
                        p.kickPlayer(Main.pr + "§cDein Rang als §aMitspieler§c wurde entfernt.");
                    }
                    int anzahl = config.getInt("Anzahl.Spieler") - 1;
                    Main.ANzahlSpieler = anzahl;
                    config.set("Anzahl.Spieler", anzahl);
                    config.set("Range.Rang Spieler" + target.getName(), null);
                    Main.getPlugin().saveConfig();
                } else if ("ausgeschieden".equalsIgnoreCase(config.getString("Range.Rang Spieler" + target.getName()))) {
                    sender.sendMessage(Main.pr + "§aDer Spieler §9" + target.getName() + " §aist nicht mehr ausgeschieden.");
                    if (target.isOnline()) {
                        Player p = target.getPlayer();
                        p.sendMessage(Main.pr + "§cDu bist kein Mitspieler mehr.");
                        p.kickPlayer(Main.pr + "§cDein Rang als §aMitspieler§c wurde entfernt.");
                    }
                    config.set("Range.Rang Spieler" + target.getName(), null);
                    Main.getPlugin().saveConfig();
                }
            } else if ("info".equals(action)) {
                if (Main.Gamemaster.contains(target)) {
                    sender.sendMessage(Main.pr + "§aDer Spieler §6" + target.getName() + "§a ist in der Gruppe §4§lGamemaster.");
                } else if (Main.AlleSpieler.contains(target)) {
                    sender.sendMessage(Main.pr + "§aDer Spieler §6" + target.getName() + "§a ist als Spieler angemeldet.");
                } else {
                    sender.sendMessage(Main.pr + "§aDer Spieler §6" + target.getName() + "§a ist in keiner Gruppe!");
                }
            }
        }
        return false;
    }
}
