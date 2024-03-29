package net.amik.createarsenal.block.staticTurret.eightBarrelTurret;

import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretTileEntity;
import net.amik.createarsenal.registrate.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class EightBarrelStaticTurretBlockEntity extends AbstractRotatingTurretTileEntity {
    public EightBarrelStaticTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected SoundEvent fireSoundName() {
        return ModSoundEvents.FIRE_CHAINGUN_TURRET.getMainEvent();
    }

    @Override
    protected float shootingInterval() {
        return 1;
    }

    @Override
    protected float getBarrelLength() {
        return 2.3f;
    }
}
