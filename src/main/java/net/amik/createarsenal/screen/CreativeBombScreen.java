package net.amik.createarsenal.screen;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import net.amik.createarsenal.network.CreativeBombPacketC2S;
import net.amik.createarsenal.network.ModMessages;
import net.amik.createarsenal.registrate.ModBlocks;
import net.amik.createarsenal.registrate.ModGUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.glfw.GLFW;

public class CreativeBombScreen extends AbstractSimiScreen {

    private final ModGUI background = ModGUI.CREATIVE_BOMB_GUI;
    int proximityRange = 0;
    int explosionRadius = 0;
    int fireRadius = 0;
    int timeRequired = 20;
    int armorPiercingLevel = 0;
    int clusterBombletCount = 0;
    int detonationAltitude = 32;
    int shrapnelCount = 0;
    int landmineCount = 0;
    int count = 1;

    public CreativeBombScreen(CompoundTag nbt) {
        super(Component.literal("Creative Bomb"));
        loadNBT(nbt);
    }

    private void loadNBT(CompoundTag nbt) {
        if (nbt.contains("proximityRange"))
            proximityRange = nbt.getInt("proximityRange");
        if (nbt.contains("explosionRadius"))
            explosionRadius = nbt.getInt("explosionRadius");
        if (nbt.contains("fireRadius"))
            fireRadius = nbt.getInt("fireRadius");
        if (nbt.contains("timeRequired"))
            timeRequired = nbt.getInt("timeRequired");
        if (nbt.contains("armorPiercingLevel"))
            armorPiercingLevel = nbt.getInt("armorPiercingLevel");
        if (nbt.contains("clusterBombletCount"))
            clusterBombletCount = nbt.getInt("clusterBombletCount");
        if (nbt.contains("detonationAltitude"))
            detonationAltitude = nbt.getInt("detonationAltitude");
        if (nbt.contains("shrapnelCount"))
            shrapnelCount = nbt.getInt("shrapnelCount");
        if (nbt.contains("landmineCount"))
            landmineCount = nbt.getInt("landmineCount");
        if (nbt.contains("count"))
            count = nbt.getInt("count");
    }


