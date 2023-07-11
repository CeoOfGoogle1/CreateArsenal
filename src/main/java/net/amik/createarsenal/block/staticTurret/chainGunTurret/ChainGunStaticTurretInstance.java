package net.amik.createarsenal.block.staticTurret.chainGunTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.PartialModel;
import net.amik.createarsenal.block.staticTurret.AbstractTurretInstance;
import net.amik.createarsenal.registrate.ModPartials;

public class ChainGunStaticTurretInstance extends AbstractTurretInstance<ChainGunStaticTurretBlockEntity> {
    public ChainGunStaticTurretInstance(MaterialManager materialManager, ChainGunStaticTurretBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected PartialModel getPartialModel() {
        return ModPartials.CHAIN_GUN_BARREL;
    }

    @Override
    protected float getOffset() {
        if (blockEntity.tickUntilRecoil != 0) {
            blockEntity.tickUntilRecoil--;
        }
        return 2 - blockEntity.tickUntilRecoil * 0.05f;
    }
}
