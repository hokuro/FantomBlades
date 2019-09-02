package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairBlade {

	private EntityBladeSmith entity;
	private int id;

	public MessageRepairBlade(){}

	public MessageRepairBlade(EntityBladeSmith entityIn) {
		entity = entityIn;
		id = entityIn.getEntityId();
	}

	public MessageRepairBlade(int entid) {
		id = entid;
	}


	public static void encode(MessageRepairBlade pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.id);
	}

	public static MessageRepairBlade decode(PacketBuffer buf)
	{
		return new MessageRepairBlade(buf.readInt());
	}

	public static class Handler
	{
		public static void handle(final MessageRepairBlade pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				Entity ent = ctx.get().getSender().world.getEntityByID(pkt.id);
				if (ent instanceof EntityBladeSmith){
					((EntityBladeSmith)ent).repairBlade();
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
