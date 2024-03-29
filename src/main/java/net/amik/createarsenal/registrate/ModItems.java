package net.amik.createarsenal.registrate;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.amik.createarsenal.item.BulletItem;
import net.amik.createarsenal.item.ScaleItem;
import net.amik.createarsenal.item.SpringItem;
import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

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

    public static final RegistryEntry<Item> ROUND_CORE_LARGE = REGISTRATE.item("round_core_large", Item::new)
            .lang("Large Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> ROUND_CORE_MEDIUM = REGISTRATE.item("round_core_medium", Item::new)
            .lang("Medium Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_CORE_SMALL = REGISTRATE.item("round_core_small", Item::new)
            .lang("Small Round Core")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_CUTOUT_LARGE = REGISTRATE.item("cartridge_cutout_large", Item::new)
            .lang("Large Cartridge Cutout")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> CARTRIDGE_CUTOUT_MEDIUM = REGISTRATE.item("cartridge_cutout_medium", Item::new)
            .lang("Medium Cartridge Cutout")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_CUTOUT_SMALL = REGISTRATE.item("cartridge_cutout_small", Item::new)
            .lang("Small Cartridge Cutout")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> JACKET_PLATE_LARGE = REGISTRATE.item("jacket_plate_large", Item::new)
            .lang("Large Jacket Plate")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> JACKET_PLATE_MEDIUM = REGISTRATE.item("jacket_plate_medium", Item::new)
            .lang("Medium Jacket Plate")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<Item> JACKET_PLATE_SMALL = REGISTRATE.item("jacket_plate_small", Item::new)
            .lang("Small Jacket Plate")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<SequencedAssemblyItem> CARTRIDGE_LARGE_INCOMPLETE = REGISTRATE.item("cartridge_large_incomplete", SequencedAssemblyItem::new)
            .lang("Large Cartridge (Incomplete)")
            .model(NonNullBiConsumer.noop())
            .register();

    public static final RegistryEntry<Item> CARTRIDGE_LARGE_EMPTY = REGISTRATE.item("cartridge_large_empty", Item::new)
            .lang("Large Cartridge (Empty)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<SequencedAssemblyItem> CARTRIDGE_MEDIUM_INCOMPLETE = REGISTRATE.item("cartridge_medium_incomplete", SequencedAssemblyItem::new)
            .lang("Medium Cartridge (Incomplete)")
            .model(NonNullBiConsumer.noop())
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_MEDIUM_EMPTY = REGISTRATE.item("cartridge_medium_empty", Item::new)
            .lang("Medium Cartridge (Empty)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    public static final RegistryEntry<SequencedAssemblyItem> CARTRIDGE_SMALL_INCOMPLETE = REGISTRATE.item("cartridge_small_incomplete", SequencedAssemblyItem::new)
            .lang("Small Cartridge (Incomplete)")
            .model(NonNullBiConsumer.noop())
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_SMALL_EMPTY = REGISTRATE.item("cartridge_small_empty", Item::new)
            .lang("Small Cartridge (Empty)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_LARGE_FILLED = REGISTRATE.item("cartridge_large_filled", Item::new)
            .lang("Large Cartridge (Filled)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_MEDIUM_FILLED = REGISTRATE.item("cartridge_medium_filled", Item::new)
            .lang("Medium Cartridge (Filled)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> CARTRIDGE_SMALL_FILLED = REGISTRATE.item("cartridge_small_filled", Item::new)
            .lang("Small Cartridge (Filled)")
            .model(NonNullBiConsumer.noop())
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_LARGE = REGISTRATE.item("round_large", Item::new)
            .lang("Large Round (Basic)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_LARGE_ARMORPIERCING = REGISTRATE.item("round_large_armorpiercing", Item::new)
            .lang("Large Round (AP)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_LARGE_FLAK = REGISTRATE.item("round_large_flak", Item::new)
            .lang("Large Round (Flak)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_LARGE_HIGHEXPLOSIVE = REGISTRATE.item("round_large_highexplosive", Item::new)
            .lang("Large Round (HE)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_LARGE_INCENDIARY = REGISTRATE.item("round_large_incendiary", Item::new)
            .lang("Large Round (Incendiary)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
     public static final RegistryEntry<Item> ROUND_MEDIUM = REGISTRATE.item("round_medium", Item::new)
             .lang("Medium Round (Basic)")
             .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
             .register();
    public static final RegistryEntry<Item> ROUND_MEDIUM_ARMORPIERCING = REGISTRATE.item("round_medium_armorpiercing", Item::new)
             .lang("Medium Round (AP)")
             .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
             .register();
    public static final RegistryEntry<Item> ROUND_MEDIUM_HIGHEXPLOSIVE = REGISTRATE.item("round_medium_highexplosive", Item::new)
             .lang("Medium Round (HE)")
             .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
             .register();
    public static final RegistryEntry<Item> ROUND_MEDIUM_INCENDIARY = REGISTRATE.item("round_medium_incendiary", Item::new)
             .lang("Medium Round (Incendiary)")
             .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
             .register();
    public static final RegistryEntry<Item> ROUND_SMALL = REGISTRATE.item("round_small", Item::new)
            .lang("Small Round (Basic)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_SMALL_ARMORPIERCING = REGISTRATE.item("round_small_armorpiercing", Item::new)
            .lang("Small Round (AP)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_SMALL_HIGHEXPLOSIVE = REGISTRATE.item("round_small_highexplosive", Item::new)
            .lang("Small Round (HE)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_SMALL_HOLLOWPOINT = REGISTRATE.item("round_small_hollowpoint", Item::new)
            .lang("Small Round (Hollow-Point)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();
    public static final RegistryEntry<Item> ROUND_SMALL_INCENDIARY = REGISTRATE.item("round_small_incendiary", Item::new)
            .lang("Small Round (Incendiary)")
            .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
            .register();

    //TODO: Organize items in creative tab
    public static void register() {}

    public static final Map<ShellScale, RegistryEntry<ScaleItem>> gunBarrelItems = new HashMap<>();

    private static RegistryEntry<ScaleItem> gunBarrel(ShellScale scale) {
        return REGISTRATE.item(scale.name().toLowerCase() + "_gun_barrel", properties -> new ScaleItem(properties, scale))
                .model(NonNullBiConsumer.noop())
                .register();
    }

    public static final Map<ShellScale, RegistryEntry<BulletItem>> bulletItems = new HashMap<>();

    private static RegistryEntry<BulletItem> bulletItem(ShellScale scale) {
        return REGISTRATE.item("bullet_" + scale.name().toLowerCase(), properties -> new BulletItem(properties, scale))
                .lang(scale.name().toLowerCase().substring(0, 1).toUpperCase() + scale.name().toLowerCase().substring(1) + " Bullet")
                .tab(() -> ModCreativeModTab.CREATE_ARSENAL_TAB_BULLETS)
                .model(NonNullBiConsumer.noop())
                .register();
    }

    static {
        for (ShellScale scale : ShellScale.values()) {
            ModItems.gunBarrelItems.put(scale, gunBarrel(scale));
            ModItems.bulletItems.put(scale, bulletItem(scale));
        }
    }
}