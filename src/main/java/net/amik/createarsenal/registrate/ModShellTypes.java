package net.amik.createarsenal.registrate;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.shell.ShellProperties;
import net.amik.createarsenal.shell.ShellScale;
import net.amik.createarsenal.shell.ShellType;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModShellTypes {
  
  //!These are arbitrary values for now
  

  
  public static ShellType ARMOUR_PIERCING = ShellType.registerItems(REGISTRATE, "armour_piercing_shell", ShellType.generateScale(
      new ShellProperties(ShellScale.MEDIUM, 20000, 1000, 100, 100)
      ));
  
  public static ShellType BASE = ShellType.registerItems(REGISTRATE, "normal_shell", ShellType.generateScale(
      new ShellProperties(ShellScale.MEDIUM, 5000, 1000, 100, 100)
  ));
  
  public static void register() {}
  
}
