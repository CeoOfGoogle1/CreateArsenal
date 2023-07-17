package net.amik.createarsenal.block.big_radar;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static net.amik.createarsenal.block.big_radar.BigRadarDishBlock.MULTIBLOCK;

public class BigRadarBaseBlock extends HorizontalDirectionalKineticBlock implements IBE<BigRadarBaseBlockTileEntity> {

    public BigRadarBaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return direction.getAxis() == Direction.Axis.Y;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getItemInHand(pHand).is(AllItems.WRENCH.get())&&pLevel.getBlockEntity(pPos) instanceof BigRadarBaseBlockTileEntity base)
            if(base.wrenchClick())
                return InteractionResult.SUCCESS;

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public Class<BigRadarBaseBlockTileEntity> getBlockEntityClass() {
        return BigRadarBaseBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends BigRadarBaseBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.BIG_RADAR_BASE_BLOCK_TILE_ENTITY.get();
    }
}

