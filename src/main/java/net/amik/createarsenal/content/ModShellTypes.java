package net.amik.createarsenal.content;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.content.shell.ShellProperties;
import net.amik.createarsenal.content.shell.ShellScale;
import net.amik.createarsenal.content.shell.ShellType;

public class ModShellTypes {
  
  //!These are arbitrary values for now
  
  private static final CreateRegistrate REGISTRATE = CreateArsenal
      .registrate()
      .creativeModeTab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS);
  
  public static ShellType ARMOUR_PIERCING = ShellType.registerItems(REGISTRATE, "armour_piercing_shell", ShellType.generateScale(
      new ShellProperties(ShellScale.MEDIUM, 20000, 1000, 100, 100)
      ));
  
  public static ShellType BASE = ShellType.registerItems(REGISTRATE, "normal_shell", ShellType.generateScale(
      new ShellProperties(ShellScale.MEDIUM, 5000, 1000, 100, 100)
  ));
  
  public static void register() {}
  
}
