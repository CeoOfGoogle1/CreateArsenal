package net.amik.createarsenal.block.staticTurret.chainGunTurret;

import net.amik.createarsenal.block.staticTurret.AbstractTurretTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChainGunStaticTurretBlockEntity extends AbstractTurretTileEntity {
    public ChainGunStaticTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
    protected int tickUntilRecoil = 0;

    @Override
    protected void shoot() {
        tickUntilRecoil = 5;

        super.shoot();
    }

    @Override
    protected float shootingInterval() {
        return 3;
    }

    @Override
    protected float getBarrelLength() {
        return 3;
    }
}
