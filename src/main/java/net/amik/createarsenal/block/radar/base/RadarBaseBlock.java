package net.amik.createarsenal.block.radar.base;

import com.simibubi.create.content.contraptions.bearing.BearingBlock;
import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class RadarBaseBlock extends BearingBlock implements IBE<RadarBaseBlockTileEntity> {

    public RadarBaseBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn,
                                          @NotNull BlockHitResult hit) {
        if (!player.mayBuild())
            return InteractionResult.FAIL;
        if (player.isShiftKeyDown())
            return InteractionResult.FAIL;
        if (player.getItemInHand(handIn)
                .isEmpty()) {
            if (worldIn.isClientSide)
                return InteractionResult.SUCCESS;
            withBlockEntityDo(worldIn, pos, be -> {
                if (be.isRunning()) {
                    be.disassemble();
                    return;
                }
                be.assembleNextTick();
            });
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public Class<RadarBaseBlockTileEntity> getBlockEntityClass() {
        return RadarBaseBlockTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends RadarBaseBlockTileEntity> getBlockEntityType() {
        return ModBlockEntities.RADAR_BASE_BLOCK_TILE_ENTITY.get();
    }
}

