package net.amik.createarsenal.block.staticTurret.FourBarrelTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.core.Direction;

import static net.amik.createarsenal.block.staticTurret.FourBarrelTurret.FourBarrelStaticTurret.FACING;

public class FourBarrelStaticTurretInstance extends HalfShaftInstance<FourBarrelStaticTurretTileEntity> implements DynamicInstance {

    private final RotatingData BARREL;
    private final FourBarrelStaticTurretTileEntity turret;
    private final Direction facing;
    float offset = 1.5f;


    public FourBarrelStaticTurretInstance(MaterialManager dispatcher, FourBarrelStaticTurretTileEntity tile) {
        super(dispatcher, tile);
        turret = tile;
        facing = turret.getBlockState().getValue(FACING);
        BARREL = getRotatingMaterial().getModel(ModPartials.FOUR_BARREL,blockState,facing).createInstance();
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
        BARREL.setRotationalSpeed(turret.getSpeed() * 0.5f);

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
