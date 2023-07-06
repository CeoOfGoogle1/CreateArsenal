package net.amik.createarsenal.block.staticTurret.EightBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.core.Direction;

import static net.amik.createarsenal.block.staticTurret.EightBarrelTurret.EightBarrelStaticTurret.FACING;

public class EightBarrelStaticTurretInstance extends ShaftInstance<EightBarrelStaticTurretTileEntity> implements DynamicInstance {

    private final RotatingData BARREL;
    private final EightBarrelStaticTurretTileEntity turret;
    private final Direction facing;
    float offset = 1.7f;


    public EightBarrelStaticTurretInstance(MaterialManager dispatcher, EightBarrelStaticTurretTileEntity tile) {
        super(dispatcher, tile);
        turret = tile;
        facing = turret.getBlockState().getValue(FACING);
        BARREL = getRotatingMaterial().getModel(ModPartials.EIGHT_BARREL,blockState,facing).createInstance();
        BARREL.setRotationAxis(facing.getAxis());
    }


    private void transformTurret()
    {
        BARREL.setPosition(getInstancePosition());
        switch (facing)
        {
            case NORTH ->BARREL.nudge(0,0,offset);
            case SOUTH ->BARREL.nudge(0,0,-offset);
            case EAST ->BARREL.nudge(-offset,0,0);
            case WEST ->BARREL.nudge(offset,0,0);
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
        super.updateLight();
    }


    @Override
    public void remove() {
        BARREL.delete();
        super.remove();
    }

}
