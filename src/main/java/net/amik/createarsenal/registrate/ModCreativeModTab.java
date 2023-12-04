package net.amik.createarsenal.registrate;

import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModTab{


    public static final CreativeModeTab CREATE_ARSENAL_TAB_GENERAL = new CreativeModeTab("createarsenal.tab.general") {
        @Override
        public @NotNull ItemStack makeIcon() {return new ItemStack(ModItems.SPRING.get());
        }
    };

    public static final CreativeModeTab CREATE_ARSENAL_TAB_BULLETS = new CreativeModeTab("createarsenal.tab.bullets") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.bulletItems.get(ShellScale.MEDIUM).get());
        }
    };

}

