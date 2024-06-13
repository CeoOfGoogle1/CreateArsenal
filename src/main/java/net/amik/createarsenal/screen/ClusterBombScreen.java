package net.amik.createarsenal.screen;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import net.amik.createarsenal.network.ClusterBombPacketC2S;
import net.amik.createarsenal.network.ModMessages;
import net.amik.createarsenal.network.SeaMinePacketC2S;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModGUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.glfw.GLFW;

public class ClusterBombScreen extends AbstractSimiScreen {

    private final ModGUI background = ModGUI.CLUSTER_GUI;
    private int floatLevel;


    public ClusterBombScreen(int prevFloatLevel) {
        super(Component.translatable("clusterbomb_gui.title"));
        this.floatLevel = prevFloatLevel;
    }


    @Override
    public void init() {
        setWindowSize(background.width + 30, background.height);
        super.init();

        int x = guiLeft;
        int y = guiTop;


        IconButton confirm = new IconButton(x + 155, y + 78, AllIcons.I_CONFIRM);
        confirm.withCallback(this::confirm);
        addRenderableWidget(confirm);


        ScrollInput floatInput =
                new ScrollInput(x + 20, y + 50, 150, 20).calling(state -> floatLevel = state).withRange(-64, 321).setState(floatLevel);
        floatInput.titled(Component.translatable("clusterbomb.altitude"));
        floatInput.active = true;
        floatInput.visible = true;

        addRenderableWidget(floatInput);
    }


    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft;
        int y = guiTop;

        background.render(graphics, x, y);

        FormattedCharSequence formattedcharsequence = title.getVisualOrderText();

        graphics.drawString(font, formattedcharsequence,
                (float) (x + (background.width - 8) / 2 - font.width(formattedcharsequence) / 2), (float) y + 4, 0xFFFFFF, false);

        graphics.drawString(font, String.valueOf(floatLevel), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(floatLevel)) / 2), (float) y + 51, 0xFFFFFF, false);


        GuiGameElement.of(ModBlocks.CLUSTER_BOMB.asStack()).<GuiGameElement
                        .GuiRenderBuilder>at(x + background.width + 8, y + background.height - 56, -200)
                .scale(5)
                .render(graphics);

    }

    @Override
    public boolean keyPressed(int keyCode, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (super.keyPressed(keyCode, p_keyPressed_2_, p_keyPressed_3_))
            return true;
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            confirm();
            return true;
        }
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        return false;
    }

    private void confirm() {
        ModMessages.sendToServer(new ClusterBombPacketC2S(floatLevel));
        onClose();
    }
}
