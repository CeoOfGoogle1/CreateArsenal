package net.amik.createarsenal.block.staticTurret.eightBarrelTurret;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.block.staticTurret.AbstractTurretBlock;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class EightBarrelStaticTurret extends AbstractTurretBlock implements IBE<EightBarrelStaticTurretBlockEntity> {

    public EightBarrelStaticTurret(Properties properties) {
        super(properties);
    }

    @Override
    public Class<EightBarrelStaticTurretBlockEntity> getBlockEntityClass() {
        return EightBarrelStaticTurretBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends EightBarrelStaticTurretBlockEntity> getBlockEntityType() {
        return ModBlockEntities.EIGHT_BARREL_STATIC_TURRET_TILE_ENTITY.get();
    }
}
