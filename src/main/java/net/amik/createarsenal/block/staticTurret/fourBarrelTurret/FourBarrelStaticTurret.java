package net.amik.createarsenal.block.staticTurret.fourBarrelTurret;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.AbstractTurretBlock;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FourBarrelStaticTurret extends AbstractTurretBlock implements IBE<FourBarrelStaticTurretBlockEntity> {
    public FourBarrelStaticTurret(Properties properties) {
        super(properties);
    }

    @Override
    public Class<FourBarrelStaticTurretBlockEntity> getBlockEntityClass() {
        return FourBarrelStaticTurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FourBarrelStaticTurretBlockEntity> getBlockEntityType() {
        return ModBlockEntities.FOUR_BARREL_STATIC_TURRET_TILE_ENTITY.get();
    }
}
