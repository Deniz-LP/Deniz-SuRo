package de.deniz.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.deniz.command.*;
import de.deniz.listener.JoinListener;
import de.deniz.listener.KeyInputListener;
import de.deniz.listener.QuitAndJoinListener;
import de.deniz.util.Starting;

public class Main extends JavaPlugin
{
    private static Main plugin;
    public static String pr;
    public static String np;
    public static String nf;
    public static String Publisher;
    public static ArrayList<Player> Gamemaster;
    public static ArrayList<Player> AlleSpieler;
    public static ArrayList<Player> Specs;
    public static ArrayList<Player> Live;
    public static ArrayList<Player> Vanished;
    public static ArrayList<Player> OnlinePlayer;
    public static int taskid;
    public static int ANzahlSpieler;
    public static int ANzahlMaxSpieler;
    public static int TimeSpeichern;
    public static boolean gamestart;
    public static boolean beforegamestart;
    public static boolean stopped;
    public static boolean introstarted;
    public static boolean introstarted2;
    public static boolean GAME_ENDED;
    public static boolean acid;
    public static Player randomPlayerF\u00fcrIntro;
    public static Player randomPlayerF\u00fcrIntro2;
    public static Player Spieler1Vom2SpielerEvent;
    public static Player Spieler2Vom2SpielerEvent;
    public static boolean ZweiSpielerEventGestartet;
    public static String noPerms;
    public static int countdownTime;
    
    static {
        Main.pr = "§d§l[§a§lSu§b§lRo§d§l]§f ";
        Main.np = String.valueOf(Main.pr) + "§4§lDazu hast du keine Rechte.";
        Main.nf = String.valueOf(Main.pr) + "§c§lSpieler wurde nicht gefunden.";
        Main.Publisher = "Deno_LP";
        Main.Gamemaster = new ArrayList<Player>();
        Main.AlleSpieler = new ArrayList<Player>();
        Main.Specs = new ArrayList<Player>();
        Main.Live = new ArrayList<Player>();
        Main.Vanished = new ArrayList<Player>();
        Main.OnlinePlayer = new ArrayList<Player>();
        Main.taskid = 0;
        Main.noPerms = "NoPerms";
        Main.countdownTime = 3600;
    }
    
    public void onEnable() {
        Main.ANzahlMaxSpieler = 10;
        this.getConfig().set("Anzahl.Spieler", (Object)0);
        this.saveConfig();
        Main.stopped = false;
        Main.ZweiSpielerEventGestartet = false;
        Main.introstarted = false;
        Main.introstarted2 = false;
        OfflinePlayer[] offlinePlayers;
        for (int length = (offlinePlayers = Bukkit.getOfflinePlayers()).length, i = 0; i < length; ++i) {
            final OfflinePlayer current = offlinePlayers[i];
            if (this.getConfig().contains("Range.Rang Spieler" + current.getName())) {
                final String Rang = this.getConfig().getString("Range.Rang Spieler" + current.getName());
                if (Rang.equals("gamemaster")) {
                    Main.Gamemaster.add(Bukkit.getPlayer(current.getName()));
                }
                else if (Rang.equals("Default")) {
                    Main.AlleSpieler.add(Bukkit.getPlayer(current.getName()));
                    if (current.isOnline()) {
                        Main.OnlinePlayer.add(current.getPlayer());
                    }
                }
                else if (Rang.equals("spec")) {
                    Main.Specs.add(Bukkit.getPlayer(current.getName()));
                }
            }
        }
        for (final Player current2 : Bukkit.getOnlinePlayers()) {
            Main.AlleSpieler.add(current2);
        }
        final int Spieleranzahl1 = Main.ANzahlSpieler = this.getConfig().getInt("Anzahl.Spieler");
        Main.plugin = this;
        final PluginManager pm = Bukkit.getPluginManager();
        System.out.println("Das ist ein Test!");
        this.getCommand("groups").setExecutor((CommandExecutor)new Groups());
        this.getCommand("groupscmd").setExecutor((CommandExecutor)new Groupscmd());
        this.getCommand("disq").setExecutor((CommandExecutor)new Disq());
        this.getCommand("start").setExecutor((CommandExecutor)new start());
        this.getCommand("startcmd").setExecutor((CommandExecutor)new StartCmd());
        this.getCommand("cancel").setExecutor((CommandExecutor)new cancel());
        this.getCommand("mitspieler").setExecutor((CommandExecutor)new Mitspieler());
        this.getCommand("pause").setExecutor((CommandExecutor)new StopTimer());
        this.getCommand("setup").setExecutor((CommandExecutor)new Setup());
        this.getCommand("zwei").setExecutor((CommandExecutor)new ZweiSPielerEvent());
        this.getCommand("getloc").setExecutor((CommandExecutor)new GetLoc());
        //this.getCommand("telep").setExecutor((CommandExecutor)new TpToPlace());
        //this.getCommand("intro").setExecutor((CommandExecutor)new IntroSuro());
        this.getCommand("REAL").setExecutor((CommandExecutor)new REAL());
        this.getCommand("vanish").setExecutor((CommandExecutor)new Vanish());
        this.getCommand("spec").setExecutor((CommandExecutor)new TPSpec());
        this.getCommand("acidrain").setExecutor((CommandExecutor)new AcidRain());
        this.getCommand("v").setExecutor((CommandExecutor)new Vanish());
        this.getCommand("alert").setExecutor((CommandExecutor)new Alert());
        pm.registerEvents((Listener)new JoinListener(), (Plugin)this);
        pm.registerEvents((Listener)new QuitAndJoinListener(), (Plugin)this);
        //pm.registerEvents((Listener)new KeyInputListener(), (Plugin)this);
        if (this.getConfig().contains("Timer.Zeit")) {
            final int timer = this.getConfig().getInt("Timer.Zeit");
            if (timer != 0) {
                if (timer > 0 && timer < 3600) {
                    Starting.startTimer(timer, Bukkit.getPlayer("Deniz_LP"));
                    Main.gamestart = true;
                    Main.beforegamestart = true;
                }
                else {
                    Main.gamestart = false;
                    Main.beforegamestart = false;
                }
            }
        }
    }
    
    public static Main getPlugin() {
        return Main.plugin;
    }
    
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Ok.");
    }
}
