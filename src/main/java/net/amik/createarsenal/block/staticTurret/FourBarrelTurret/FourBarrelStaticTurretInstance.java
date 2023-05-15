package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import net.amik.createarsenal.ModBlockPartials;

public class FourBarrelStaticTurretInstance extends KineticTileInstance<FourBarrelStaticTurretTileEntity> {

    private final RotatingData BARREL;
    public FourBarrelStaticTurretInstance(MaterialManager dispatcher, FourBarrelStaticTurretTileEntity tile) {
        super(dispatcher, tile);
        BARREL = getRotatingMaterial().getModel(ModBlockPartials.FOUR_BARREL,blockState).createInstance();
        BARREL.setPosition(getInstancePosition());
        BARREL.nudge(0,0,-1.2f);
    }


    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, BARREL);
    }

    @Override
    protected void remove() {
        BARREL.delete();
    }
}
