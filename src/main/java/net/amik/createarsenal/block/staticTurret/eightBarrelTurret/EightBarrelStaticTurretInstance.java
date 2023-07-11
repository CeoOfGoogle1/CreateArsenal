package net.amik.createarsenal.block.staticTurret.eightBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.PartialModel;
import net.amik.createarsenal.block.staticTurret.AbstractRotatingTurretInstance;
import net.amik.createarsenal.registrate.ModPartials;

public class EightBarrelStaticTurretInstance extends AbstractRotatingTurretInstance<EightBarrelStaticTurretBlockEntity> implements DynamicInstance {
    public EightBarrelStaticTurretInstance(MaterialManager dispatcher, EightBarrelStaticTurretBlockEntity tile) {
        super(dispatcher, tile);
    }

    @Override
    protected PartialModel getPartialModel() {
        return ModPartials.EIGHT_BARREL;
    }

    @Override
    public float getOffset() {
        return 1.7f;
    }
}
