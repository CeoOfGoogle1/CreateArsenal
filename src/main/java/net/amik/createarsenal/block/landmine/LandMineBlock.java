package net.amik.createarsenal.block.landmine;

import net.amik.createarsenal.registrate.ModProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LandMineBlock extends Block {

    public static final BooleanProperty BURIED = BooleanProperty.create("buried");
    public static final BooleanProperty ARMED = BooleanProperty.create("armed");

    VoxelShape SHAPE = Block.box(2, 0, 2, 14, 3, 14);
    VoxelShape BURRIED_SHAPE = Block.box(5, 0, 5, 11, 3, 11);

    public LandMineBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(super.defaultBlockState().setValue(BURIED, false));
        registerDefaultState(super.defaultBlockState().setValue(ARMED, false));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BURIED);
        builder.add(ARMED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BURIED) ? BURRIED_SHAPE : SHAPE;
    }



    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, Direction.UP) && !level.getBlockState(blockpos).isAir();
    }


    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (!this.canSurvive(state, level, pos) || level.getBlockState(pos.below()).isAir())
            explode(level, pos);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return this.canSurvive(state, level, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getValue(ARMED) && validEntity(entity)) {
            this.explode(level, pos);
            level.destroyBlock(pos, false);
        }
        super.entityInside(state, level, pos, entity);
    }

    private boolean validEntity(Entity entity) {
        return entity.getType() != ModProjectiles.FALLING_LANDMINE.get() && !(entity instanceof ItemEntity);
    }

    private void explode(Level level, BlockPos pos) {
        if (!level.isClientSide()) {
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2, false, Level.ExplosionInteraction.BLOCK);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2, false, Level.ExplosionInteraction.NONE);
        }

    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ItemTags.SHOVELS) && !state.getValue(BURIED)) {

            level.setBlock(pos, state.setValue(BURIED, true), 3);

            if (!player.isCreative() && player instanceof ServerPlayer playerEntity)
                itemStack.hurt(1, level.random, playerEntity);

            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