    @Override
    public void init() {
        setWindowSize(background.width, background.height);
        super.init();

        int x = guiLeft;
        int y = guiTop;


        IconButton confirm = new IconButton(x + 159, y + 245, AllIcons.I_CONFIRM);
        confirm.withCallback(this::confirm);
        addRenderableWidget(confirm);


        ScrollInput explosionInput =
                new ScrollInput(x + 20, y + 20, 150, 20).calling(state -> explosionRadius = state).withRange(0, 1000).setState(explosionRadius);
        explosionInput.titled(Component.literal("Explosion Radius"));
        explosionInput.active = true;
        explosionInput.visible = true;
        addRenderableWidget(explosionInput);

        ScrollInput fireInput =
                new ScrollInput(x + 20, y + 45, 150, 20).calling(state -> fireRadius = state).withRange(0, 1000).setState(fireRadius);
        fireInput.titled(Component.literal("Fire Radius"));
        fireInput.active = true;
        fireInput.visible = true;
        addRenderableWidget(fireInput);

        ScrollInput armorInput =
                new ScrollInput(x + 20, y + 70, 150, 20).calling(state -> armorPiercingLevel = state).withRange(0, 1000).setState(armorPiercingLevel);
        armorInput.titled(Component.literal("Armor Piercing Level"));
        armorInput.active = true;
        armorInput.visible = true;
        addRenderableWidget(armorInput);


        ScrollInput proximityInput =
                new ScrollInput(x + 20, y + 95, 150, 20).calling(state -> proximityRange = state).withRange(0, 1000).setState(proximityRange);
        proximityInput.titled(Component.literal("Proximity Range"));
        proximityInput.active = true;
        proximityInput.visible = true;
        addRenderableWidget(proximityInput);


        ScrollInput detonationInput =
                new ScrollInput(x + 20, y + 120, 150, 20).calling(state -> detonationAltitude = state).withRange(0, 1000).setState(detonationAltitude);
        detonationInput.titled(Component.literal("Cluster Altitude"));
        detonationInput.active = true;
        detonationInput.visible = true;
        addRenderableWidget(detonationInput);

        ScrollInput clusterInput =
                new ScrollInput(x + 20, y + 145, 150, 20).calling(state -> clusterBombletCount = state).withRange(0, 1000).setState(clusterBombletCount);
        clusterInput.titled(Component.literal("Cluster Bomblet Count"));
        clusterInput.active = true;
        clusterInput.visible = true;
        addRenderableWidget(clusterInput);

        ScrollInput shrapnelInput =
                new ScrollInput(x + 20, y + 170, 150, 20).calling(state -> shrapnelCount = state).withRange(0, 1000).setState(shrapnelCount);
        shrapnelInput.titled(Component.literal("Shrapnel Count"));
        shrapnelInput.active = true;
        shrapnelInput.visible = true;
        addRenderableWidget(shrapnelInput);

        ScrollInput timeInput =
                new ScrollInput(x + 20, y + 195, 150, 20).calling(state -> timeRequired = state).withRange(0, 1000).setState(timeRequired);
        timeInput.titled(Component.literal("Falling Time"));
        timeInput.active = true;
        timeInput.visible = true;
        addRenderableWidget(timeInput);

        ScrollInput landmine =
                new ScrollInput(x + 20, y + 220, 150, 20).calling(state -> landmineCount = state).withRange(0, 1000).setState(landmineCount);
        landmine.titled(Component.literal("Landmine Count"));
        landmine.active = true;
        landmine.visible = true;
        addRenderableWidget(landmine);


    }


    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = guiLeft + 4;
        int y = guiTop;

        background.render(graphics, x, y);

        FormattedCharSequence formattedcharsequence = title.getVisualOrderText();

        graphics.drawString(font, formattedcharsequence,
                (float) (x + (background.width - 8) / 2 - font.width(formattedcharsequence) / 2), (float) y + 4, 0x000000, false);

        graphics.drawString(font, String.valueOf(explosionRadius), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(explosionRadius)) / 2), (float) y + 28, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(fireRadius), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(fireRadius)) / 2), (float) y + 52, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(armorPiercingLevel), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(armorPiercingLevel)) / 2), (float) y + 76, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(proximityRange), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(proximityRange)) / 2), (float) y + 100, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(detonationAltitude), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(detonationAltitude)) / 2), (float) y + 124, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(clusterBombletCount), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(clusterBombletCount)) / 2), (float) y + 148, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(shrapnelCount), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(shrapnelCount)) / 2), (float) y + 172, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(timeRequired), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(timeRequired)) / 2), (float) y + 196, 0xFFFFFF, false);
        graphics.drawString(font, String.valueOf(landmineCount), (float) (x + (background.width - 8) / 2 - font.width(String.valueOf(landmineCount)) / 2), (float) y + 220, 0xFFFFFF, false);


        GuiGameElement.of(ModBlocks.CREATIVE_BOMB.asStack()).<GuiGameElement
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
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("proximityRange", proximityRange);
        nbt.putInt("explosionRadius", explosionRadius);
        nbt.putInt("fireRadius", fireRadius);
        nbt.putInt("timeRequired", timeRequired);
        nbt.putInt("armorPiercingLevel", armorPiercingLevel);
        nbt.putInt("clusterBombletCount", clusterBombletCount);
        nbt.putInt("detonationAltitude", detonationAltitude);
        nbt.putInt("shrapnelCount", shrapnelCount);
        nbt.putInt("landmineCount", landmineCount);
        nbt.putInt("count", count);
        ModMessages.sendToServer(new CreativeBombPacketC2S(nbt));
        onClose();
    }
}
