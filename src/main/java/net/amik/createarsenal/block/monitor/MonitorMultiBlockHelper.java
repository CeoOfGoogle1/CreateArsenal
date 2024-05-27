package net.amik.createarsenal.block.monitor;

import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static net.amik.createarsenal.block.monitor.MonitorBlock.SHAPE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MonitorMultiBlockHelper {

    public static int MAX_SIZE = 5;

    public static void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (isMulti(pLevel, pPos))
            formMulti(pState, pLevel, pPos);
        if (pState.getValue(SHAPE) == MonitorBlock.Shape.SINGLE || pState.getValue(SHAPE) == MonitorBlock.Shape.LOWER_RIGHT) {
            if (pLevel.getBlockEntity(pPos) instanceof MonitorBlockEntity monitor)
                monitor.setControllerPos(pPos);
        }

    }


    public static void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
    }

    public static void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
    }

    private static void formMulti(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pState.getValue(SHAPE) != MonitorBlock.Shape.SINGLE)
            return;
        int size = getSize(pLevel, pPos);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j == 0)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.LOWER_RIGHT));
                if (i == 0 && j == size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.LOWER_LEFT));
                if (i == size - 1 && j == 0)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.UPPER_RIGHT));
                if (i == size - 1 && j == size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.UPPER_LEFT));
                if (i == 0 && j > 0 && j < size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.LOWER_CENTER));
                if (i == size - 1 && j > 0 && j < size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.UPPER_CENTER));
                if (j == 0 && i > 0 && i < size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.MIDDLE_RIGHT));
                if (j == size - 1 && i > 0 && i < size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.MIDDLE_LEFT));
                if (j > 0 && j < size - 1 && i > 0 && i < size - 1)
                    pLevel.setBlockAndUpdate(pPos.above(i).relative(pLevel.getBlockState(pPos).getValue(FACING).getClockWise(), j), pState.setValue(SHAPE, MonitorBlock.Shape.CENTER));
            }
        }
    }

    public static int getSize(Level pLevel, BlockPos pPos) {
        return Math.min(getHeight(pLevel, pPos), getWidth(pLevel, pPos));
    }

    public static boolean isMulti(Level pLevel, BlockPos pos) {
        if (!pLevel.getBlockState(pos).is(ModBlocks.MONITOR.get()))
            return false;
        return getHeight(pLevel, pos) > 1 && getWidth(pLevel, pos) > 1;

    }

    public static int getHeight(Level pLevel, BlockPos pos) {
        int height = 0;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (pLevel.getBlockState(pos.above(i)).is(ModBlocks.MONITOR.get()))
                height++;
            else
                break;
        }
        return height;
    }

    public static int getWidth(Level pLevel, BlockPos pos) {
        int width = 0;
        if (!pLevel.getBlockState(pos).is(ModBlocks.MONITOR.get()))
            return width;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (pLevel.getBlockState(pos.relative(pLevel.getBlockState(pos).getValue(FACING).getClockWise(), i)).is(ModBlocks.MONITOR.get()))
                width++;
            else
                break;
        }
        return width;
    }


}
