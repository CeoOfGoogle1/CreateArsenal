package net.amik.createarsenal.block.staticTurret.ChaingunTurret;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ChaingunStaticTurret extends HorizontalDirectionalKineticBlock implements IBE<ChaingunStaticTurretBlockEntity> {
    public ChaingunStaticTurret(Properties properties) {
        super(properties);
    }

    public Direction getPreferredFacing(BlockPlaceContext context) {
        return super.getPreferredFacing(context) == null || !super.getPreferredFacing(context).getAxis().isHorizontal() ? context.getHorizontalDirection() : super.getPreferredFacing(context).getOpposite();
    }

    @Override
    public Class<ChaingunStaticTurretBlockEntity> getBlockEntityClass() {
        return ChaingunStaticTurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ChaingunStaticTurretBlockEntity> getBlockEntityType() {
        return ModBlockEntities.CHAINGUN_STATIC_TURRET_TILE_ENTITY.get();
    }
}
