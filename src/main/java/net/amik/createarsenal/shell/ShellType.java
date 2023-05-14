package net.amik.createarsenal.shell;

import com.simibubi.create.foundation.data.CreateRegistrate;

public record ShellType(ShellProperties large, ShellProperties medium, ShellProperties small) {
  
  public static ShellType generateScale(ShellProperties properties) {
    //Todo Change values probably
    //Todo Medium
    return new ShellType(
        new ShellProperties(ShellScale.LARGE,
            (int) (properties.piercing() * 1.2),
            (int) (properties.velocity() * 1.2),
            (int) (properties.drag() * 1.1),
            (int) (properties.drop() * 1.1)),
        properties,
        new ShellProperties(ShellScale.SMALL,
            (int) (properties.piercing() * 0.75),
            (int) (properties.velocity() * 0.75),
            (int) (properties.drag() * 0.8),
            (int) (properties.drop() * 0.9))
    );
  }
  
  public static ShellType registerItems(CreateRegistrate registrate, String id, ShellType type) {

    /**ShellProperties.registerItems(registrate, id + "_small", type.small());
    ShellProperties.registerItems(registrate, id + "_medium", type.medium());
    ShellProperties.registerItems(registrate, id + "_large", type.large()); */
    return type;
  }
  
  public ShellProperties getScaleProperties(ShellScale scale) {
    return (scale == ShellScale.LARGE ? large : (scale == ShellScale.MEDIUM ? medium : small));
  }
  
}
