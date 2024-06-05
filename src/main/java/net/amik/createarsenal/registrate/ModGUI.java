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

    SEAMINE_GUI("seamine_gui", 34, 77, 187, 100);


    public final ResourceLocation location;
    public final int width;
    public final int height;
    public final int startX;
    public final int startY;

    ModGUI(String location, int width, int height) {
        this(location, 0, 0, width, height);
    }

    ModGUI(String location, int startX, int startY, int width, int height) {
        this(CreateArsenal.MOD_ID, location, startX, startY, width, height);
    }

    ModGUI(String namespace, String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y) {
        graphics.blit(location, x, y, startX, startY, width, height);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y, Color c) {
        bind();
        UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
    }

}
