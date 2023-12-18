package net.amik.createarsenal.block.radar.dish;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import net.amik.createarsenal.registrate.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class RadarDishBlock extends WrenchableDirectionalBlock {
    public RadarDishBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return ModShapes.RADAR_DISH.get(pState.getValue(FACING));
    }
}

