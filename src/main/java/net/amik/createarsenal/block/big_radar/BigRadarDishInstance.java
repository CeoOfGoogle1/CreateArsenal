package net.amik.createarsenal.block.big_radar;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.FlatLit;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class BigRadarDishInstance extends KineticBlockEntityInstance<BigRadarDishBlockTileEntity> implements DynamicInstance {

    protected RotatingData DISH;

    public BigRadarDishInstance(MaterialManager materialManager, BigRadarDishBlockTileEntity blockEntity) {
        super(materialManager, blockEntity);
        DISH = getRotatingMaterial().getModel(ModPartials.BIG_DISH, blockState, getFacing()).createInstance();
        DISH.setRotationAxis(Direction.Axis.Y);
    }

    @Override
    public void beginFrame() {
        if(blockEntity.isMultiBlock()&&blockEntity.isValid()) {
            DISH.setPosition(getInstancePosition());
            DISH.setRotationalSpeed(blockEntity.getSpeed());
        }
    }
    protected Direction getFacing() {
        return blockEntity.getBlockState().getValue(DirectionalKineticBlock.FACING);
    }

    @Override
    public void updateLight() {
            relight(pos, DISH);
    }

    @Override
    protected void remove() {
            DISH.delete();
    }
}
