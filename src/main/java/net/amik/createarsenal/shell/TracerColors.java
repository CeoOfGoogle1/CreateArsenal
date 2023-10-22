package net.amik.createarsenal.shell;

import com.jozufozu.flywheel.util.Color;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TracerColors {
   public static final Color GREEN_INSIDE=new Color(182,255,0);
   public static final Color GREEN_OUTSIDE=new Color(42, 255, 0);
   public static final Color BLUE_INSIDE=new Color(0, 225, 255);
   public static final Color BLUE_OUTSIDE=new Color(0, 140, 255);
   public static final Color PINK_INSIDE=new Color(165, 0, 255);
   public static final Color PINK_OUTSIDE=new Color(102, 0, 255);
   public static final Color WHITE_INSIDE=new Color(255, 255, 255);
   public static final Color WHITE_OUTSIDE=new Color(255, 235, 201);
   public static final Color RED_INSIDE=new Color(255, 150, 150);
   public static final Color RED_OUTSIDE=new Color(255, 0, 16);

    public static void fillCreativeTab(NonNullList<ItemStack> pItems, Item item) {
        pItems.add(getColoredStack(item,GREEN_INSIDE,GREEN_OUTSIDE,"Green"));
        pItems.add(getColoredStack(item,BLUE_INSIDE,BLUE_OUTSIDE,"Blue"));
        pItems.add(getColoredStack(item,PINK_INSIDE,PINK_OUTSIDE,"Pink"));
        pItems.add(getColoredStack(item,WHITE_INSIDE,WHITE_OUTSIDE,"White"));
        pItems.add(getColoredStack(item,RED_INSIDE,RED_OUTSIDE,"Red"));
    }

    private static ItemStack getColoredStack(Item item, Color colorInside, Color colorOutside, String name) {
        ItemStack stack =new ItemStack(item);
        CompoundTag tag= stack.getOrCreateTag();
        tag.putString("tracerColorName",name);
        tag.putInt("insideColor",colorInside.getRGB());
        tag.putInt("outsideColor",colorOutside.getRGB());
        return stack;
    }

    public static void setBulletColor(BulletEntity bullet, ItemStack stack) {
        CompoundTag tag= stack.getOrCreateTag();
        if(tag.contains("insideColor")&& tag.contains("outsideColor"))
            bullet.setColor(tag.getInt("outsideColor"),tag.getInt("insideColor"));
    }
}
