package net.amik.createarsenal.item;

import com.simibubi.create.foundation.utility.Components;
import net.amik.createarsenal.shell.ShellScale;
import net.amik.createarsenal.shell.TracerColors;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BulletItem extends ScaleItem{
    public BulletItem(Properties pProperties, ShellScale scale) {
        super(pProperties, scale);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            TracerColors.fillCreativeTab(pItems, this);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.getOrCreateTag().contains("tracerColorName")){
            pTooltipComponents.add(Components.literal(pStack.getOrCreateTag().getString("tracerColorName")+" Tracer"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
