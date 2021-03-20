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


package tk.sakizciadam.anticheatbase.user.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import tk.sakizciadam.anticheatbase.AntiCheatBase;
import tk.sakizciadam.anticheatbase.cheats.Cheat;
import tk.sakizciadam.anticheatbase.event.BaseEvent;
import tk.sakizciadam.anticheatbase.event.impl.MovementEvent;
import tk.sakizciadam.anticheatbase.user.User;

public class MovementData {
    public int airTicks=0,groundTicks=0,liquidTicks=0;
    public Location safeLocation;

    public double lastDeltaY,deltaY=-999,diffY,deltaXZ;

    public void handle(BaseEvent event){
        if(event instanceof MovementEvent){
            Player player=event.player;
            MovementEvent movementEvent=(MovementEvent)event;
            User user=movementEvent.getUser();


            if(!user.isOnline()){
                return;
            }



            if(deltaY!=-999){
                lastDeltaY=deltaY;
            }

            if(player.isOnGround()){
                airTicks=0;
                groundTicks++;
                if(groundTicks>=40&&user.getActionData().since(ActionData.EnumAction.FLAGGED)>=15000l){
                    safeLocation=player.getLocation();
                }
            } else {
                airTicks++;
                groundTicks=0;

            }

            if(user.inWater()){
                liquidTicks++;
            } else {
                liquidTicks=0;
            }

            deltaY=movementEvent.getEvent().getTo().getY()-movementEvent.getEvent().getFrom().getY();
            deltaXZ=+((movementEvent.getEvent().getTo().getX()-movementEvent.getEvent().getFrom().getX())+(movementEvent.getEvent().getTo().getZ()-movementEvent.getEvent().getFrom().getZ()));
            diffY=deltaY-lastDeltaY;


            if(!user.canBypass()){
                for(Cheat cheat: AntiCheatBase.getInstance().getCheatManager().getCheats()){
                    if(!event.isCancelled()) cheat.onEvent(event);

                }
            }

            if(event.isCancelled()){
                if(safeLocation!=null){
                    player.teleport(safeLocation);
                } else {
                    movementEvent.getEvent().setTo(movementEvent.getEvent().getFrom());
                }
            }
        }


    }
}
