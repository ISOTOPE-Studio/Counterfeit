package cc.isotopestudio.counterfeit;

import cc.isotopestudio.counterfeit.listener.PlayerListener;
import cc.isotopestudio.counterfeit.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Counterfeit extends JavaPlugin {

    private static final String pluginName = "Counterfeit";

    public static Counterfeit plugin;

    public static PluginFile config;

    @Override
    public void onEnable() {
        plugin = this;
        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);

        //this.getCommand("csclass").setExecutor(new CommandCsclass());
        FakePlayerUtil.playerList = config.getStringList("players");

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

}
