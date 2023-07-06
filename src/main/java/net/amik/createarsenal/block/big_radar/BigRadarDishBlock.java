package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BigRadarDishBlock extends HorizontalDirectionalKineticBlock implements IBE<BigRadarDishBlockTileEntity> {

    public BigRadarDishBlock(Properties properties) {
        super(properties);
    }

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }


    @Override
    public Class<BigRadarDishBlockTileEntity> getBlockEntityClass() {
        return BigRadarDishBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends BigRadarDishBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.BIG_RADAR_DISH_BLOCK_TILE_ENTITY.get();
    }
}

