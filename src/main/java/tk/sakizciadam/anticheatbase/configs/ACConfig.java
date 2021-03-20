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


package tk.sakizciadam.anticheatbase.configs;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.sakizciadam.anticheatbase.AntiCheatBase;

import java.io.File;
import java.io.IOException;

public class ACConfig {
    private File file;
    private FileConfiguration config;

    private String name;
    private AntiCheatBase base;

    public ACConfig(AntiCheatBase base,String name){
        this.base=base;
        this.name=name+".yml";
        create();
    }





    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void create()  {
        file = new File(base.getDataFolder(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            base.saveResource(name, false);
        }

        config= new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void reload(){
        create();
    }
}
