package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BigRadarReceiverBlock extends HorizontalDirectionalKineticBlock implements IBE<BigRadarReceiverBlockTileEntity> {

    public BigRadarReceiverBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }


    @Override
    public Class<BigRadarReceiverBlockTileEntity> getBlockEntityClass() {
        return BigRadarReceiverBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends BigRadarReceiverBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.BIG_RADAR_RECEIVER_BLOCK_TILE_ENTITY.get();
    }
}

