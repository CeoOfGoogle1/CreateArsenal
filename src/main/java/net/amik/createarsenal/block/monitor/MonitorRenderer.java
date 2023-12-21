package net.amik.createarsenal.block.monitor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MonitorRenderer extends SmartBlockEntityRenderer<MonitorBlockEntity> {
    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    protected void renderSafe(MonitorBlockEntity monitor, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(monitor, partialTicks, ms, buffer, light, overlay);

        if (monitor.tickSinceLastWork <= 0)
            return;


        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        Direction direction = monitor.getBlockState().getValue(FACING).getOpposite();
        MonitorBlock.Shape shape=monitor.getBlockState().getValue(MonitorBlock.SHAPE);


        if(shape.equals(MonitorBlock.Shape.GHOST)) return;

        SuperByteBuffer radar = CachedBufferer.partialFacing(ModPartials.RADAR, monitor.getBlockState(), direction);
        SuperByteBuffer radar_line = CachedBufferer.partialFacing(ModPartials.RADAR_LINE, monitor.getBlockState(), direction);

        if(shape.equals(MonitorBlock.Shape.DOUBLE)) {
            radar.scale(2, 2, 1);
            radar_line.scale(2, 2, 1);
        }

        switch (direction) {
            case SOUTH -> {
            radar.translate(0, 0, -0.01);
            radar_line.translate(0, 0, .045);}
            case NORTH -> {
                radar.translate(0, 0, 0.01);
                radar_line.translate(0, 0, -.045);}
            case EAST -> {
                radar.translate(-0.01, 0, 0);
                radar_line.translate(.045, 0,0 );}
            case WEST -> {
                radar.translate(0.01, 0, 0);
                radar_line.translate(-.045, 0, 0);}
        }


        radar_line.rotateCentered(direction,(float) -Math.PI*2f*monitor.getAnimation());


        radar_line.light(light).renderInto(ms,vb);
        radar.light(light).renderInto(ms,vb);


        if(!monitor.hasDisplayEntities())return;

        BlockPos pos=monitor.getBlockPos();





        for (Entity scannedEntity : monitor.getDisplayEntities()) {

            double x=scannedEntity.getX()-pos.getX();
            double z=scannedEntity.getZ()-pos.getZ();
            SuperByteBuffer hitbox;
            if(scannedEntity.getType().getDimensions().width<1||(scannedEntity instanceof Slime slime&&slime.getSize()<3))
                hitbox=CachedBufferer.partialFacing(ModPartials.HITBOX_1,monitor.getBlockState(),direction);
            else
                hitbox=CachedBufferer.partialFacing(ModPartials.HITBOX_2,monitor.getBlockState(),direction);

            if(shape.equals(MonitorBlock.Shape.DOUBLE)) {
                hitbox.scale(2, 2, 1);
            }

            if(scannedEntity.getType().getDimensions().width<.5) {
                hitbox.scale(.5f, .5f, .5f);
                switch (direction) {
                    case SOUTH -> hitbox.translate(.5f, .5f, 0);
                    case EAST -> hitbox.translate(0, .5f, 0.5f);
                    case WEST -> hitbox.translate(1, .5f, .5);
                    case NORTH -> hitbox.translate(.5, .5f, 1f);

                }
            }

            switch (direction) {
                case SOUTH -> {
                    hitbox.translate(0, 0, .043);
                    hitbox.translate(x/monitor.widthRange /2*.875,z/monitor.widthRange /2*.875,0);
                }
                case NORTH -> {
                    hitbox.translate(0, 0, -.043);
                    hitbox.translate(x/monitor.widthRange /2*.875,-z/monitor.widthRange /2*.875,0);
                }
                case WEST -> {
                    hitbox.translate(-.043,0,0);
                    hitbox.translate(0,-x/monitor.widthRange /2*.875,z/monitor.widthRange /2*.875);
                }
                case EAST -> {
                    hitbox.translate(.043, 0, 0);
                    hitbox.translate(0,x/monitor.widthRange /2*.875,z/monitor.widthRange /2*.875);
                }
            }

            hitbox.color(MonitorUtils.getColor(scannedEntity));
            hitbox.light(light).renderInto(ms,vb);
        }

    }


}
