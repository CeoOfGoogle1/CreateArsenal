package net.amik.createarsenal.block.staticTurret.chainGunTurret;

import net.amik.createarsenal.block.staticTurret.AbstractTurretTileEntity;
import net.amik.createarsenal.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChainGunStaticTurretBlockEntity extends AbstractTurretTileEntity {
    public ChainGunStaticTurretBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    private int tickUntilRecoil = 0;

    @Override
    protected void shoot() {
        tickUntilRecoil = 3;
        super.shoot();
    }

    @Override
    public void tick() {
        tickUntilRecoil--;
        if (tickUntilRecoil <= 0) {
            tickUntilRecoil = 0;
        }
        super.tick();
    }

    @Override
    protected SoundEvent fireSoundName() {
        return ModSounds.CHAIN_GUN_FIRED.get();
    }

    @Override
    protected float shootingInterval() {
        return 3;
    }

    @Override
    protected float getBarrelLength() {
        return 2.5f;
    }

    public int getTickUntilRecoil() {
        return tickUntilRecoil;
    }
}
