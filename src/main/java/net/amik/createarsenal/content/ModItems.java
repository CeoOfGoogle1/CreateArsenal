package net.amik.createarsenal.content;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.content.item.SpringItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateArsenal.MOD_ID);
    
    private static final CreateRegistrate REGISTRATE = CreateArsenal
        .registrate()
        .creativeModeTab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL);

    //items & details
    public static final RegistryObject<Item> SPRING = ITEMS.register("spring",
            () -> new SpringItem(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL)));

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

    //bullet parts


    //bullet assembly parts
    public static final RegistryObject<Item> BULLET_ASSEMBLY_LARGE = ITEMS.register("bullet_assembly_large", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> BULLET_ASSEMBLY_MEDIUM = ITEMS.register("bullet_assembly_medium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> BULLET_ASSEMBLY_SMALL = ITEMS.register("bullet_assembly_small", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_ASSEMBLY_LARGE = ITEMS.register("cartridge_assembly_large", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_ASSEMBLY_MEDIUM = ITEMS.register("cartridge_assembly_medium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_ASSEMBLY_SMALL = ITEMS.register("cartridge_assembly_small", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_EMPTY_LARGE = ITEMS.register("cartridge_empty_large", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_EMPTY_MEDIUM = ITEMS.register("cartridge_empty_medium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_EMPTY_SMALL = ITEMS.register("cartridge_empty_small", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_LARGE = ITEMS.register("cartridge_large", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_LARGE_UNFILLED = ITEMS.register("cartridge_large_unfilled", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_MEDIUM = ITEMS.register("cartridge_medium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_MEDIUM_UNFILLED = ITEMS.register("cartridge_medium_unfilled", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_SMALL = ITEMS.register("cartridge_small", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> CARTRIDGE_SMALL_UNFILLED = ITEMS.register("cartridge_small_unfilled", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE = ITEMS.register("round_large", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE_ARMORPIERCING = ITEMS.register("round_large_armorpiercing", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE_EMP = ITEMS.register("round_large_emp", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE_FLAK = ITEMS.register("round_large_flak", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE_HIGHEXPLOSIVE = ITEMS.register("round_large_highexplosive", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_LARGE_INCENDIARY = ITEMS.register("round_large_incendiary", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_MEDIUM = ITEMS.register("round_medium", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_MEDIUM_ARMORPIERCING = ITEMS.register("round_medium_armorpiercing", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_MEDIUM_HIGHEXPLOSIVE = ITEMS.register("round_medium_highexplosive", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_MEDIUM_INCENDIARY = ITEMS.register("round_medium_incendiary", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_SMALL = ITEMS.register("round_small", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_SMALL_ARMORPIERCING = ITEMS.register("round_small_armorpiercing", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_SMALL_HIGHEXPLOSIVE = ITEMS.register("round_small_highexplosive", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_SMALL_HOLLOWPOINT = ITEMS.register("round_small_hollowpoint", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));
    public static final RegistryObject<Item> ROUND_SMALL_INCENDIARY = ITEMS.register("round_small_incendiary", () -> new Item(new Item.Properties().tab(ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}