package net.amik.createarsenal.item;

import net.amik.createarsenal.block.staticTurret.modularGun.barrel.GunBarrelBlockEntity;
import net.amik.createarsenal.shell.ShellScale;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class ScaleItem extends Item {

    private final ShellScale scale;
    public ScaleItem(Properties pProperties, ShellScale scale) {
        super(pProperties);
        this.scale=scale;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof GunBarrelBlockEntity barrel) {
            if (pContext.getPlayer() != null && pContext.getPlayer().isShiftKeyDown())
                return barrel.useOn(pContext);
        }
        return super.useOn(pContext);
    }

    public ShellScale getScale() {
        return scale;
    }
}
