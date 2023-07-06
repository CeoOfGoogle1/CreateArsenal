package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.simibubi.create.foundation.block.IBE;
import net.amik.createarsenal.registrate.ModBlockEntities;
import net.amik.createarsenal.util.HorizontalDirectionalKineticBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FourBarrelStaticTurret extends HorizontalDirectionalKineticBlock implements IBE<FourBarrelStaticTurretTileEntity> {
    public FourBarrelStaticTurret(Properties properties) {
        super(properties);
    }

    @Override
    public Class<FourBarrelStaticTurretTileEntity> getBlockEntityClass() {
        return FourBarrelStaticTurretTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends FourBarrelStaticTurretTileEntity> getBlockEntityType() {
        return ModBlockEntities.FOUR_BARREL_STATIC_TURRET_TILE_ENTITY.get();
    }
}
