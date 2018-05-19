package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message_BladeStandChange implements IMessage, IMessageHandler<Message_BladeStandChange, IMessage> {

	private boolean isSet;
	private BlockPos pos;

	public Message_BladeStandChange(){}

	public Message_BladeStandChange(boolean value, BlockPos posIn) {
		isSet = value;
		pos =posIn;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		isSet = buf.readBoolean();
		pos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt()
				);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isSet);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public IMessage onMessage(Message_BladeStandChange message, MessageContext ctx){
		TileEntity ent = Minecraft.getMinecraft().world.getTileEntity(message.pos);
		if (ent instanceof TileEntityBladeStand){
			TileEntityBladeStand stand = ((TileEntityBladeStand)ent);
			if (message.isSet){
				stand.setKatana(Mod_FantomBlade.proxy.getEntityPlayerInstance().getHeldItemMainhand());
			}else{
				stand.getKatana();
			}
		}
		return null;
	}
}
