package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.entity.mob.EntityArmorSmith;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairArmor {

	private EntityArmorSmith entity;
	private int id;

	public MessageRepairArmor(){}

	public MessageRepairArmor(EntityArmorSmith entityIn) {
		entity = entityIn;
		id = entityIn.getEntityId();
	}

	public MessageRepairArmor(int entid) {
		id = entid;
	}


	public static void encode(MessageRepairArmor pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.id);
	}

	public static MessageRepairArmor decode(PacketBuffer buf)
	{
		return new MessageRepairArmor(buf.readInt());
	}

	public static class Handler
	{
		public static void handle(final MessageRepairArmor pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				Entity ent = ctx.get().getSender().world.getEntityByID(pkt.id);
				if (ent instanceof EntityArmorSmith){
					((EntityArmorSmith)ent).repairArmor();
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
