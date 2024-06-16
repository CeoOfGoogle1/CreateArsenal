package net.amik.createarsenal.block.dispenser;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static net.amik.createarsenal.util.HorizontalDirectionBlock.FACING;


public class ProjectileDispenserRenderer extends KineticBlockEntityRenderer<ProjectileDispenserBlockEntity> {

    public ProjectileDispenserRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(ProjectileDispenserBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        BlockState blockState = be.getBlockState();
        Direction direction = be.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

        // half-shaft
        SuperByteBuffer shaft = CachedBufferer
                .block(KineticBlockEntityRenderer.shaft(KineticBlockEntityRenderer.getRotationAxisOf(be)));
        standardKineticRotationTransform(shaft, be, light).renderInto(ms, vb);

        int angle = be.angle;

        // Dispenser
        SuperByteBuffer dispenser = CachedBufferer
                .block(blockState);

        dispenser.rotateCentered(direction.getClockWise(), (float) ((double) Math.toRadians((angle - 90.0))));

        dispenser.light(light).renderInto(ms, vb);
    }
}
