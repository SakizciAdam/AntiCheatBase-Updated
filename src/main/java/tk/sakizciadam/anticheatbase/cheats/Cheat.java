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

package tk.sakizciadam.anticheatbase.cheats;

import tk.sakizciadam.anticheatbase.AntiCheatBase;
import tk.sakizciadam.anticheatbase.event.BaseEvent;
import tk.sakizciadam.anticheatbase.user.User;

public class Cheat {
    private String name;
    private Category category;


    public Cheat(String name,Category category){
        this.name=name;
        this.category=category;

    }

    public void onEvent(BaseEvent event){

    }


    public int getMax(){
        String[] splited=name.split(" ");

        return AntiCheatBase.getInstance().getCheckConfig().getConfig().getInt(splited[0]+".MaxVL");
    }

    public void flag(User user){
        user.flag(this);
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}
