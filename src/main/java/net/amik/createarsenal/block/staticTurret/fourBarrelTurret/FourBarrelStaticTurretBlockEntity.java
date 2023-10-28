package net.amik.createarsenal.block.staticTurret.fourBarrelTurret;

import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretTileEntity;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class FourBarrelStaticTurretBlockEntity extends AbstractRotatingTurretTileEntity {
    public FourBarrelStaticTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected SoundEvent fireSoundName() {
        return ModSoundEvents.FIRE_CHAINGUN_TURRET.getMainEvent();
    }

    @Override
    protected float shootingInterval() {
        return 2;
    }

    @Override
    protected float getBarrelLength() {
        return 2f;
    }
}
