package net.amik.createarsenal.item;

import com.simibubi.create.foundation.gui.ScreenOpener;
import net.amik.createarsenal.screen.RadioScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioItem extends Item {

    public RadioItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (usedHand == InteractionHand.MAIN_HAND)
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> displayScreen(stack, player));

        return super.use(level, player, usedHand);
    }


    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(ItemStack stack, Player player) {
        if (player instanceof LocalPlayer)
            ScreenOpener
                    .open(new RadioScreen(getBlockPos(stack)));
    }

    public BlockPos getBlockPos(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("radio_blockPos"))
            return NbtUtils.readBlockPos(tag.getCompound("radio_blockPos"));
        return BlockPos.ZERO;
    }


}
