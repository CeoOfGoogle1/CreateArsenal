package net.amik.createarsenal.block.staticTurret.fourBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretInstance;
import net.amik.createarsenal.registrate.ModPartials;

public class FourBarrelStaticTurretInstance extends AbstractRotatingTurretInstance<FourBarrelStaticTurretBlockEntity> {
    public FourBarrelStaticTurretInstance(MaterialManager dispatcher, FourBarrelStaticTurretBlockEntity tile) {
        super(dispatcher, tile);
    }

    @Override
    protected PartialModel getPartialModel() {
        return ModPartials.FOUR_BARREL;
    }

    @Override
    public float getOffset() {
        return 1.5f;
    }
}
