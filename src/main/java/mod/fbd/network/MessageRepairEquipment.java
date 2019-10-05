package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.entity.mob.EntitySmithBase;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairEquipment {

	private int id;

	public MessageRepairEquipment(int entid) {
		id = entid;
	}


	public static void encode(MessageRepairEquipment pkt, PacketBuffer buf) {
		buf.writeInt(pkt.id);
	}

	public static MessageRepairEquipment decode(PacketBuffer buf) {
		return new MessageRepairEquipment(buf.readInt());
	}

	public static class Handler {
		public static void handle(final MessageRepairEquipment pkt, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity ent = ctx.get().getSender().world.getEntityByID(pkt.id);
				if (ent instanceof EntitySmithBase){
					((EntitySmithBase)ent).repair();
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
