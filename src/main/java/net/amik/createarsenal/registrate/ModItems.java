package net.amik.createarsenal.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;

import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.amik.createarsenal.item.SpringItem;
import net.minecraft.world.item.Item;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

@SuppressWarnings("unused")
public class ModItems {


    /** Items and Details */

    public static final RegistryEntry<SpringItem> SPRING = REGISTRATE.item("spring", SpringItem::new)
            .model(NonNullBiConsumer.noop())
            .register();

    public static final RegistryEntry<Item> IRON_ROD = REGISTRATE.item("iron_rod", Item::new)
            .register();


    public static final RegistryEntry<Item> BRASS_ROD = REGISTRATE.item("brass_rod", Item::new)
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_SMALL = REGISTRATE.item("gun_barrel_small", Item::new)
            .lang("Small Gun Barrel")
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_MEDIUM = REGISTRATE.item("gun_barrel_medium", Item::new)
            .lang("Medium Gun Barrel")
            .register();

    public static final RegistryEntry<Item> GUN_BARREL_LARGE = REGISTRATE.item("gun_barrel_large", Item::new)
            .lang("Large Gun Barrel")
            .register();

    public static final RegistryEntry<Item> BULLET_SMALL = REGISTRATE.item("bullet_small", Item::new)
            .lang("Small Bullet")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .model(NonNullBiConsumer.noop())
            .register();

    public static final RegistryEntry<Item> BULLET_MEDIUM = REGISTRATE.item("bullet_medium", Item::new)
            .lang("Medium Bullet")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .model(NonNullBiConsumer.noop())
            .register();

    public static final RegistryEntry<Item> BULLET_LARGE = REGISTRATE.item("bullet_large", Item::new)
            .lang("Large Bullet")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .model(NonNullBiConsumer.noop())
            .register();

    public static final RegistryEntry<Item> LARGE_ROUND_CORE = REGISTRATE.item("large_round_core", Item::new)
            .lang("Large Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> MEDIUM_ROUND_CORE = REGISTRATE.item("medium_round_core", Item::new)
            .lang("Medium Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> SMALL_ROUND_CORE = REGISTRATE.item("small_round_core", Item::new)
            .lang("Small Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static void register() {}
}