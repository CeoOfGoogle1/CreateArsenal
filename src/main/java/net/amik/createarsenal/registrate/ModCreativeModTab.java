package net.amik.createarsenal.registrate;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.utility.Components;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.amik.createarsenal.CreateArsenal;
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

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = addTab("base", "Create: Arsenal",
            ModItems.bulletItems.get(ShellScale.LARGE)::asStack);


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
