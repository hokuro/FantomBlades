package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRepairBlade  implements IMessage, IMessageHandler<MessageRepairBlade, IMessage> {

	private EntityBladeSmith entity;
	private int id;

	public MessageRepairBlade(){}

	public MessageRepairBlade(EntityBladeSmith entityIn) {
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
	public IMessage onMessage(MessageRepairBlade message, MessageContext ctx){
		Entity ent = ctx.getServerHandler().player.world.getEntityByID(message.id);
		if (ent instanceof EntityBladeSmith){
			((EntityBladeSmith)ent).repairBlade();
		}
		return null;
	}
}
