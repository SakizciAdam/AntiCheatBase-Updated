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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.sakizciadam.anticheatbase.AntiCheatBase;
import tk.sakizciadam.anticheatbase.user.data.ActionData;

import java.util.ArrayList;

public class UserManager implements Listener {
    private ArrayList<User> users=new ArrayList<>();

    public UserManager(){
        Bukkit.getServer().getPluginManager().registerEvents(this, AntiCheatBase.getInstance());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event){
        Player player=event.getPlayer();

        if(hasPlayer(player)){
            User user=getUser(player);

            users.remove(user);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player=event.getPlayer();

        if(hasPlayer(player)){
            User user=getUser(player);

            users.remove(user);
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player=event.getPlayer();

        if(hasPlayer(player)){
            User user=getUser(player);

            users.remove(user);
        }
        User user=new User(player.getUniqueId());
        user.getActionData().onAction(ActionData.EnumAction.JOINED);
        users.add(user);

    }


    public User getUser(Player player){
        for(User user:users){
            if(user.isOnline()&&user.getPlayer()!=null&&user.getPlayer()==player){
                return user;
            }
        }
        return null;
    }

    public boolean hasPlayer(Player player){
        for(User user:users){
            if(user.isOnline()&&user.getPlayer()!=null&&user.getPlayer()==player){
                return true;
            }
        }
        return false;
    }
}
