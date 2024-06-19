package net.amik.createarsenal.block.aerialBombs;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

public class AerialBombBlock extends HorizontalDirectionalBlock implements IBE<AerialBombBlockEntity>, ProperWaterloggedBlock {



    public AerialBombBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
        registerDefaultState(super.defaultBlockState().setValue(FACING, Direction.NORTH));
    }


    @Override
    public FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return withWater(super.getStateForPlacement(pContext), pContext);
    }


    @Override
    public Class<AerialBombBlockEntity> getBlockEntityClass() {
        return AerialBombBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AerialBombBlockEntity> getBlockEntityType() {
        return ModBlockEntities.AERIAL_BOMB.get();
    }
}
