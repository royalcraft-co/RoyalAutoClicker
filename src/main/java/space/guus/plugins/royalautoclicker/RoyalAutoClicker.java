package space.guus.plugins.royalautoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import space.guus.plugins.royalautoclicker.commands.MainCommand;
import space.guus.plugins.royalautoclicker.events.ClickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

public final class RoyalAutoClicker extends JavaPlugin {

    private String pluginVersion;
    private HashMap<UUID, Double> clickRate = new HashMap<>();
    public HashMap<UUID, Integer> clickCount = new HashMap<>();
    ConsoleCommandSender console = Bukkit.getConsoleSender();


    @Override
    public void onEnable() {
        this.setPluginVersion(getDescription().getVersion());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Register commands
        getCommand("autoclicker").setExecutor(new MainCommand(this));
        // Register events
        getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    private void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public String translate(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void getRate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            LinkedList<Double> ll = new LinkedList();
            double sum = 0.0D;
            double total = 0.0D;

            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if (RoyalAutoClicker.this.clickCount.containsKey(p.getUniqueId())) {
                        double count = (double)(Integer)RoyalAutoClicker.this.clickCount.get(p.getUniqueId());
                        if(count > (double)RoyalAutoClicker.this.getConfig().getInt("Click-Until-Ban")){
                            getServer().dispatchCommand(console, getConfig().getString("Ban-CMD").replaceAll("%player%", p.getName()));
                        }
                        if (count > (double)RoyalAutoClicker.this.getConfig().getInt("Click-Until-Notify")) {
                            for(Player on : Bukkit.getOnlinePlayers()) {
                                getServer().dispatchCommand(console, getConfig().getString("Notify-CMD").replaceAll("%player%", p.getName()).replaceAll("%cps%", String.valueOf(count)));
                            }
                        }

                        if (count > 0.0D) {
                            this.ll.add(count);

                            for(Iterator localIterator = this.ll.iterator(); localIterator.hasNext(); this.total = this.sum + count) {
                                double x = (Double)localIterator.next();
                            }
                        }

                        this.total = 0.0D;

                        try {
                            this.ll.remove();
                        } catch (Exception ignored) {
                        }

                        RoyalAutoClicker.this.clickRate.put(p.getUniqueId(), count);
                        RoyalAutoClicker.this.clickCount.put(p.getUniqueId(), 0);
                    }
                }
            }
        }, 0L, 20L);
    }
}