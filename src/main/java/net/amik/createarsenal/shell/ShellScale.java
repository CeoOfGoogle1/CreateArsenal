package net.amik.createarsenal.shell;

import net.amik.createarsenal.item.ScaleItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public enum ShellScale {
    SMALL, MEDIUM, LARGE;

    static final String NBT_KEY="ArsenalShellScale";
    public static ShellScale getScaleFromItem(ItemStack stack){
        if(stack.getItem() instanceof ScaleItem item)
            return item.getScale();
        return null;
    }

    public void saveToNBT(CompoundTag tag){
        tag.putInt(NBT_KEY,this.ordinal());
    }

    public static ShellScale readFromNBT(CompoundTag tag){
        if(!tag.contains(NBT_KEY))
            return null;
        return values()[tag.getInt(NBT_KEY)];
    }

}
