package net.amik.createarsenal.block.staticTurret.modularGun;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class GunBarrelBlockRenderer extends SmartBlockEntityRenderer<GunBarrelBlockEntity> {
    public GunBarrelBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(GunBarrelBlockEntity barrel, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(barrel, partialTicks, ms, buffer, light, overlay);
        if(barrel.getBarrelCount()==0||barrel.getPartialModel()==null) return;

        BlockState blockState = barrel.getBlockState();
        Direction direction = barrel.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        Direction shift=direction.getCounterClockWise();

        double modifier=4;


        SuperByteBuffer barrelModel=CachedBufferer
                .partialFacing(barrel.getPartialModel(), blockState, direction);


        if(barrel.isPrimary()) {
            if (barrel.barrelCount == 1)
                barrelModel.light(light).renderInto(ms, vb);


            if (barrel.barrelCount == 2) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0, shift.getStepZ() * 0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
            }


            if (barrel.barrelCount == 3) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(0, .05 * modifier, 0);
                barrelModel.light(light).renderInto(ms, vb);
            }


            if (barrel.barrelCount >= 4) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
            }
        }
        else {
            int primaryBarrelCount=barrel.getPrimaryBarrelCount();

            if (primaryBarrelCount == 1&&barrel.barrelCount==1)
                barrelModel.light(light).renderInto(ms, vb);


            if (primaryBarrelCount == 2) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if(barrel.barrelCount==2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
            }


            if (primaryBarrelCount == 3) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if(barrel.barrelCount>=2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if(barrel.barrelCount==3) {
                    barrelModel.translate(0, .05 * modifier, 0);
                    barrelModel.light(light).renderInto(ms, vb);
                }
            }


            if (primaryBarrelCount >= 4) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if(barrel.barrelCount>=2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if(barrel.barrelCount>=3) {
                    barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if(barrel.barrelCount>=4) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
            }

        }

    }

}
