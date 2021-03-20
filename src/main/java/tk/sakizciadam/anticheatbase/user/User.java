/*
MIT License

Copyright (c) 2021 SakizciAdam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/


package tk.sakizciadam.anticheatbase.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.sakizciadam.anticheatbase.AntiCheatBase;
import tk.sakizciadam.anticheatbase.cheats.Cheat;
import tk.sakizciadam.anticheatbase.user.data.ActionData;
import tk.sakizciadam.anticheatbase.user.data.MovementData;
import tk.sakizciadam.anticheatbase.utils.ConfigUtils;
import tk.sakizciadam.anticheatbase.utils.MessageUtils;

import java.util.HashMap;
import java.util.UUID;

public class User {
    private UUID uuid;
    private MovementData movementData;
    private ActionData actionData;
    private HashMap<Cheat,Integer> flags=new HashMap<>();
    private boolean punished;


    public User(UUID uuid){
        this.uuid=uuid;
        this.movementData=new MovementData();
        this.actionData=new ActionData();
    }

    public MovementData getMovementData() {
        return movementData;
    }

    public ActionData getActionData() {
        return actionData;
    }

    public void flag(Cheat cheat){
        new BukkitRunnable() {

            @Override
            public void run() {
                if(isOnline()){

                    flags.put(cheat,flags.getOrDefault(cheat,0)+1);
                    String msg=ConfigUtils.getFlagMessage();
                    msg=msg.replaceAll("%player%",getPlayer().getName());
                    msg=msg.replaceAll("%cheat%",cheat.getName());
                    msg=msg.replaceAll("%flag%",""+getFlag(cheat));
                    MessageUtils.sendMessageToAdmins(ChatColor.translateAlternateColorCodes('&',msg));
                    actionData.onAction(ActionData.EnumAction.FLAGGED);
                    if(getFlag(cheat)>=cheat.getMax()&&cheat.getMax()>0){
                        punish(cheat);
                    }
                }

            }

        }.runTaskLater(AntiCheatBase.getInstance(), 20);



    }

    public void punish(Cheat cheat){
        if(!punished){
            punished=true;
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(isOnline()){
                        String msg=ConfigUtils.getActionMessage();
                        msg=msg.replaceAll("%player%",getPlayer().getName());
                        msg=msg.replaceAll("%cheat%",cheat.getName());
                        MessageUtils.sendMessageToAdmins(ChatColor.translateAlternateColorCodes('&',msg));
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),ConfigUtils.getAction().replaceAll("%player%", getPlayer().getName()).replaceAll("%cheat%",cheat.getName()));
                        flags.clear();
                    }

                }

            }.runTaskLater(AntiCheatBase.getInstance(), 20);
        }

    }

    public Block getBlock(){
        Location loc=getPlayer().getLocation();
        loc.add(0,-1,0);
        return loc.getBlock();


    }

    public boolean inWater(){
        return getBlock().getType()==Material.WATER||getBlock().getType()==Material.LAVA;
    }

    public int getFlag(Cheat cheat){
        return flags.getOrDefault(cheat,0);
    }

    public boolean canBypass(){
        return getPlayer().hasPermission("anticheat.bypass");
    }

    public boolean canBeAlerted(){
        return getPlayer().hasPermission("anticheat.alert");
    }

    public Player getPlayer(){
        if(!isOnline()) return null;
        for(Player player: Bukkit.getServer().getOnlinePlayers()){
            if(player.getUniqueId().equals(uuid)){
                return player;
            }
        }
        return null;

    }

    public boolean isOnline(){
        for(Player player: Bukkit.getServer().getOnlinePlayers()){
            if(player.getUniqueId().equals(uuid)){
                return true;
            }
        }
        return false;
    }
}
