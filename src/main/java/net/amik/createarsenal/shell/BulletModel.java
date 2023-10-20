package net.amik.createarsenal.shell;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.jozufozu.flywheel.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static net.amik.createarsenal.CreateArsenal.resource;

public class BulletModel extends EntityModel<BulletEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(resource("custom_bullet"), "main");

	private final ModelPart inside;
	private final ModelPart outside;



	private Color insideColor=new Color(255,212,0,255);
	private Color OutsideColor=new Color(255,72,0,100);

	public BulletModel(ModelPart root) {
		this.inside = root.getChild("inside");
		this.outside = root.getChild("outside");	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition inside = partdefinition.addOrReplaceChild("inside", CubeListBuilder.create().texOffs(0, 4).addBox(-9.5F, -9.5F, -3.5F, 3.0F, 3.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition outside = partdefinition.addOrReplaceChild("outside", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -10.0F, -12.0F, 4.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}



	@Override
	public void setupAnim(BulletEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	public void setColor(Color outside, Color inside){
		insideColor=inside;
		OutsideColor=outside;
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		outside.render(poseStack, vertexConsumer, packedLight, packedOverlay, OutsideColor.getRedAsFloat(), OutsideColor.getGreenAsFloat(),OutsideColor.getBlueAsFloat(), .1f);
		inside.render(poseStack, vertexConsumer, packedLight, packedOverlay, insideColor.getRedAsFloat(), insideColor.getGreenAsFloat(), insideColor.getBlueAsFloat(), 1f);
	}

}