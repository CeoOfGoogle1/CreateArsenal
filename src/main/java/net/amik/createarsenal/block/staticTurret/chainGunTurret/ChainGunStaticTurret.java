package net.amik.createarsenal.block.staticTurret.chainGunTurret;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.AbstractTurretBlock;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ChainGunStaticTurret extends AbstractTurretBlock implements IBE<ChainGunStaticTurretBlockEntity> {
    public ChainGunStaticTurret(Properties properties) {
        super(properties);
    }

    @Override
    public Class<ChainGunStaticTurretBlockEntity> getBlockEntityClass() {
        return ChainGunStaticTurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ChainGunStaticTurretBlockEntity> getBlockEntityType() {
        return ModBlockEntities.CHAINGUN_STATIC_TURRET_TILE_ENTITY.get();
    }
}
