package net.amik.createarsenal.shell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import net.amik.createarsenal.shell.ShellEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class BulletRenderer extends EntityRenderer<ShellEntity> {

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ShellEntity entity, float yaw, float pt, PoseStack ms, MultiBufferSource buffer,
                       int light) {
        ItemStack item = entity.getItem();
        if (item.isEmpty())
            return;
        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(item, ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, ms, buffer, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(ShellEntity entity) {
        return null;
    }

}
