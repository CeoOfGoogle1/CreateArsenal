package net.amik.createarsenal.shell;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.amik.createarsenal.item.ShellItem;

public record ShellProperties(ShellScale scale, int piercing, int velocity, int drag, int drop) {
  
  public static ShellProperties registerItems(CreateRegistrate registrate, String id, ShellProperties properties) {
    //Todo fill out models
    registrate.item(id, p -> new ShellItem(p, properties)).register();
    
    return properties;
  }

}
