package cc.isotopestudio.counterfeit;
/*
 * Created by Mars Tan on 8/10/2017.
 * Copyright ISOTOPE Studio
 */

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cc.isotopestudio.counterfeit.Counterfeit.plugin;

public abstract class FakePlayerUtil {

    public static List<String> playerList;

    public static void addPlayer(Player player) {
//        playerList.forEach(s -> addPlayer(s, player));
        addPlayer(new ArrayList<>(playerList), player);
    }

    private static void addPlayer(List<String> remains, Player sendTo) {
        if (remains.size() > 0) {
            String name = remains.remove(0);
            new BukkitRunnable() {
                @Override
                public void run() {
                    addPlayer(remains, sendTo);
                    GameProfile profile = new GameProfile(UUID.randomUUID(), name);
                    MinecraftServer server = MinecraftServer.getServer();
                    // Get the Minecraft server object, required to create a player.
                    // UPDATE:  In newer versions, this method returns null, so instead get the  MinecraftServer
                    //         with this code instead:
                    //    ((CraftServer) Bukkit.getServer()).getServer();

                    WorldServer world = server.getWorldServer(0);
                    // Get the world server for the overworld (0). Also required.

                    PlayerInteractManager manager = new PlayerInteractManager(world);
                    // Create a new player interact manager for the overworld.  Required.

                    EntityPlayer player = new EntityPlayer(server, world, profile, manager);
                    // Finally, create the entity player with all of the pieces.
                    PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, player);
                    // Creates a packet that will add our new EntityPlayer "player" to the tab list.
                    ((CraftPlayer) sendTo).getHandle().playerConnection.sendPacket(packet);
                }
            }.runTaskLater(plugin, 1);
        }
    }

}
