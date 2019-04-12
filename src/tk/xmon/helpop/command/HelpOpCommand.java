package tk.xmon.helpop.command;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.xmon.helpop.HelpOp;
import tk.xmon.helpop.utils.ChatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HelpOpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            sender.sendMessage(ChatUtils.fixColor("&7>> &3Poprawne użycie: &8helpop [wiadomość]"));
            return true;
        }
        String msg = StringUtils.join(strings, " ");
        for (Player po : Bukkit.getOnlinePlayers()) {
            if(po.hasPermission("xmonhelpop.see")){
                po.sendMessage(ChatUtils.fixColor("&4HelpOp: &8Gracz &3" + sender.getName() + " &8napisał: &6" + msg));
            }
        }
        for(Client c : HelpOp.aapi.getClients().getUninterruptibly()) {
            if (c.isInServerGroup(HelpOp.getInstance().getConfig().getInt("ts3.group"))) {
                HelpOp.aapi.sendPrivateMessage(c.getId(), "[b][color=red][x-HelpOp] [color=gray]Gracz [color=blue]" + sender.getName() + " [color=gray]napisał: [color=yellow]" + msg);
            }
        }
        sender.sendMessage(ChatUtils.fixColor("&7>> &3Wiadomość wysłana&7!"));
        return false;
    }
}
