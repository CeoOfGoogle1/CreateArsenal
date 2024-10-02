package net.amik.createarsenal.item;

import net.amik.createarsenal.compat.cbc.block.CannonControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class MarkerItem extends Item {

    public MarkerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        System.out.println("MarkerItem: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
        if (context.getLevel().getBlockEntity(pos) instanceof CannonControllerBlockEntity) {
            context.getItemInHand().getOrCreateTag().put("controller_pos", NbtUtils.writeBlockPos(pos));
            context.getPlayer().sendSystemMessage(Component.literal("Controller set"));
            return InteractionResult.SUCCESS;
        }
        CompoundTag tag = context.getItemInHand().getOrCreateTag();
        if (tag.contains("controller_pos")) {
            if (context.getLevel().getBlockEntity(NbtUtils.readBlockPos(tag.getCompound("controller_pos"))) instanceof CannonControllerBlockEntity controller) {
                controller.setTarget(pos);
                context.getLevel().setBlock(pos, Blocks.TARGET.defaultBlockState(), 3);
                context.getPlayer().sendSystemMessage(Component.literal("Target set"));
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
