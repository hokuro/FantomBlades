package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.entity.mob.EntityArmorSmith;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCreateArmor{


	private EntityArmorSmith entity;
	private int id;

	public MessageCreateArmor(){}

	public MessageCreateArmor(EntityArmorSmith entity2) {
		entity = entity2;
		id = entity2.getEntityId();
	}

	public MessageCreateArmor(int entid) {
		id = entid;
	}

	public static void encode(MessageCreateArmor pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.id);
	}

	public static MessageCreateArmor decode(PacketBuffer buf)
	{
		return new MessageCreateArmor(buf.readInt());
	}

	public static class Handler
	{
		public static void handle(final MessageCreateArmor pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				Entity ent = ctx.get().getSender().world.getEntityByID(pkt.id);
				if (ent instanceof EntityArmorSmith){
					EntityArmorSmith smith = ((EntityArmorSmith)ent);
					BlockPos checkPos = smith.getPos().add(0,-1,0);
					if (smith.canStartArmorSmith(ctx.get().getSender().world, checkPos, true, smith)){
						ItemStack katana = smith.createBlade();
						if (!katana.isEmpty()){
							smith.startWork(ctx.get().getSender().world, smith.getPos(), katana);
							ctx.get().getSender().closeContainer();
						}else{
							ctx.get().getSender().sendStatusMessage(new TextComponentString("please give me TAMAHAGANE"),false);
						}
					}else{
						ctx.get().getSender().sendStatusMessage(new TextComponentString("not ready for work"),false);
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}

}
