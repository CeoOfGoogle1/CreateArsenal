package net.amik.createarsenal.block.radar.monitor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.amik.createarsenal.block.radar.base.RadarBaseBlockTileEntity;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MonitorRenderer extends SmartBlockEntityRenderer<MonitorBlockEntity> {
    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    protected void renderSafe(MonitorBlockEntity monitor, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(monitor, partialTicks, ms, buffer, light, overlay);

        if (!monitor.isControllerPos())
            return;

        if (monitor.getRadar().isEmpty())
            return;

        RadarBaseBlockTileEntity radar = monitor.getRadar().get();
        BlockState state = monitor.getBlockState();
        Direction facing = state.getValue(FACING);
        BlockPos referencePos = monitor.getRadarPos();
        double radarAngle = radar.getAngle();
        double radarFov = radar.getFOV();
        int size = monitor.getSize();
        boolean xDir = facing.getAxis() == Direction.Axis.X;
        boolean zDir = facing.getAxis() == Direction.Axis.Z;
        double radarRange = radar.getRange();

        //Prepare MatrixStack
        if (size > 1) {
            if (facing.getStepX() < 0 || facing.getStepZ() > 0)
                ms.translate(-facing.getStepZ() * (size - 1), 0, facing.getStepX() * (size - 1));
            ms.scale(zDir ? size : 1, size, xDir ? size : 1);
        }
        ms.translate(facing.getStepX(), 0, facing.getStepZ());


        //Draw Radar Base
        SuperByteBuffer radarLine = CachedBufferer.partialFacing(ModPartials.RADAR_LINE, state, facing).light(LightTexture.FULL_BRIGHT);
        radarLine.rotateCentered(facing, (float) Math.toRadians(radarAngle - radarFov / 2 + 225));
        radarLine.color(0, 255, 0, 200);
        radarLine.light(LightTexture.FULL_BRIGHT).renderInto(ms, buffer.getBuffer(RenderType.translucent()));

        //Draw Entities
        AtomicInteger i = new AtomicInteger();
        radar.getEntityPositions(monitor.getFilter()).forEach((entity, pos) -> {
            i.getAndIncrement();
            // Calculate relative position scaled by radar range
            double scaledX = (pos.getX() - referencePos.getX()) / radarRange * .7 / 2;
            double scaledZ = (pos.getZ() - referencePos.getZ()) / radarRange * .7 / 2;

            // Translate and render hitbox
            SuperByteBuffer hitbox = CachedBufferer.partialFacing(ModPartials.HITBOX_1, state, facing);
            hitbox.color(MonitorUtils.getColor(entity)).light(LightTexture.FULL_BRIGHT);
            hitbox.translate(zDir ? scaledX : facing.getStepX() * .001 * i.get(), zDir ? -facing.getStepZ() * scaledZ : -facing.getStepX() * scaledX, xDir ? scaledZ : facing.getStepZ() * .001 * i.get());
            hitbox.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
        });

        //Draw selected entityMarker
        BlockPos targetPos = monitor.getTargetPos();
        if (targetPos == null)
            return;

        SuperByteBuffer entityMarker = CachedBufferer.partialFacing(ModPartials.TARGET_SELECTED, state, facing);
        double scaledX = (targetPos.getX() - referencePos.getX()) / radarRange * .7 / 2;
        double scaledZ = (targetPos.getZ() - referencePos.getZ()) / radarRange * .7 / 2;
        entityMarker.translate(zDir ? scaledX : facing.getStepX() * .002, zDir ? -facing.getStepZ() * scaledZ : -facing.getStepX() * scaledX, xDir ? scaledZ : facing.getStepZ() * .002);
        entityMarker.renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));
    }

}
