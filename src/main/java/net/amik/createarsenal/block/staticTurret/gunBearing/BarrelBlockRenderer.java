package net.amik.createarsenal.block.staticTurret.gunBearing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class BarrelBlockRenderer extends SmartBlockEntityRenderer<BarrelBlockEntity> {
    public BarrelBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BarrelBlockEntity barrel, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(barrel, partialTicks, ms, buffer, light, overlay);
        if(barrel.getBarrelCount()==0||barrel.getPartialModel()==null) return;

        BlockState blockState = barrel.getBlockState();
        Direction direction = barrel.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        Direction shift=direction.getCounterClockWise();


        SuperByteBuffer barrelModel=CachedBufferer
                .partialFacing(barrel.getPartialModel(), blockState, direction);


        if(barrel.barrelCount==1)
            barrelModel.light(light).renderInto(ms, vb);


        if(barrel.barrelCount==2) {
            barrelModel.translate(shift.getStepX() * -0.1, 0, shift.getStepZ() * -0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(shift.getStepX() * 0.1, 0, shift.getStepZ() * 0.1);
            barrelModel.light(light).renderInto(ms, vb);
        }


        if(barrel.barrelCount==3) {
            barrelModel.translate(shift.getStepX() * -0.1, -0.1, shift.getStepZ() * -0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(shift.getStepX() * 0.1, -0.1, shift.getStepZ() * 0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(0, .1, 0);
            barrelModel.light(light).renderInto(ms, vb);
        }


        if (barrel.barrelCount >= 4) {
            barrelModel.translate(shift.getStepX() * -0.1, -0.1, shift.getStepZ() * -0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(shift.getStepX() * 0.1, -0.1, shift.getStepZ() * 0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(shift.getStepX() * -0.1, 0.1, shift.getStepZ() * -0.1);
            barrelModel.light(light).renderInto(ms, vb);
            barrelModel.translate(shift.getStepX() * 0.1, 0.1, shift.getStepZ() * 0.1);
            barrelModel.light(light).renderInto(ms, vb);
        }


    }

}
