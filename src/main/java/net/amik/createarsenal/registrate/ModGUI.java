package net.amik.createarsenal.registrate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.utility.Color;
import net.amik.createarsenal.CreateArsenal;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModGUI implements ScreenElement {
    CREATIVE_BOMB_GUI("creative_bomb_gui", 0, 0, 187, 268, 512, 512),
    PROXIMITY_GUI("proximity_gui", 187, 100),
    CLUSTER_GUI("cluster_gui", 187, 78),
    SEAMINE_GUI("seamine_gui", 187, 78);


    public final ResourceLocation location;
    public final int width;
    public final int height;
    public final int startX;
    public final int startY;
    public final int textureWidth;
    public final int textureHeight;

    ModGUI(String location, int width, int height) {
        this(location, 0, 0, width, height);
    }

    ModGUI(String location, int startX, int startY, int width, int height) {
        this(CreateArsenal.MOD_ID, location, startX, startY, width, height, 256, 256);
    }

    ModGUI(String namespace, String location, int startX, int startY, int width, int height, int textureWidth, int textureHeight) {

        this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    ModGUI(String location, int startX, int startY, int width, int height, int textureWidth, int textureHeight) {
        this(CreateArsenal.MOD_ID, location, startX, startY, width, height, textureWidth, textureHeight);

    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y) {
        graphics.blit(location, x, y, 0, startX, startY, width, height, textureWidth, textureHeight);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y, Color c) {
        bind();
        UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
    }

}
