package net.amik.createarsenal.block.staticTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

public abstract class AbstractRotatingTurretInstance<T extends KineticBlockEntity> extends AbstractTurretInstance<T> {
    protected AbstractRotatingTurretInstance(MaterialManager dispatcher, T tile) {
        super(dispatcher, tile);
    }

    @Override
    protected void transformTurret() {
        super.transformTurret();
        BARREL.setRotationalSpeed(blockEntity.getSpeed() * 0.5f);
    }
}
