package net.amik.createarsenal.content.item;

import net.amik.createarsenal.content.shell.ShellProperties;
import net.amik.createarsenal.content.shell.ShellScale;
import net.minecraft.world.item.Item;

public class ShellItem extends Item {
  
  ShellProperties properties;
  
  public ShellItem(Properties p_41383_, ShellProperties properties) {
    super(p_41383_);
    this.properties = properties;
  }
  
  public ShellScale getScale() {
    return properties.scale();
  }
}
