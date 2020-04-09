package space.guus.plugins.royalautoclicker.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import space.guus.plugins.royalautoclicker.RoyalAutoClicker;

public class ClickEvent implements Listener {

    private RoyalAutoClicker plugin;

    public ClickEvent(RoyalAutoClicker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() == Action.LEFT_CLICK_AIR){
            int count = 1;
            if(plugin.clickCount.get(e.getPlayer().getUniqueId()) != null){
                count += (Integer)this.plugin.clickCount.get(e.getPlayer().getUniqueId());
            }

            plugin.clickCount.put(e.getPlayer().getUniqueId(), count);
        }
    }
}
