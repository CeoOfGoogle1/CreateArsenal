package net.amik.createarsenal.registrate.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;

public class ItemStackSyncS2CPacket extends SimplePacketBase {
    private final ItemStack item;
    private final BlockPos pos;

    public ItemStackSyncS2CPacket(ItemStack item, BlockPos pos) {
        this.item = item;
        this.pos = pos;
    }

    public ItemStackSyncS2CPacket(FriendlyByteBuf buf) {
        this(buf.readItem(), buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeItem(item);
        buf.writeBlockPos(pos);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        if(context.getDirection() != PLAY_TO_CLIENT)
            return false;
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemStackSyncBlockEntity blockEntity) {
                blockEntity.setItem(this.item, this.pos);
            }
        });
        return true;
    }

    public interface ItemStackSyncBlockEntity {
        void setItem(ItemStack stack, BlockPos blockPos);

        default void syncItem(ItemStack stack, BlockPos blockPos) {
            ModMessages.sendToClients(new ItemStackSyncS2CPacket(stack, blockPos));
        }
    }
}
