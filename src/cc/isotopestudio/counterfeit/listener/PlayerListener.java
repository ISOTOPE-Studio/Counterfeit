package cc.isotopestudio.counterfeit.listener;
/*
 * Created by Mars Tan on 8/10/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.counterfeit.FakePlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cc.isotopestudio.counterfeit.Counterfeit.plugin;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                FakePlayerUtil.addPlayer(event.getPlayer());
            }
        }.runTaskLater(plugin, 40);
    }

    @EventHandler
    public void onPlayerCMD(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/list")) {
            event.setCancelled(true);
            List<String> nameList = Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toList());
            nameList.addAll(FakePlayerUtil.playerList);
            event.getPlayer().sendMessage("§7[§b系统§7]当前有 §b" + nameList.size()
                    + " §7个玩家在线,最大在线人数为 §b" + Bukkit.getMaxPlayers() + " §7个玩家.");
            String s = nameList.toString();
            s = s.substring(1, s.length() - 2);
            event.getPlayer().sendMessage("§7[§b系统§7]§r" + s);
        } else if (event.getMessage().startsWith("/tpa ")) {
            String s = event.getMessage().replaceAll("/tpa ", "");
            Optional<String> first = FakePlayerUtil.playerList.stream().filter(s::equalsIgnoreCase).findFirst();
            if (first.isPresent()) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§7[§b系统§7]请求已发送给 " + first.get());
            }
        }
    }
}
