package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static net.amik.createarsenal.block.big_radar.BigRadarDishBlock.MULTIBLOCK;

public class BigRadarReceiverBlock extends HorizontalDirectionalKineticBlock implements IBE<BigRadarReceiverBlockTileEntity> {

    public BigRadarReceiverBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MULTIBLOCK, false));
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    public Direction getPreferredFacing(BlockPlaceContext context) {
        return super.getPreferredFacing(context).getOpposite();
    }
    @Override
    public Class<BigRadarReceiverBlockTileEntity> getBlockEntityClass() {
        return BigRadarReceiverBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends BigRadarReceiverBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.BIG_RADAR_RECEIVER_BLOCK_TILE_ENTITY.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MULTIBLOCK);
        super.createBlockStateDefinition(builder);
    }
}

