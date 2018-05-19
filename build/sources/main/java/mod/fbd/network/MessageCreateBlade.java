package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCreateBlade implements IMessage, IMessageHandler<MessageCreateBlade, IMessage> {

	private EntityBladeSmith entity;
	private int id;

	public MessageCreateBlade(){}

	public MessageCreateBlade(EntityBladeSmith entityIn) {
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
	public IMessage onMessage(MessageCreateBlade message, MessageContext ctx){
		Entity ent = ctx.getServerHandler().player.world.getEntityByID(message.id);
		if (ent instanceof EntityBladeSmith){
			EntityBladeSmith smith = ((EntityBladeSmith)ent);
				BlockPos checkPos = smith.getPos().add(0,-1,0);
				if (smith.canStartBladeSmith(ctx.getServerHandler().player.world, checkPos, true, smith)){
					ItemStack katana = smith.createBlade();
					if (!katana.isEmpty()){
						smith.startWork(ctx.getServerHandler().player.world, smith.getPos(), katana);
						ctx.getServerHandler().player.closeContainer();
					}else{
						ctx.getServerHandler().player.sendStatusMessage(new TextComponentString("please give me TAMAHAGANE"),false);
					}
				}else{
					ctx.getServerHandler().player.sendStatusMessage(new TextComponentString("not ready for work"),false);
				}
		}
		return null;
	}
}
