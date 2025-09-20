package de.deniz.listener;

import org.bukkit.inventory.Inventory;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.Location;
import org.bukkit.GameMode;
import de.deniz.util.Starting;
import org.bukkit.event.player.PlayerJoinEvent;
import de.deniz.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class QuitAndJoinListener implements Listener {
    public static int taskid = 1;
    String Livee = "§f§l[§3§lLIVE§f§l] ";
    FileConfiguration Range = Main.getPlugin().getConfig();

    @EventHandler
    public void whenPlayerJoin(final PlayerJoinEvent event) {
        final Player current = event.getPlayer();
        if (Range.contains("Range.Rang Spieler" + current.getName())) {
            final String Rang = Range.getString("Range.Rang Spieler" + current.getName());
            if (Rang.equals("gamemaster")) {
                current.setPlayerListName("§4" + current.getName());
                event.setJoinMessage("§f[§2+§f] §4" + current.getName());
                Main.Gamemaster.add(current);
                if (!Main.beforegamestart) {
                    current.sendMessage(Main.pr + "§cDer Server wäre grade nicht für alle zugänglich.");
                }
                if (!Main.gamestart) {
                    int starte = Starting.getTime() - 3600;
                    if (starte >= 120) {
                        int starteInMinn = starte / 60;
                        current.sendMessage(Main.pr + "§aNoch §2" + starteInMinn + " §aMinuten bis zum Start!");
                    } else {
                        current.sendMessage(Main.pr + "§aNoch §2" + starte + " §aSekunden bis zum Start!");
                    }
                }
            } else if (Rang.equals("spec")) {
                current.setPlayerListName("§7" + current.getName());
                if (Range.contains("Range.Rang.Grund Spieler" + current.getName() + ".Grund")) {
                    String DISQ = Range.getString("Range.Rang.Grund Spieler" + current.getName() + ".Grund");
                    current.sendMessage(Main.pr + "§cAusgeschieden: §f" + DISQ);
                }
                event.setJoinMessage(" ");
                Main.Specs.add(current);
                if (!Main.beforegamestart) {
                    current.kickPlayer(Main.pr + "§cBitte trete dem Server in den Öffnungszeiten bei.");
                    return;
                }
                if (!Main.gamestart) {
                    int starte = Starting.getTime() - 3600;
                    if (starte >= 120) {
                        int starteInMinn2 = starte / 60;
                        current.sendMessage(Main.pr + "§aNoch §2" + starteInMinn2 + " §aMinuten bis zum Start!");
                    } else {
                        current.sendMessage(Main.pr + "§aNoch §2" + starte + " §aSekunden bis zum Start!");
                    }
                }
                current.sendMessage(" ");
                current.sendMessage(Main.pr + "§cDu kannst nur den Command §a/spec <Spieler> §ceingeben! ");
                current.setGameMode(GameMode.SPECTATOR);
            } else if (Rang.equals("Default")) {
                if (!Main.beforegamestart) {
                    current.kickPlayer(Main.pr + "§cBitte trete dem Server in den Öffnungszeiten bei.");
                    return;
                }
                Main.OnlinePlayer.add(current);
                if (!Main.gamestart) {
                    int starte = Starting.getTime() - 3600;
                    if (starte >= 120) {
                        int starteInMinn = starte / 60;
                        current.sendMessage(Main.pr + "§aNoch ca. §2" + starteInMinn + " §aMinuten bis zum Start!");
                    } else {
                        current.sendMessage(Main.pr + "§aNoch §2" + starte + " §aSekunden bis zum Start!");
                    }
                }
                current.setPlayerListName("§8" + current.getName());
                event.setJoinMessage("§f[§2+§f] §b" + current.getName());
                Main.AlleSpieler.add(current);

                int LocX = Range.getInt("Spieler." + current.getName() + ".Location.X");
                int LocY = Range.getInt("Spieler." + current.getName() + ".Location.Y");
                int LocY2 = LocY + 1;
                int LocZ = Range.getInt("Spieler." + current.getName() + ".Location.Z");
                int LocZ2 = LocZ - 1;
                current.getInventory().clear();

                Location loc = new Location(current.getWorld(), LocX, LocY, LocZ);
                Block block = loc.getBlock();
                Chest chest = (Chest) block.getState();
                for (int i = 0; i <= 17; i++) {
                    current.getInventory().setItem(18 + i, chest.getInventory().getItem(i));
                }
                for (int i = 0; i <= 8; i++) {
                    current.getInventory().setItem(i, chest.getInventory().getItem(18 + i));
                }

                Location loc2 = new Location(current.getWorld(), LocX, LocY2, LocZ);
                Block block2 = loc2.getBlock();
                Chest chest2 = (Chest) block2.getState();
                for (int i = 0; i <= 8; i++) {
                    current.getInventory().setItem(9 + i, chest2.getInventory().getItem(18 + i));
                }
                for (int i = 0; i <= 3; i++) {
                    current.getInventory().setItem(36 + i, chest2.getInventory().getItem(i));
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + LocX + " " + LocY + " " + LocZ + " air");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + LocX + " " + LocY2 + " " + LocZ + " air");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + LocX + " " + LocY + " " + LocZ2 + " air");
            } else {
                if (!Rang.equals("ausgeschieden")) {
                    current.kickPlayer(Main.pr + "§fDu bist NICHT beim Projekt angemeldet.");
                    return;
                }
                String Grund = Range.getString("Range.Rang.Grund Spieler" + current.getName() + ".Grund");
                current.kickPlayer(Main.pr + "§cDu bist aus dem Projekt ausgeschieden! Grund: " + Grund);
            }

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Team gm = scoreboard.registerNewTeam("00gmaster");
            Team alle = scoreboard.registerNewTeam("01spieler");
            Team spec = scoreboard.registerNewTeam("02specs");
            Player player = event.getPlayer();

            if (Main.Gamemaster.contains(player)) {
                gm.addEntry(player.getName());
                if (Main.Live.contains(player)) {
                    player.setPlayerListName(Livee + "§4" + player.getName());
                } else {
                    player.setPlayerListName("§4" + player.getName());
                }
            } else if (Main.AlleSpieler.contains(player)) {
                alle.addEntry(player.getName());
                event.setJoinMessage("§f[§2+§f] §b" + player.getName());
                if (Range.contains("Kills.Spieler" + player.getName())) {
                    int Kills = Range.getInt("Kills.Spieler" + player.getName());
                    player.setPlayerListName("§8" + player.getName() + " §c\u2694\ufe0f " + Kills);
                } else {
                    player.setPlayerListName("§8" + player.getName());
                }
            } else if (Main.Specs.contains(player)) {
                spec.addEntry(player.getName());
                event.setJoinMessage(" ");
                player.setPlayerListName("§7" + player.getName());
            }
            player.setScoreboard(scoreboard);
            return;
        }
        current.kickPlayer(Main.pr + "§fDu bist NICHT beim Projekt angemeldet.");
    }

    @EventHandler
    public void whenPlayerQuit(final PlayerQuitEvent event) {
        final Player p = event.getPlayer();
        if (Range.contains("Range.Rang Spieler" + p.getName())) {
            final String Rang = Range.getString("Range.Rang Spieler" + p.getName());
            int a = 1;
            if (Rang.equals("gamemaster")) {
                a = 3;
            } else if (Rang.equals("Event")) {
                a = 4;
            } else if (Rang.equals("Default")) {
                Main.OnlinePlayer.remove(p);
                a = 2;
            } else if (Rang.equals("spec")) {
                a = 1;
            }
            final String Rang2 = Range.getString("Range.Rang Spieler" + p.getName());
            if (a == 3) {
                event.setQuitMessage("§f[§c-§f] §4" + p.getName());
            } else if (a == 4) {
                event.setQuitMessage("§f[§c-§f] §7" + p.getName());
            } else if (a == 2) {
                event.setQuitMessage("§f[§c-§f] §7" + p.getName());
                if (Rang2.equals("Default")) {
                    int PlayerX = p.getLocation().getBlockX();
                    int PlayerY = p.getLocation().getBlockY();
                    int PlayerYund1 = PlayerY + 1;
                    int PlayerZ = p.getLocation().getBlockZ();
                    int PlayerZund1 = PlayerZ - 1;
                    Range.set("Spieler." + p.getName() + ".Location.X", PlayerX);
                    Main.getPlugin().saveConfig();
                    Range.set("Spieler." + p.getName() + ".Location.Y", PlayerY);
                    Main.getPlugin().saveConfig();
                    Range.set("Spieler." + p.getName() + ".Location.Z", PlayerZ);
                    Main.getPlugin().saveConfig();
                    Range.set("Spieler." + p.getName() + ".Location.Abbau", 0);
                    Main.getPlugin().saveConfig();

                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerY + " " + PlayerZ + " chest 0 replace {CustomName:" + p.getName() + "}");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerYund1 + " " + PlayerZ + " chest 0 replace {CustomName:" + p.getName() + "}");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerY + " " + PlayerZund1 + " minecraft:wall_sign");

                    Location sign = new Location(p.getWorld(), PlayerX, PlayerY, PlayerZund1);
                    Block block11 = sign.getBlock();
                    Sign s = (Sign) block11.getState();
                    s.setLine(0, "§c§l" + p.getName());
                    s.setLine(1, "Baue die Kisten");
                    s.setLine(2, "ab, um ihn von");
                    s.setLine(3, "§a§lSu§b§lRo §0zu §4bannen.");
                    s.update(true);

                    Block block12 = p.getLocation().getBlock();
                    Chest chest = (Chest) block12.getState();
                    Inventory inv = chest.getInventory();
                    for (int i = 0; i <= 17; i++) {
                        inv.setItem(i, p.getInventory().getItem(18 + i));
                    }
                    for (int i = 0; i <= 8; i++) {
                        inv.setItem(18 + i, p.getInventory().getItem(i));
                    }

                    Location loc = new Location(p.getWorld(), PlayerX, PlayerYund1, PlayerZ);
                    Block block13 = loc.getBlock();
                    Chest chest2 = (Chest) block13.getState();
                    Inventory inv2 = chest2.getInventory();
                    for (int i = 0; i <= 3; i++) {
                        inv2.setItem(i, p.getInventory().getItem(36 + i));
                    }
                    for (int i = 0; i <= 8; i++) {
                        inv2.setItem(18 + i, p.getInventory().getItem(9 + i));
                    }
                }
            } else if (a == 1) {
                event.setQuitMessage("");
            }
        } else {
            event.setQuitMessage(" ");
        }
    }
}
