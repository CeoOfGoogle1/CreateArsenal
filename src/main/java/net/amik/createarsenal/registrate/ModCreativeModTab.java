package net.amik.createarsenal.registrate;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.amik.createarsenal.CreateArsenal;
import net.amik.createarsenal.compat.Mods;
import net.amik.createarsenal.shell.ShellScale;
import net.amik.createarsenal.util.IAdditionalCreativeItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.amik.createarsenal.CreateArsenal.REGISTRATE;

public class ModCreativeModTab{
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateArsenal.MOD_ID);

    public static final List<ItemProviderEntry<?>> RADAR_ITEMS = new ArrayList<>();
    public static final List<ItemProviderEntry<?>> KABOOM_ITEMS = new ArrayList<>();
    public static final List<ItemProviderEntry<?>> SKIP = new ArrayList<>();


    static {
        // adds SKIP items
        SKIP.add(ModBlocks.CANNON_CONTROLLER_BLOCK);

        // adds RADAR items
        RADAR_ITEMS.add(ModBlocks.MONITOR);
        RADAR_ITEMS.add(ModBlocks.RADAR_BEARING_BLOCK);
        RADAR_ITEMS.add(ModBlocks.RADAR_RECEIVER_BLOCK);
        RADAR_ITEMS.add(ModBlocks.RADAR_DISH_BLOCK);
        RADAR_ITEMS.add(ModBlocks.RADAR_PLATE_BLOCK);
        if (Mods.CREATEBIGCANNONS.isLoaded())
            RADAR_ITEMS.add(ModBlocks.CANNON_CONTROLLER_BLOCK);

        // adds KABOOM items
        KABOOM_ITEMS.add(ModBlocks.SMALL_BOMB);
        KABOOM_ITEMS.add(ModBlocks.MEDIUM_BOMB);
        KABOOM_ITEMS.add(ModBlocks.BIG_BOMB);
        KABOOM_ITEMS.add(ModBlocks.INCENDIARY_SMALL_BOMB);
        KABOOM_ITEMS.add(ModBlocks.INCENDIARY_MEDIUM_BOMB);
        KABOOM_ITEMS.add(ModBlocks.INCENDIARY_BIG_BOMB);
        KABOOM_ITEMS.add(ModBlocks.ARMOR_PIERCING_SMALL_BOMB);
        KABOOM_ITEMS.add(ModBlocks.ARMOR_PIERCING_MEDIUM_BOMB);
        KABOOM_ITEMS.add(ModBlocks.ARMOR_PIERCING_BIG_BOMB);
        KABOOM_ITEMS.add(ModBlocks.SHRAPNEL_SMALL_BOMB);
        KABOOM_ITEMS.add(ModBlocks.SHRAPNEL_MEDIUM_BOMB);
        KABOOM_ITEMS.add(ModBlocks.SHRAPNEL_BIG_BOMB);
        KABOOM_ITEMS.add(ModBlocks.CLUSTER_SMALL_BOMB);
        KABOOM_ITEMS.add(ModBlocks.CLUSTER_MEDIUM_BOMB);
        KABOOM_ITEMS.add(ModBlocks.CLUSTER_BIG_BOMB);
        KABOOM_ITEMS.add(ModBlocks.CREATIVE_BOMB);
        KABOOM_ITEMS.add(ModItems.PROXIMITY_FUSE);
        KABOOM_ITEMS.add(ModItems.BOMBLET);
        KABOOM_ITEMS.add(ModBlocks.SEA_MINE);
        KABOOM_ITEMS.add(ModBlocks.LANDMINE);
        KABOOM_ITEMS.add(ModBlocks.PROJECTILE_DISPENSER);
        KABOOM_ITEMS.add(ModItems.BOMB_LIGHT_CUTOUT);
        KABOOM_ITEMS.add(ModItems.BOMB_MEDIUM_CUTOUT);
        KABOOM_ITEMS.add(ModItems.BOMB_HEAVY_CUTOUT);

    }

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = addTab("base", "Create: Arsenal",
            ModItems.bulletItems.get(ShellScale.LARGE)::asStack);

    public static final RegistryObject<CreativeModeTab> RADAR_CREATIVE_TAB = addTab("radar", "Create: radar",
            ModBlocks.MONITOR::asStack, RADAR_ITEMS);

    public static final RegistryObject<CreativeModeTab> KABOOM_CREATIVE_TAB = addTab("kaboom", "Create: Kaboom",
            ModBlocks.BIG_BOMB::asStack, KABOOM_ITEMS);

    public static RegistryObject<CreativeModeTab> addTab(String id, String name, Supplier<ItemStack> icon, List<ItemProviderEntry<?>> items) {
        String itemGroupId = "itemGroup." + CreateArsenal.MOD_ID + "." + id;
        REGISTRATE.addRawLang(itemGroupId, name);
        CreativeModeTab.Builder tabBuilder = CreativeModeTab.builder()
                .icon(icon)
                .displayItems((pParameters, pOutput) -> items.forEach(pOutput::accept))
                .title(Components.translatable(itemGroupId))
                .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey());
        return REGISTER.register(id, tabBuilder::build);
    }

    public static RegistryObject<CreativeModeTab> addTab(String id, String name, Supplier<ItemStack> icon) {
        String itemGroupId = "itemGroup." + CreateArsenal.MOD_ID + "." + id;
        REGISTRATE.addRawLang(itemGroupId, name);
        CreativeModeTab.Builder tabBuilder = CreativeModeTab.builder()
                .icon(icon)
                .displayItems(ModCreativeModTab::displayItems)
                .title(Components.translatable(itemGroupId))
                .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey());
        return REGISTER.register(id, tabBuilder::build);
    }


    private static void displayItems(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output pOutput) {
        for (RegistryEntry<Item> item : REGISTRATE.getAll(ForgeRegistries.ITEMS.getRegistryKey())) {
            if (item.get() instanceof SequencedAssemblyItem)
                continue;
            boolean skip = false;
            for (ItemProviderEntry<?> entry : SKIP) {
                if (entry.is(item.get()))
                    skip = true;
            }
            if (skip)
                continue;
            for (ItemProviderEntry<?> entry : RADAR_ITEMS) {
                if (entry.is(item.get()))
                    skip = true;
            }
            if (skip)
                continue;
            for (ItemProviderEntry<?> entry : KABOOM_ITEMS) {
                if (entry.is(item.get()))
                    skip = true;
            }
            if (skip)
                continue;
            if (item.get() instanceof IAdditionalCreativeItems customCreativeItem) {
                List<ItemStack> items = new ArrayList<>();
                customCreativeItem.addCreativeItems(items);
                pOutput.acceptAll(items);
                continue;
            }
            pOutput.accept(item.get());
        }
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }

}
