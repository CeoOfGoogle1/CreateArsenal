package net.amik.createarsenal.block.landmine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.amik.createarsenal.block.aerialBombs.projectiles.FallingAerialBomb;
import net.amik.createarsenal.registrate.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FallingLandMineRenderer extends EntityRenderer<FallingLandMine> {
    private final BlockRenderDispatcher dispatcher;

    public FallingLandMineRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(FallingLandMine entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BakedModel model = this.dispatcher.getBlockModel(ModBlocks.LANDMINE.get().defaultBlockState());
        Minecraft.getInstance()
                .getItemRenderer()
                .renderModelLists(model, ItemStack.EMPTY, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer.getBuffer(RenderType.cutout()));
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }


    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(FallingLandMine entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}