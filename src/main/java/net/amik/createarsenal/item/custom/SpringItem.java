    // First of all, I'm really sorry if this doesn't work >*-*<

package net.amik.createarsenal.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpringItem extends Item {
    public SpringItem(Properies p) {
        super(p);
    }
    
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            return ActionResult.PASS;
        }

        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.getItem() != this) {
            return ActionResult.PASS;
        }

        playAnimation();


        return ActionResult.success(heldItem);
    }

    
    private void playAnimation() {
        
        Identifier[] frames = {
            new Identifier("CreateArsenal", "textures/items/1b.png"),
            new Identifier("CreateArsenal", "textures/items/2b.png"),
            new Identifier("CreateArsenal", "textures/items/3b.png"),
            new Identifier("CreateArsenal", "textures/items/4b.png")
                //if I remember I'll change it to the correct path later this is a placholder for now
        };

        net.minecraft.client.texture.TextureManager textureManager = net.minecraft.client.MinecraftClient.getInstance().getTextureManager();

        net.minecraft.client.texture.AnimationResourceMetadata animationMetadata = new net.minecraft.client.texture.AnimationResourceMetadata(1, frames.length);
        net.minecraft.client.texture.AnimationTexture animationTexture = new net.minecraft.client.texture.AnimationTexture(animationMetadata, frames);

        textureManager.registerTexture(new Identifier("CreateArsenal", "animation_texture"), animationTexture);

        //                                                      \/ Making the Animated Model \/
        net.minecraft.client.render.model.json.ModelTransformation modelTransform = net.minecraft.client.render.model.json.ModelTransformation.NONE;
        net.minecraft.client.render.model.json.ModelElement modelElement = new net.minecraft.client.render.model.json.ModelElement.Quad(
            new net.minecraft.client.render.model.json.ModelElement.Face(
                net.minecraft.client.render.model.json.ModelElement.Quad.Color.from(255, 255, 255),
                net.minecraft.client.render.model.json.ModelElement.Quad.Vertex.from(0, 0, 0, 0, 0),
                net.minecraft.client.render.model.json.ModelElement.Quad.Vertex.from(0, 0, 1, 0, 16),
                net.minecraft.client.render.model.json.ModelElement.Quad.Vertex.from(1, 0, 1, 16, 16),
                net.minecraft.client.render.model.json.ModelElement.Quad.Vertex.from(1, 0, 0, 16, 0),
                null, modelTransform, new Identifier("CreateArsenal", "animation_texture"), net.minecraft.client.render.model.json.ModelElement.TextureSlot.OVERLAY, false
            )
        );
        net.minecraft.client.render.model.json.ModelElement[] modelElements = { modelElement };
        net.minecraft.client.render.model.json.Model model = new net.minecraft.client.render.model.json.Model(modelElements, null, true, false, null, null, false);

        net.minecraft.client.render.model.BakedModel bakedModel = model.bake(net
                                                                             
}
//I bet there's a way easier way of doing this
