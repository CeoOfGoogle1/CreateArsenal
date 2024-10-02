package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class RadioPacketC2S extends SimplePacketBase {

    private final BlockPos pos;

    public RadioPacketC2S(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public RadioPacketC2S(BlockPos pos) {
        this.pos = pos;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getOrCreateTag();
            tag.put("radio_blockPos", NbtUtils.writeBlockPos(pos));
        });
        return true;
    }
}
