package net.amik.createarsenal.screen;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import net.amik.createarsenal.network.ModMessages;
import net.amik.createarsenal.network.ProximityFusePacketC2S;
import net.amik.createarsenal.network.SeaMinePacketC2S;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModGUI;
import net.amik.createarsenal.registrate.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.glfw.GLFW;

public class ProximityFuseScreen extends AbstractSimiScreen {

    private final ModGUI background = ModGUI.PROXIMITY_GUI;
    private int range;


    public ProximityFuseScreen(int prevRange) {
        super(Component.translatable("proximityfuse_gui.title"));
        this.range = prevRange;
    }


    @Override
    public void init() {
        setWindowSize(background.width + 30, background.height);
        super.init();

        int x = guiLeft;
        int y = guiTop;


        IconButton confirm = new IconButton(x + 155, y + 55, AllIcons.I_CONFIRM);
        confirm.withCallback(this::confirm);
        addRenderableWidget(confirm);

        ScrollInput rangeInput =
                new ScrollInput(x + 20, y + 30, 150, 20).calling(state -> range = state).withRange(1, 9).setState(range).withShiftStep(1);
        rangeInput.titled(Component.translatable("proximityfuse.range"));
        rangeInput.active = true;
        rangeInput.visible = true;
        addRenderableWidget(rangeInput);

    }


    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft;
        int y = guiTop;

        background.render(graphics, x, y);

        FormattedCharSequence formattedcharsequence = title.getVisualOrderText();

        graphics.drawString(font, formattedcharsequence,
                (float) (x + (background.width - 8) / 2 - font.width(formattedcharsequence) / 2), (float) y + 4, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(range), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(range)) / 2), (float) y + 29, 0xFFFFFF, false);


        GuiGameElement.of(ModItems.PROXIMITY_FUSE.asStack()).<GuiGameElement
                        .GuiRenderBuilder>at(x + background.width, y + background.height - 52, -200)
                .scale(2)
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
        ModMessages.sendToServer(new ProximityFusePacketC2S(range));
        onClose();
    }
}
