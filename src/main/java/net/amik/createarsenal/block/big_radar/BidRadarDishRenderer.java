package net.amik.createarsenal.block.big_radar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.amik.createarsenal.registrate.ModPartials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

public class BidRadarDishRenderer extends SmartBlockEntityRenderer<BigRadarDishBlockTileEntity> {
    public BidRadarDishRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BigRadarDishBlockTileEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);
        if(!blockEntity.isMultiBlock()||!blockEntity.isValid()) return;

        BlockState blockState = blockEntity.getBlockState();
        Direction direction = blockEntity.getBlockState().getValue(FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        float speed = blockEntity.getSpeed();
        float time = AnimationTickHolder.getRenderTime(blockEntity.getLevel());
        float angle = ((time * speed * 6 / 10f) % 360) / 180 * Mth.PI;


        SuperByteBuffer DishModel= CachedBufferer
                .partialFacing(ModPartials.BIG_DISH, blockState, direction);

        DishModel.rotateCentered(Direction.UP,angle);
        DishModel.light(light).renderInto(ms,vb);

    }
}
