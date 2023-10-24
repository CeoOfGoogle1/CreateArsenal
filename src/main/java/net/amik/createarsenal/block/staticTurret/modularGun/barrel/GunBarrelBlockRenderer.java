package net.amik.createarsenal.block.staticTurret.modularGun.barrel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class GunBarrelBlockRenderer extends SmartBlockEntityRenderer<GunBarrelBlockEntity> {
    public GunBarrelBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(GunBarrelBlockEntity barrel, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(barrel, partialTicks, ms, buffer, light, overlay);
        if (barrel.getBarrelCount() == 0 || barrel.getGunBE() == null || barrel.getSize().equals(ShellScale.NONE))
            return;

        BlockState blockState = barrel.getBlockState();
        Direction direction = barrel.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        Direction shift = direction.getCounterClockWise();

        double modifier = 4;


        if (barrel.isSpinning())
            renderSpinningBarrels(blockState, barrel, direction, light, ms, vb);
        else
            renderNormalBarrels(blockState, barrel, shift, direction, modifier, light, ms, vb);

    }

    private void renderSpinningBarrels(BlockState blockState, GunBarrelBlockEntity barrel, Direction direction, int light, PoseStack ms, VertexConsumer vb) {
        SuperByteBuffer barrelModel = CachedBufferer
                .partialFacing(barrel.getPartialRotatingModel(), blockState, direction);
        float speed = barrel.getGunBE().getSpeed();
        float time = AnimationTickHolder.getRenderTime(barrel.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * Mth.PI;
        if (barrel.barrelCount == 1 && barrel.getPrimaryBarrelCount() == 1) {
            SuperByteBuffer Model = CachedBufferer
                    .partialFacing(barrel.getPartialModel(), blockState, direction);
            Model.rotateCentered(direction, angle);
            Model.light(light).renderInto(ms, vb);
            return;
        }
        for (int i = 0; i < barrel.barrelCount; i++) {
            barrelModel.translate(direction.getStepX(), 0, direction.getStepZ());
            barrelModel.rotateCentered(direction, angle + (Mth.PI * 2 * i / barrel.getPrimaryBarrelCount()));
            barrelModel.light(light).renderInto(ms, vb);
        }

    }


    private void renderNormalBarrels(BlockState blockState, GunBarrelBlockEntity barrel, Direction shift, Direction direction, double modifier, int light, PoseStack ms, VertexConsumer vb) {
        SuperByteBuffer barrelModel = CachedBufferer
                .partialFacing(barrel.getPartialModel(), blockState, direction);

        if (barrel.isPrimary()) {
            if (barrel.barrelCount == 1) {
                barrelModel.light(light).renderInto(ms, vb);
            }

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
        } else {
            int primaryBarrelCount = barrel.getPrimaryBarrelCount();

            if (primaryBarrelCount == 1 && barrel.barrelCount == 1)
                barrelModel.light(light).renderInto(ms, vb);


            if (primaryBarrelCount == 2) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if (barrel.barrelCount == 2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
            }


            if (primaryBarrelCount == 3) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if (barrel.barrelCount >= 2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if (barrel.barrelCount == 3) {
                    barrelModel.translate(0, .05 * modifier, 0);
                    barrelModel.light(light).renderInto(ms, vb);
                }
            }

            if (primaryBarrelCount >= 4) {
                barrelModel.translate(shift.getStepX() * -0.05 * modifier, -0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                barrelModel.light(light).renderInto(ms, vb);
                if (barrel.barrelCount >= 2) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, -0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if (barrel.barrelCount >= 3) {
                    barrelModel.translate(shift.getStepX() * -0.05 * modifier, 0.05 * modifier, shift.getStepZ() * -0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }
                if (barrel.barrelCount >= 4) {
                    barrelModel.translate(shift.getStepX() * 0.05 * modifier, 0.05 * modifier, shift.getStepZ() * 0.05 * modifier);
                    barrelModel.light(light).renderInto(ms, vb);
                }

            }
        }
    }

}
