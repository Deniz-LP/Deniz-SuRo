package de.deniz.util;

import de.deniz.main.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class Starting {
    static int taskid = 0;
    static int time;
    static FileConfiguration Range = Main.getPlugin().getConfig();

    // Startet den Timer für die Spielrunde
    public static void startTimer(final int time1, final Player pl) {
        time = time1;
        Bukkit.getScheduler().cancelTask(taskid);
        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                // Setzt Gamemode je nach Zeit
                if (time > 3600) {
                    Main.beforegamestart = true; Main.gamestart = false;
                    for (Player player : Bukkit.getOnlinePlayers())
                        if (Main.AlleSpieler.contains(player)) player.setGameMode(GameMode.ADVENTURE);
                } else if (time < 3600) {
                    Main.beforegamestart = true; Main.gamestart = true;
                    for (Player player : Bukkit.getOnlinePlayers())
                        if (Main.AlleSpieler.contains(player)) player.setGameMode(GameMode.SURVIVAL);
                }

                // Ablauf bei Zeit = 0 (Spielende)
                if (time == 0) {
                    Main.gamestart = false; Main.beforegamestart = false; time = Main.countdownTime;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kickPlayer(Main.pr + "§cVorbei für heute!");
                        if (Main.AlleSpieler.contains(player)) {
                            player.setGameMode(GameMode.ADVENTURE); time = Main.countdownTime;
                            int X = player.getLocation().getBlockX(), Y = player.getLocation().getBlockY() + 1, Z = player.getLocation().getBlockZ();
                            Location teleportLoc = new Location(player.getWorld(), X + 0.5, Y, Z + 0.5);
                            player.teleport(teleportLoc);

                            int PlayerX = player.getLocation().getBlockX(), PlayerY = player.getLocation().getBlockY(), PlayerZ = player.getLocation().getBlockZ();
                            int PlayerYund1 = PlayerY + 1, PlayerZund1 = PlayerZ - 1;

                            // Speichert Spielerposition und Status
                            Range.set("Spieler." + player.getName() + ".Location.X", PlayerX); Main.getPlugin().saveConfig();
                            Range.set("Spieler." + player.getName() + ".Location.Y", PlayerY); Main.getPlugin().saveConfig();
                            Range.set("Spieler." + player.getName() + ".Location.Z", PlayerZ); Main.getPlugin().saveConfig();
                            Range.set("Spieler." + player.getName() + ".Location.Abbau", 0); Main.getPlugin().saveConfig();

                            // Setzt Kisten und Schild
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerY + " " + PlayerZ + " chest 0 replace {CustomName:" + player.getName() + "}");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerYund1 + " " + PlayerZ + " chest 0 replace {CustomName:" + player.getName() + "}");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + PlayerX + " " + PlayerY + " " + PlayerZund1 + " minecraft:wall_sign");

                            Location signLoc = new Location(player.getWorld(), PlayerX, PlayerY, PlayerZund1);
                            Block signBlock = signLoc.getBlock();
                            Sign sign = (Sign) signBlock.getState();
                            sign.setLine(0, "§c§l" + player.getName());
                            sign.setLine(1, "Baue die Kisten");
                            sign.setLine(2, "ab, um ihn von");
                            sign.setLine(3, "§a§lSu§b§lRo §0zu §4bannen.");
                            sign.update(true);

                            // Inventar in Kisten kopieren
                            Block chestBlock1 = player.getLocation().getBlock();
                            Chest chest1 = (Chest) chestBlock1.getState();
                            Inventory inv1 = chest1.getInventory();
                            for (int i = 0; i <= 17; i++) inv1.setItem(i, player.getInventory().getItem(i + 18));
                            for (int i = 18; i <= 26; i++) inv1.setItem(i, player.getInventory().getItem(i - 18));

                            Location chest2Loc = new Location(player.getWorld(), PlayerX, PlayerYund1, PlayerZ);
                            Block chestBlock2 = chest2Loc.getBlock();
                            Chest chest2 = (Chest) chestBlock2.getState();
                            Inventory inv2 = chest2.getInventory();
                            for (int i = 0; i <= 3; i++) inv2.setItem(i, player.getInventory().getItem(i + 36));
                            for (int i = 18; i <= 26; i++) inv2.setItem(i, player.getInventory().getItem(i - 9));
                        }
                    }
                    Bukkit.getScheduler().cancelTask(taskid);
                }

                // Countdown-Nachrichten
                else if (time == 5 || time == 10 || time == 15 || time == 60 || time == 120)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aIn §2" + time + " §aSekunden ist die heute Spielerunde beendet!");
                else if (time == 30)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aSuch dir langsam einen Sicheren Platz zum ausloggen!");
                else if (time == 3)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§a3");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                else if (time == 2)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§c2");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                else if (time == 1)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§41");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                else if (time == 60)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aIn §2eine §aMinute ist die heute Spielerunde beendet!");
                else if (time == 300)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aIn §25 §aMinuten ist die heute Spielerunde beendet!.");
                else if (time == 600)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aIn §210 §aMinuten ist die heute Spielerunde beendet!.");
                else if (time == 1800)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aIn §230 §aMinuten ist die heute Spielerunde beendet!.");
                else if (time == 3600) {
                    Main.gamestart = true; time = Main.countdownTime;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Main.pr + "§aLasset die Spiele beginnen!");
                        player.sendTitle("  ", " ");
                        if (Main.AlleSpieler.contains(player)) {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 7.5f);
                        }
                    }
                }
                // Countdown vor Spielstart
                else if (time == 3605 || time == 3610 || time == 3615 || time == 3620 || time == 3660)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        int zeit = time - 3600;
                        player.sendMessage(Main.pr + "§aNoch §2" + zeit + " §aSekunden bis zum Start!");
                    }
                else if (time == 3603)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§a3");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                else if (time == 3602)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§c2");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                else if (time == 3601)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("  ", "§41");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.5f);
                    }
                // Vorstart-Nachrichten
                else if (time == 5400)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(Main.pr + "§aNoch §230 §aMinuten bis zum Start!");
                        Main.beforegamestart = true;
                    }
                else if (time == 4500)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aNoch §215 §aMinuten bis zum Start!");
                else if (time == 4200)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aNoch §210 §aMinuten bis zum Start!");
                else if (time == 3900)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aNoch §25 §aMinuten bis zum Start!");
                else if (time == 3720)
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage(Main.pr + "§aNoch §22 §aMinuten bis zum Start!");

                time--;
            }
        }, 0L, 20L);
    }

    // Gibt die verbleibende Zeit zurück
    public static int getTime() { return time; }

    // Stoppt den Timer
    public static void cancelTask() { Bukkit.getScheduler().cancelTask(taskid); }
}
