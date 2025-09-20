package de.deniz.listener;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import de.deniz.main.Main;

@SuppressWarnings("unused")
public class JoinListener implements Listener {

    public static int taskid = 1;
    private static final String LIVE_PREFIX = "§f§l[§3§lLIVE§f§l] ";
    private static final ArrayList<String> poisoned = new ArrayList<>();
	private static boolean isAir;
    private final FileConfiguration config = Main.getPlugin().getConfig();

    private boolean isPlayerHead(Block block) {
        Material type = block.getType();
        return type == Material.PLAYER_HEAD || type.name().equals("PLAYER_WALL_HEAD");
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();

        if (Main.Specs.contains(p)) {
            if (!Main.GAME_ENDED) {
                p.sendMessage(Main.pr + "§cEntschuldige, als Zuschauer darfst du nichts schreiben :/");
                e.setCancelled(true);
            } else {
                e.setFormat("§cX §7" + p.getName() + " §6§ §7" + message);
            }
        } else if (Main.Gamemaster.contains(p)) {
            if (Main.Live.contains(p)) {
                e.setFormat(LIVE_PREFIX + "§4" + p.getName() + " §6§ §c" + message);
            } else {
                e.setFormat("§4" + p.getName() + " §6§ §c" + message);
            }
        } else if (Main.AlleSpieler.contains(p)) {
            if (Main.Live.contains(p)) {
                e.setFormat(LIVE_PREFIX + "§8" + p.getName() + " §6§ §f" + message);
            } else {
                e.setFormat("§8" + p.getName() + " §6§ §f" + message);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void whenChestDestroyed(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        int blockYMinus1 = block.getLocation().getBlockY() - 1;

        if (Main.gamestart && (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
            for (OfflinePlayer current : Bukkit.getOfflinePlayers()) {
                String base = "Spieler." + current.getName() + ".Location.";
                if (!current.isOnline() && config.get(base + "X") != null
                        && block.getX() == config.getInt(base + "X")) {
                    boolean yMatch = block.getY() == config.getInt(base + "Y");
                    boolean yMinus1Match = blockYMinus1 == config.getInt(base + "Y");
                    boolean zMatch = block.getZ() == config.getInt(base + "Z");
                    if ((yMatch || yMinus1Match) && zMatch) {
                        int abbau = config.getInt(base + "Abbau");
                        if (abbau == 0) {
                            player.sendMessage(Main.pr + "§aDu hast die 1. Kiste von §b" + current.getName() + "§a abgebaut!");
                            config.set(base + "Abbau", 1);
                            Main.getPlugin().saveConfig();
                        } else if (abbau == 1) {
                            config.set("Spieler." + current.getName(), null);
                            config.set("Range.Rang Spieler" + current.getName(), null);
                            config.set("Range.Rang Spieler" + current.getName(), "ausgeschieden");
                            config.set("Range.Rang.Grund Spieler" + current.getName() + ".Grund", "Kistenabbau von " + player.getName());
                            Main.getPlugin().saveConfig();
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendMessage("§c\u271e §c" + current.getName() + " §f§ §a" + player.getName() + "§7(Kiste)");
                                p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0f, 1.0f);
                            }
                            if (current.isOnline()) {
                                Player p = (Player) current;
                                String grund = config.getString("Range.Rang.Grund Spieler" + current.getName() + ".Grund");
                                p.kickPlayer(Main.pr + "§cDu bist aus dem Projekt ausgeschieden! Grund: " + grund);
                            }
                        }
                    }
                }
            }
        }

        if (isPlayerHead(block)) {
            player.sendMessage("Skull");
            if (!Main.Gamemaster.contains(player)) {
                e.setCancelled(true);
            }
        }

        if (!Main.gamestart || Main.stopped) {
            if (!Main.Gamemaster.contains(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerMoveBeforeStart(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        e.setCancelled(true);

        if (Bukkit.getWorld("world").isThundering() && !Main.acid) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
        }

        if (Main.gamestart) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                config.set("LastLogoutPositionX" + p.getName(), p.getLocation().getBlockX());
                config.set("LastLogoutPositionY" + p.getName(), p.getLocation().getBlockY());
                config.set("LastLogoutPositionZ" + p.getName(), p.getLocation().getBlockZ());
                Main.getPlugin().saveConfig();
            }
        }

        String rang = config.getString("Range.Rang Spieler" + player.getName());
        if ("ausgeschieden".equals(rang)) {
            String grund = config.getString("Range.Rang.Grund Spieler" + player.getName() + ".Grund");
            player.kickPlayer(Main.pr + "§cDu bist aus dem Projekt ausgeschieden! Grund: " + grund);
            return;
        }

        if (Main.acid) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                int highestY = p.getLocation().getWorld().getHighestBlockYAt(p.getLocation());
                if (highestY <= p.getLocation().getY()) {
                    isAir = false;
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 3));
                } else {
                    p.removePotionEffect(PotionEffectType.WITHER);
                }
            }
        }

