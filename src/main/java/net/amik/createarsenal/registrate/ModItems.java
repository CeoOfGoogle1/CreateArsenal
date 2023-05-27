package net.amik.createarsenal.registrate;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.item.SpringItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModItems {


    /** Items and Details */

    public static final RegistryEntry<SpringItem> SPRING = REGISTRATE.item("spring", SpringItem::new)
            .register();

    public static final RegistryEntry<Item> IRON_ROD = REGISTRATE.item("iron_rod", Item::new)
            .register();


    public static final RegistryEntry<Item> BRASS_ROD = REGISTRATE.item("brass_rod", Item::new)
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_SMALL = REGISTRATE.item("gun_barrel_small", Item::new)
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_MEDIUM = REGISTRATE.item("gun_barrel_medium", Item::new)
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_LARGE = REGISTRATE.item("gun_barrel_large", Item::new)
            .register();

    public static final RegistryEntry<Item> BULLET_SMALL = REGISTRATE.item("bullet_small", Item::new)
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> BULLET_MEDIUM = REGISTRATE.item("bullet_medium", Item::new)
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> BULLET_LARGE = REGISTRATE.item("bullet_large", Item::new)
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static void register() {}
}