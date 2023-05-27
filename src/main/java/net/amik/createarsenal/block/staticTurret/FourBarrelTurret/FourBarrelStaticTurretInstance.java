package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import net.amik.createarsenal.ModBlockPartials;
import net.minecraft.core.Direction;

import static net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurret.FACING;

public class FourBarrelStaticTurretInstance extends KineticTileInstance<FourBarrelStaticTurretTileEntity> implements DynamicInstance {

    private final RotatingData BARREL;
    private final FourBarrelStaticTurretTileEntity turret;
    private final Direction dir;
    float offsett=1.5f;


    public FourBarrelStaticTurretInstance(MaterialManager dispatcher, FourBarrelStaticTurretTileEntity tile) {
        super(dispatcher, tile);
        turret=tile;
        dir=turret.getBlockState().getValue(FACING);
        BARREL = getRotatingMaterial().getModel(ModBlockPartials.FOUR_BARREL,blockState,dir).createInstance();
        BARREL.setRotationAxis(dir.getAxis());
    }


    private void transformTurret()
    {
        BARREL.setPosition(getInstancePosition());
        switch (dir)
        {
            case NORTH ->BARREL.nudge(0,0,offsett);
            case SOUTH ->BARREL.nudge(0,0,-offsett);
            case EAST ->BARREL.nudge(-offsett,0,0);
            case WEST ->BARREL.nudge(offsett,0,0);
        }
        BARREL.setRotationalSpeed(turret.getSpeed());

    }

    @Override
    public void beginFrame() {
     transformTurret();
    }


    @Override
    public void updateLight() {
        relight(pos, BARREL);
    }


    @Override
    public void remove() {
        BARREL.delete();
    }
}