        if (!Main.Gamemaster.contains(player) && !Main.Specs.contains(player)) {
            if (Main.introstarted) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Main.AlleSpieler.contains(p) && !Main.randomPlayerFürIntro.getName().equalsIgnoreCase(p.getName())) {
                        p.teleport(new Location(p.getWorld(), 111.6, 99.8, 12294.9, 198.0f, 27.0f));
                    }
                }
            } else if (!Main.gamestart || (Main.stopped && config.contains("LastLogoutPositionX" + player.getName()))) {
                int x = config.getInt("LastLogoutPositionX" + player.getName());
                int y = config.getInt("LastLogoutPositionY" + player.getName());
                int z = config.getInt("LastLogoutPositionZ" + player.getName());
                Location loc = player.getLocation();
                if (loc.getBlockX() != x || loc.getBlockZ() != z) {
                    player.teleport(new Location(player.getWorld(), x + 0.5, y, z + 0.5));
                }
            }
        }
    }

    @EventHandler
    public void PlayerDamageBeforeStart(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if ((!Main.gamestart && !Main.Gamemaster.contains(p)) || (Main.stopped && !Main.Gamemaster.contains(p))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerOpenBeforeStart(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if ((Main.stopped || !Main.gamestart) && !Main.Gamemaster.contains(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerAttackBeforeStart(PlayerDeathEvent e) {
        Player tot = e.getEntity().getPlayer();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10.0f, 1.0f);
        }

        if (!Main.Gamemaster.contains(tot)) {
            Main.AlleSpieler.remove(tot);
            config.set("Range.Rang Spieler" + tot.getName(), null);
            config.set("Range.Rang Spieler" + tot.getName(), "spec");
            int anzahl = Main.ANzahlSpieler = config.getInt("Anzahl.Spieler") - 1;
            config.set("Anzahl.Spieler", anzahl);
            Main.getPlugin().saveConfig();
        } else {
            e.setDeathMessage(" ");
        }

        if (!Main.Gamemaster.contains(tot)) {
            Player killer = e.getEntity().getKiller();
            if (killer != null) {
                String killsKey = "Kills.Spieler" + killer.getName();
                int kills = config.getInt(killsKey, 0) + 1;
                config.set(killsKey, kills);
                if (Main.AlleSpieler.contains(killer)) {
                    killer.setPlayerListName("§8" + killer.getName() + " §c\u2694\ufe0f " + kills);
                }
                Bukkit.broadcastMessage("§c\u271e " + tot.getName() + " §f§ §a" + killer.getName());
                config.set("Range.Rang.Grund Spieler" + tot.getName() + ".Grund", "Getötet von " + killer.getName());
            } else {
                Bukkit.broadcastMessage("§c\u271e §c" + tot.getName() + " §f§ §anatürlich");
                config.set("Range.Rang.Grund Spieler" + tot.getName() + ".Grund", "Natürlicher Tod");
            }
            Main.getPlugin().saveConfig();
            if (tot.isOnline()) {
                tot.kickPlayer(Main.pr + "§cDu bist aus dem Projekt ausgeschieden! \n \n Grund: " + config.getString("Range.Rang.Grund Spieler" + tot.getName() + ".Grund"));
            }
            Bukkit.broadcastMessage(Main.pr + "§fÜbrig: §a" + Main.AlleSpieler.size() + "§f Spieler.");
            e.setDeathMessage("");
        } else {
            e.setDeathMessage(" ");
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;
        List<Block> blockListCopy = new ArrayList<>(event.blockList());
        for (Block block : blockListCopy) {
            if (isPlayerHead(block)) {
                event.blockList().remove(block);
            }
        }
        if (!Main.gamestart) {
            event.setCancelled(true);
        }
    }
}
