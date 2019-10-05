package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntitySmithBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCreateEquipment {

	private EntityBladeSmith entity;
	private int id;

	public MessageCreateEquipment(){}

	public MessageCreateEquipment(EntityBladeSmith entityIn) {
		entity = entityIn;
		id = entityIn.getEntityId();
	}

	public MessageCreateEquipment(int entid) {
		id = entid;
	}


	public static void encode(MessageCreateEquipment pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.id);
	}

	public static MessageCreateEquipment decode(PacketBuffer buf)
	{
		return new MessageCreateEquipment(buf.readInt());
	}

	public static class Handler
	{
		public static void handle(final MessageCreateEquipment pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				Entity ent = ctx.get().getSender().world.getEntityByID(pkt.id);
				if (ent instanceof EntitySmithBase){
					EntitySmithBase smith = ((EntitySmithBase)ent);
						BlockPos checkPos = smith.getPos();
						if (smith.canStartSmith(ctx.get().getSender().world, checkPos, true, smith)){
							ItemStack result = smith.create();
							if (!result.isEmpty()){
								smith.startWork(ctx.get().getSender().world, smith.getPos(), result);
								ctx.get().getSender().closeContainer();
							}else{
								ctx.get().getSender().sendStatusMessage(new StringTextComponent("please give me TAMAHAGANE"),false);
							}
						}else{
							ctx.get().getSender().sendStatusMessage(new StringTextComponent("not ready for work"),false);
						}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}

}
