package net.amik.createarsenal.block.radar.plate;

import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import net.amik.createarsenal.registrate.ModShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class RadarPlateBlock extends WrenchableDirectionalBlock {
    public RadarPlateBlock(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return ModShapes.RADAR_PLATE.get(pState.getValue(FACING));
    }
}
