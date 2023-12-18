package net.amik.createarsenal.block.monitor;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;


public class MonitorBlock extends HorizontalDirectionalBlock implements IBE<MonitorBlockEntity> {
    public MonitorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(SHAPE,Shape.SINGLE));
    }

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape",Shape.class );


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection()
                        .getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    @ParametersAreNonnullByDefault
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.getItemInHand(pHand).is(AllItems.WRENCH.get())&&pLevel.getBlockEntity(pPos) instanceof MonitorBlockEntity monitor)
            if(monitor.wrenchClick(pHit))
                return InteractionResult.SUCCESS;

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public enum Shape implements StringRepresentable {
        SINGLE, DOUBLE, TRIPLE, GHOST;

        @Override
        public @NotNull String getSerializedName() {
            return Lang.asId(name());
        }
    }

    @Override
    public Class<MonitorBlockEntity> getBlockEntityClass() {
        return MonitorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MonitorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.MONITOR.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(SHAPE);
        super.createBlockStateDefinition(builder);
    }
}
