package net.amik.createarsenal.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ClusterBombPacketC2S extends SimplePacketBase {
    private final int detonationAltitude;

    public ClusterBombPacketC2S(FriendlyByteBuf buffer) {
        detonationAltitude = buffer.readInt();
    }

    public ClusterBombPacketC2S(int detonationAltitude) {
        this.detonationAltitude = detonationAltitude;
    }


    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeInt(detonationAltitude);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getOrCreateTag();
            tag.putInt("detonationAltitude", detonationAltitude);
        });
        return true;
    }
}
