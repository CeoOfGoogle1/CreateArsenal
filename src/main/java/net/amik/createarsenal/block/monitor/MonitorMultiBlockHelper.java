package net.amik.createarsenal.block.monitor;

import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicBoolean;

import static net.amik.createarsenal.block.monitor.MonitorBlock.SHAPE;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MonitorMultiBlockHelper {

    public static int MAX_SIZE = 5;

    public static void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pState.getValue(SHAPE) != MonitorBlock.Shape.SINGLE)
            return;
        int size = getSize(pLevel, pPos);
        if (size > 1)
            formMulti(pState, pLevel, pPos, size);
    }


    public static void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
    }

    public static void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
    }

    private static void formMulti(BlockState pState, Level pLevel, BlockPos pPos, int size) {
        MonitorBlock.Shape shape;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j == 0) shape = MonitorBlock.Shape.LOWER_RIGHT;
                else if (i == 0 && j == size - 1) shape = MonitorBlock.Shape.LOWER_LEFT;
                else if (i == size - 1 && j == 0) shape = MonitorBlock.Shape.UPPER_RIGHT;
                else if (i == size - 1 && j == size - 1) shape = MonitorBlock.Shape.UPPER_LEFT;
                else if (i == 0) shape = MonitorBlock.Shape.LOWER_CENTER;
                else if (i == size - 1) shape = MonitorBlock.Shape.UPPER_CENTER;
                else if (j == 0) shape = MonitorBlock.Shape.MIDDLE_RIGHT;
                else if (j == size - 1) shape = MonitorBlock.Shape.MIDDLE_LEFT;
                else shape = MonitorBlock.Shape.CENTER;

                Direction facing = pLevel.getBlockState(pPos).getValue(FACING);
                pLevel.setBlockAndUpdate(pPos.above(i).relative(facing.getClockWise(), j), pState.setValue(SHAPE, shape));
                if (pLevel.getBlockEntity(pPos.above(i).relative(facing.getClockWise(), j)) instanceof MonitorBlockEntity monitor) {
                    monitor.setControllerPos(pPos, size);
                }
            }
        }
    }

    public static int getSize(Level pLevel, BlockPos pPos) {
        if (!pLevel.getBlockState(pPos).is(ModBlocks.MONITOR.get()))
            return 0;
        Direction facing = pLevel.getBlockState(pPos).getValue(FACING);
        int potentialsize = 0;
        for (int i = 0; i < MAX_SIZE; i++) {
            AtomicBoolean valid = new AtomicBoolean(true);
            BlockPos.betweenClosed(pPos, pPos.above(i).relative(facing.getClockWise(), i)).forEach(p -> {
                if (!pLevel.getBlockState(p).is(ModBlocks.MONITOR.get()))
                    valid.set(false);
            });
            if (valid.get())
                potentialsize = i + 1;
            else
                break;
        }
        if (potentialsize == 1)
            return 1;

        for (int i = 0; i < potentialsize; i++) {
            for (int j = 0; j < potentialsize; j++) {
                BlockEntity be = pLevel.getBlockEntity(pPos.above(i).relative(facing.getClockWise(), j));
                if (!(be instanceof MonitorBlockEntity monitor && monitor.getSize() < potentialsize))
                    return Math.min(i, j);

            }
        }

        return potentialsize;

    }

    public static boolean isMulti(Level pLevel, BlockPos pos) {
        if (!pLevel.getBlockState(pos).is(ModBlocks.MONITOR.get()))
            return false;
        return getSize(pLevel, pos) > 1;
    }




}
