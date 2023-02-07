package net.ceoofgoogle.createarsenal.item;

import net.ceoofgoogle.createarsenal.CreateArsenal;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateArsenal.MOD_ID);
    //items & details

    public static final RegistryObject<Item> SPRING = ITEMS.register("spring",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    public static final RegistryObject<Item> IRON_ROD = ITEMS.register("iron_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));
    public static final RegistryObject<Item> BRASS_ROD = ITEMS.register("brass_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}