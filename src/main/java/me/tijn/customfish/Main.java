package me.tijn.customfish;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.Random;

public final class Main extends JavaPlugin implements Listener {
    ConsoleCommandSender c = Bukkit.getServer().getConsoleSender();
    @Override
    public void onEnable() {
        c.sendMessage("§3§lCustom Fish §l§fjust loaded in.");
        getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().addDefault("Main.prefix", "§b§lTijn.§f§lDev =>");
        this.getConfig().options().copyDefaults(true);
        saveConfig();
    }
    @Override
    public void onDisable() {
        c.sendMessage("§3§lCustom Fish §l§fjust shut down.");
    }
    @EventHandler
    public void onFishEvent(PlayerFishEvent e) {
        if(e.getCaught() instanceof Item) {
            Player p = e.getPlayer();
            List<String> FishItems = this.getConfig().getStringList("FishItems");
            Integer randomItem = new Random().nextInt(FishItems.size());
            Material m = Material.getMaterial(FishItems.get(randomItem).split("-")[1].toUpperCase());
            ItemStack i = new ItemStack(m);
            String name = i.getType().name().replaceAll("_"," ").toLowerCase();;
            Integer amount = Integer.parseInt(FishItems.get(randomItem).split("-")[0]);
            i.setAmount(amount);
            p.getInventory().addItem(i);
            String txt = "";
            if(i.getAmount() == 1 || name.equalsIgnoreCase("redstone")) {
                txt = " You just caught " + amount + " " + name;
            } else {
                txt = " You just caught " + amount + " " + name + "s";
            }
            e.getPlayer().sendMessage(this.getConfig().getString("Main.prefix") + txt);
        }
    }
}
