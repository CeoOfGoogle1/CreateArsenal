package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BigRadarBaseBlockTileEntity extends KineticBlockEntity {

    private static final int SHAFT_HEIGHT=1;
    public BigRadarBaseBlockTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public boolean wrenchClick() {
        if(validMultiBlock()) {
            updateDish();
            return true;
        }
        return false;
    }

    private void updateDish() {
        if(level.getBlockEntity(getBlockPos().above(SHAFT_HEIGHT+1)) instanceof BigRadarDishBlockTileEntity dish)
            dish.update();
    }

    private boolean validMultiBlock() {
        if(level.getBlockEntity(getBlockPos().above(SHAFT_HEIGHT+1)) instanceof BigRadarDishBlockTileEntity dish)
            return level.getBlockState(getBlockPos().above()).is(AllBlocks.SHAFT.get())&&dish.isValid();
        return false;
    }

}
