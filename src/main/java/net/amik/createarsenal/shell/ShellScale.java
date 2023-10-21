package net.amik.createarsenal.shell;

import net.amik.createarsenal.item.ScaleItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public enum ShellScale {
  NONE, SMALL, MEDIUM, LARGE;

  static final String NBT_KEY="ArsenalShellScale";
   public static ShellScale getScaleFromItem(ItemStack stack){
    if(stack.getItem() instanceof ScaleItem item)
      return item.getScale();
    return NONE;
  }

  public static ShellScale getScaleFromItem(Item item){
    if(item instanceof ScaleItem scaleItem)
      return scaleItem.getScale();
    return NONE;
  }

  public void saveToNBT(CompoundTag tag){
     tag.putInt(NBT_KEY,this.ordinal());
  }

  public static ShellScale readFromNBT(CompoundTag tag){
     if(!tag.contains(NBT_KEY))
       return NONE;
     return values()[tag.getInt(NBT_KEY)];
  }

}
