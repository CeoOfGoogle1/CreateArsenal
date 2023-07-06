package net.amik.createarsenal.util;

import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class CreateUtil {
    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> horizontalDirectionalBlockProvider(
            boolean customItem, int additionalRotation) {
        return (c, p) -> p.getVariantBuilder(c.get())
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(customItem ? AssetLookup.partialBaseModel(c, p) : AssetLookup.standardModel(c, p))
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + additionalRotation) % 360)
                            .build();
                });
    }
}
