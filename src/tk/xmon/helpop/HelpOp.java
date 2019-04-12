package tk.xmon.helpop;

import com.github.theholywaffle.teamspeak3.*;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import tk.xmon.helpop.command.HelpOpCommand;

public class HelpOp extends JavaPlugin {
    public static TS3Config config = new TS3Config();
    public static TS3Query query = new TS3Query(config);
    public static TS3Api api = query.getApi();
    public static TS3ApiAsync aapi = query.getAsyncApi();

    private static HelpOp instance;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config.setHost(getInstance().getConfig().getString("ts3.host"));
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        query.connect();
        api.selectVirtualServerById(1);
        api.login(getInstance().getConfig().getString("ts3.login"), getInstance().getConfig().getString("ts3.password"));
        api.setNickname("x-HelpOp by Xm0n");
        this.getCommand("helpop").setExecutor(new HelpOpCommand());
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onDisable() {
        if(query.isConnected()){
            query.exit();
        }
        instance = null;
    }

    public static HelpOp getInstance(){
        return instance;
    }
}
