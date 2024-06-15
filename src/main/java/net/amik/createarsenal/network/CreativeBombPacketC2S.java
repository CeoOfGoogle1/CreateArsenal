package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class CreativeBombPacketC2S extends SimplePacketBase {
    private final CompoundTag nbt;

    public CreativeBombPacketC2S(FriendlyByteBuf buffer) {
        nbt = buffer.readNbt();
    }

    public CreativeBombPacketC2S(CompoundTag nbt) {
        this.nbt = nbt;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(nbt);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = player.getMainHandItem();
            stack.setTag(nbt);
        });
        return true;
    }
}
