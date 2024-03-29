package net.amik.createarsenal.shell;

import com.jozufozu.flywheel.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static net.amik.createarsenal.CreateArsenal.resource;

@SuppressWarnings("unused")
public class BulletModel extends EntityModel<BulletEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(resource("custom_bullet"), "main");

	private final ModelPart inside;
	private final ModelPart outside;

	private Color insideColor = new Color(255, 212, 0, 255);
	private Color outsideColor = new Color(255, 72, 0, 100);

	public BulletModel(ModelPart root) {
		this.inside = root.getChild("inside");
		this.outside = root.getChild("outside");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("inside", CubeListBuilder.create().texOffs(0, 0).addBox(.5f, .5f, .5f, 3.0F, 23.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
		partdefinition.addOrReplaceChild("outside", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0f, 0, 4.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}


	@Override
	public void setupAnim(@NotNull BulletEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public void setColor(Color outside, Color inside) {
		insideColor = inside;
		outsideColor = outside;
	}

	public void customRender(@NotNull PoseStack poseStack, @NotNull MultiBufferSource pBuffer) {

		outside.render(poseStack, pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(), true))
				, LightTexture.FULL_BRIGHT, LightTexture.FULL_BRIGHT, outsideColor.getRedAsFloat(), outsideColor.getGreenAsFloat(), outsideColor.getBlueAsFloat(), 1f);

		inside.render(poseStack, pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(), false))
				, LightTexture.FULL_BRIGHT, LightTexture.FULL_BRIGHT, insideColor.getRedAsFloat(), insideColor.getGreenAsFloat(), insideColor.getBlueAsFloat(), 1f);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
	}

	public @NotNull ResourceLocation getTextureLocation() {
		return resource("textures/entity/dynamic_bullet.png");
	}
}