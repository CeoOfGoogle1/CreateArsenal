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

    //barrels

    public static final RegistryObject<Item> GUN_BARREL_SMALL = ITEMS.register("gun_barrel_small",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    public static final RegistryObject<Item> GUN_BARREL_MEDIUM = ITEMS.register("gun_barrel_medium",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    public static final RegistryObject<Item> GUN_BARREL_LARGE = ITEMS.register("gun_barrel_large",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    public static final RegistryObject<Item> BRASS_GUN_BARREL_SMALL = ITEMS.register("brass_gun_barrel_small",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    public static final RegistryObject<Item> BRASS_GUN_BARREL_MEDIUM = ITEMS.register("brass_gun_barrel_medium",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

    /* public static final RegistryObject<Item> BRASS_GUN_BARREL_LARGE = ITEMS.register("brass_gun_barrel_large",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));*/

    //bullets

    public static final RegistryObject<Item> BULLET_SMALL = ITEMS.register("bullet_small",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));

    public static final RegistryObject<Item> BULLET_MEDIUM = ITEMS.register("bullet_medium",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));

    public static final RegistryObject<Item> BULLET_BIG = ITEMS.register("bullet_large",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}