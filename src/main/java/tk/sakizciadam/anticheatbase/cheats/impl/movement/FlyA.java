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

package tk.sakizciadam.anticheatbase.cheats.impl.movement;

import org.bukkit.event.EventHandler;
import tk.sakizciadam.anticheatbase.AntiCheatBase;
import tk.sakizciadam.anticheatbase.cheats.Category;
import tk.sakizciadam.anticheatbase.cheats.Cheat;
import tk.sakizciadam.anticheatbase.event.BaseEvent;
import tk.sakizciadam.anticheatbase.event.impl.MovementEvent;
import tk.sakizciadam.anticheatbase.user.User;
import tk.sakizciadam.anticheatbase.user.data.ActionData;

public class FlyA extends Cheat {
    public FlyA() {
        super("Fly A", Category.MOVEMENT);
    }

    @Override
    public void onEvent(BaseEvent e){
        if(e instanceof MovementEvent){
            MovementEvent event=(MovementEvent)e;
            User user= event.getUser();
            if(user.getMovementData().airTicks>9&&user.getMovementData().diffY>=0&&user.getMovementData().liquidTicks==0&&user.getActionData().since(ActionData.EnumAction.JOINED)>=5000){
                flag(user);
                e.setCancelled(true);
            }
        }
    }
}
