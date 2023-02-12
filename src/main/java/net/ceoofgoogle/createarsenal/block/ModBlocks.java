package net.ceoofgoogle.createarsenal.block;

import net.ceoofgoogle.createarsenal.CreateArsenal;
import net.ceoofgoogle.createarsenal.block.custom.TurretBaseBlock;
import net.ceoofgoogle.createarsenal.item.ModCreativeModTab;
import net.ceoofgoogle.createarsenal.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateArsenal.MOD_ID);

   public static final RegistryObject<Block> TURRET_BASE_BLOCK = registerBlock("turret_base_block",
            () -> new TurretBaseBlock(BlockBehaviour.Properties.of(Material.METAL).strength(5f)), ModCreativeModTab.CREATE_ARSENAL_TAB_GENERAL);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
