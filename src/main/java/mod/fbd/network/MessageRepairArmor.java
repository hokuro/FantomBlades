package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.entity.mob.EntityArmorSmith;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRepairArmor  implements IMessage, IMessageHandler<MessageRepairArmor, IMessage> {

	private EntityArmorSmith entity;
	private int id;

	public MessageRepairArmor(){}

	public MessageRepairArmor(EntityArmorSmith entityIn) {
		entity = entityIn;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entity.getEntityId());
	}

	@Override
	public IMessage onMessage(MessageRepairArmor message, MessageContext ctx){
		Entity ent = ctx.getServerHandler().player.world.getEntityByID(message.id);
		if (ent instanceof EntityArmorSmith){
			((EntityArmorSmith)ent).repairArmor();
		}
		return null;
	}
}
