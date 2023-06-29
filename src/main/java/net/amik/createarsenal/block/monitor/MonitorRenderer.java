package net.amik.createarsenal.block.monitor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.amik.createarsenal.registrate.ModBlockPartials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MonitorRenderer extends SmartBlockEntityRenderer<MonitorBlockEntity> {
    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    private float animation;

    @Override
    protected void renderSafe(MonitorBlockEntity monitor, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(monitor, partialTicks, ms, buffer, light, overlay);

        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        Direction direction=monitor.getBlockState().getValue(FACING).getOpposite();

        SuperByteBuffer radar= CachedBufferer.partialFacing(ModBlockPartials.RADAR,monitor.getBlockState(),direction);
        SuperByteBuffer radar_line= CachedBufferer.partialFacing(ModBlockPartials.RADAR_LINE,monitor.getBlockState(),direction);

        radar.translate(0,0,-0.01);
        radar.light(light).renderInto(ms,vb);

        animation+=.01/2f;
        if(animation>=1) animation=.0f;


        radar_line.rotateCentered(Direction.NORTH,(float) -Math.PI*2f*animation);
        radar_line.translate(0,0,.045);
        radar_line.light(light).renderInto(ms,vb);


        if(!monitor.hasDisplayEntities())return;

        BlockPos pos=monitor.getBlockPos();





        for (Entity scannedEntity : monitor.getDisplayEntities()) {

            double x=scannedEntity.getX()-pos.getX();
            double z=scannedEntity.getZ()-pos.getZ();
            SuperByteBuffer hitbox;
            if(scannedEntity.getType().getDimensions().width<1)
                hitbox=CachedBufferer.partialFacing(ModBlockPartials.HITBOX_1,monitor.getBlockState(),direction);
            else
                hitbox=CachedBufferer.partialFacing(ModBlockPartials.HITBOX_2,monitor.getBlockState(),direction);

            hitbox.translate(0,0,.043);
            hitbox.translate(x/MonitorBlockEntity.RANGE_WIDTH/2*.875,z/MonitorBlockEntity.RANGE_WIDTH/2*.875,0);
            hitbox.color(MonitorUtils.getColor(scannedEntity));
            hitbox.light(light).renderInto(ms,vb);
        }

    }


}
