package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class BigRadarDishBlock extends HorizontalDirectionalKineticBlock implements IBE<BigRadarDishBlockTileEntity> {

    public BigRadarDishBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MULTIBLOCK, false));
    }

    public Direction getPreferredFacing(BlockPlaceContext context) {
        return super.getPreferredFacing(context).getOpposite();
    }
    protected static final VoxelShape SHAPE_X = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);

    public static BooleanProperty MULTIBLOCK = BooleanProperty.create("multiblock");


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if(pState.getValue(FACING).getAxis().equals(Direction.Axis.X))
            return SHAPE_X;
        return SHAPE_Z;
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

    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MULTIBLOCK);
        super.createBlockStateDefinition(builder);
    }
}

