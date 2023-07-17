package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;
import static net.amik.createarsenal.block.big_radar.BigRadarDishBlock.MULTIBLOCK;

public class BigRadarDishBlockTileEntity extends KineticBlockEntity {

    private static final int DISH_HEIGHT=2;
    private static final int DISH_WIDTH=3; //must be odd# for rod to be centered

    public BigRadarDishBlockTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public boolean isValid() {
        for (int i = 0; i < DISH_HEIGHT; i++) {
            for (int j = 1; j < DISH_WIDTH/2+1; j++) {
                BlockPos rightPos = getBlockPos().above(i).relative(getBlockState().getValue(FACING).getCounterClockWise(),j);
                BlockPos leftPos = getBlockPos().above(i).relative(getBlockState().getValue(FACING).getClockWise(),j);
                BlockPos centerPos = getBlockPos().above(i);

                if(!isBlock(ModBlocks.BIG_RADAR_DISH_BLOCK.get(),rightPos,leftPos,centerPos))
                    return false;
            }
        }
        return isBlock(ModBlocks.BIG_RADAR_RECEIVER_BLOCK.get(),getBlockPos().relative(getBlockState().getValue(FACING).getOpposite()));
    }

    boolean isBlock(Block block,BlockPos... positions){
        for (BlockPos pos:positions) {
            if(!level.getBlockState(pos).is(block))
                return false;
        }
        return true;
    }


    public boolean isMultiBlock(){
        return getBlockState().getValue(MULTIBLOCK);
    }

    public void update() {
        boolean update=!isMultiBlock();
        for (int i = 0; i < DISH_HEIGHT; i++) {
            for (int j = 1; j < DISH_WIDTH/2+1; j++) {
                BlockPos rightPos = getBlockPos().above(i).relative(getBlockState().getValue(FACING).getCounterClockWise(),j);
                BlockPos leftPos = getBlockPos().above(i).relative(getBlockState().getValue(FACING).getClockWise(),j);
                BlockPos centerPos = getBlockPos().above(i);

                level.setBlockAndUpdate(rightPos,getBlockState().setValue(MULTIBLOCK,update));
                level.setBlockAndUpdate(leftPos,getBlockState().setValue(MULTIBLOCK,update).setValue(MULTIBLOCK,update));
                level.setBlockAndUpdate(centerPos,getBlockState().setValue(MULTIBLOCK,update).setValue(MULTIBLOCK,update));


            }
        }
        level.setBlockAndUpdate(getBlockPos().relative(getBlockState().getValue(FACING).getOpposite()),level.getBlockState(getBlockPos().relative(getBlockState().getValue(FACING).getOpposite())).setValue(MULTIBLOCK,update));

    }
}
