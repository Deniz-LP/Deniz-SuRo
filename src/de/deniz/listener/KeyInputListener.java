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
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import de.deniz.main.Main;

@SuppressWarnings("unused")
public class KeyInputListener implements Listener {
	FileConfiguration Range = Main.getPlugin().getConfig();
	
	 @EventHandler
	    public void KeyInput(PlayerInputEvent e) {
		 System.out.println("tset");
	        Player player = e.getPlayer();
	        if (Range.contains("Range.Rang Spieler" + player.getName())) {
	            final String Rang = Range.getString("Range.Rang Spieler" + player.getName());
	            if (!Rang.equals("gamemaster")) {
	            player.kickPlayer(Main.pr + " ßc÷ffne nicht F3, um zu vermeiden dass man die Koordinaten sieht!");
	            }
	            	
	        }
	 }    
    
}
