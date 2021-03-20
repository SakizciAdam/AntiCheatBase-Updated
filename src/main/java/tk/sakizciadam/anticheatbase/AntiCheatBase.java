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


package tk.sakizciadam.anticheatbase;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.sakizciadam.anticheatbase.cheats.CheatManager;
import tk.sakizciadam.anticheatbase.configs.ACConfig;
import tk.sakizciadam.anticheatbase.event.listener.UserListener;
import tk.sakizciadam.anticheatbase.user.UserManager;

public final class AntiCheatBase extends JavaPlugin {

    private static AntiCheatBase Instance;

    private CheatManager cheatManager;
    private UserManager userManager;
    private ACConfig checks;
    private boolean debug;

    @Override
    public void onEnable() {
        Instance=this;
        checks=new ACConfig(this,"checks");
        debug=checks.getConfig().getBoolean("Debug");
        this.userManager=new UserManager();
        this.cheatManager=new CheatManager();
        Bukkit.getPluginManager().registerEvents(new UserListener(),this);


    }

    @Override
    public void onDisable() {
        checks.save();
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public boolean isDebug() {
        return debug;
    }

    public CheatManager getCheatManager() {
        return cheatManager;
    }

    public static AntiCheatBase getInstance() {
        return Instance;
    }

    public ACConfig getCheckConfig() {
        return checks;
    }
}
