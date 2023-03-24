package net.amik.createarsenal.registrate;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModTab{


    public static final CreativeModeTab CREATE_ARSENAL_TAB_GENERAL = new CreativeModeTab("createarsenaltabgeneral") {
        @Override
        public ItemStack makeIcon() {return new ItemStack(ModItems.SPRING.get());
        }
    };

    public static final CreativeModeTab CREATE_ARSENAL_TAB_BULLETS = new CreativeModeTab("createarsenaltabbullets") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BULLET_MEDIUM.get());
        }
    };

}

