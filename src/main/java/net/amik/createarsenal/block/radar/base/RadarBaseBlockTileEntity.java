package net.amik.createarsenal.block.radar.base;

import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RadarBaseBlockTileEntity extends MechanicalBearingBlockEntity {
    public RadarBaseBlockTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void assembleNextTick() {
        this.assembleNextTick = true;
    }
}
