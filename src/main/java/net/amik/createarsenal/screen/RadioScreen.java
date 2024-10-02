package net.amik.createarsenal.screen;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import net.amik.createarsenal.network.ModMessages;
import net.amik.createarsenal.network.RadioPacketC2S;
import net.amik.createarsenal.registrate.ModGUI;
import net.amik.createarsenal.registrate.ModItems;
import net.amik.createarsenal.screen.widget.BlockPosInputBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class RadioScreen extends AbstractSimiScreen {

    private final ModGUI background = ModGUI.RADIO_GUI;
    private BlockPos pos;
    private int xPos;
    private int yPos;
    private int zPos;


    public RadioScreen(BlockPos prevRange) {
        super(Component.literal("Artillery Radio"));
        this.pos = prevRange;
    }


    @Override
    public void init() {
        setWindowSize(background.width + 30, background.height);
        super.init();

        IconButton confirm = new IconButton(guiLeft + 155, guiTop + 55, AllIcons.I_CONFIRM);
        confirm.withCallback(this::confirm);
        addRenderableWidget(confirm);
        BlockPosInputBox XposInput =
                new BlockPosInputBox(font, guiLeft + 20, guiTop + 30, 20, 20, null, Component.literal("X"));
        XposInput.active = true;
        XposInput.visible = true;
        addRenderableWidget(XposInput);

    }


    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft;
        int y = guiTop;

        background.render(graphics, x, y);

        GuiGameElement.of(ModItems.RADIO.asStack()).<GuiGameElement
                        .GuiRenderBuilder>at(x + background.width, y + background.height, -200)
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
        ModMessages.sendToServer(new RadioPacketC2S(pos));
        onClose();
    }
}
