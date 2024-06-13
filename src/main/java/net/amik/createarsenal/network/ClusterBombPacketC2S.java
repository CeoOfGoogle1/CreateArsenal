package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ClusterBombPacketC2S extends SimplePacketBase {
    private final int floatLevel;

    public ClusterBombPacketC2S(FriendlyByteBuf buffer) {
        floatLevel = buffer.readInt();
    }

    public ClusterBombPacketC2S(int floatLevel) {
        this.floatLevel = floatLevel;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(floatLevel);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("floatLevel", floatLevel);
        });
        return true;
    }
}
