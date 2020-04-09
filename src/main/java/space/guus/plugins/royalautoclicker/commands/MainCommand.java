package space.guus.plugins.royalautoclicker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.guus.plugins.royalautoclicker.RoyalAutoClicker;

public class MainCommand implements CommandExecutor {

    private RoyalAutoClicker plugin;

    public MainCommand(RoyalAutoClicker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            player.sendMessage(plugin.translate("&3&lRoyalAutoClicker (v" + plugin.getPluginVersion() + ") by Gusuu."));
        }else{
            plugin.getServer().getConsoleSender().sendMessage(plugin.translate("&3&lRoyalAutoClicker (v" + plugin.getPluginVersion() + ") by Gusuu."));
        }
        return true;
    }
}
