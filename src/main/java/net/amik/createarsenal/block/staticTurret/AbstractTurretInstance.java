package net.amik.createarsenal.block.staticTurret;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import net.minecraft.core.Direction;

public abstract class AbstractTurretInstance<T extends KineticBlockEntity> extends HalfShaftInstance<T> implements DynamicInstance {
    protected final RotatingData BARREL;
    protected AbstractTurretInstance(MaterialManager dispatcher, T tile) {
        super(dispatcher, tile);
        BARREL = getRotatingMaterial().getModel(getPartialModel(), blockState, getFacing()).createInstance();
        BARREL.setRotationAxis(getFacing().getAxis());
    }

    protected abstract PartialModel getPartialModel();

    protected abstract float getOffset();

    protected Direction getFacing() {
        return blockEntity.getBlockState().getValue(DirectionalKineticBlock.FACING);
    }

    protected void transformTurret() {
        BARREL.setPosition(getInstancePosition());
        switch (getFacing())
        {
            case NORTH ->BARREL.nudge(0,0,getOffset());
            case SOUTH ->BARREL.nudge(0,0,-getOffset());
            case EAST ->BARREL.nudge(-getOffset(),0,0);
            case WEST ->BARREL.nudge(getOffset(),0,0);
        }
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
