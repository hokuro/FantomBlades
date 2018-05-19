package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageBladeforgeSmeltingStart  implements IMessage, IMessageHandler<MessageBladeforgeSmeltingStart, IMessage> {

	private int posx;
	private int posy;
	private int posz;

	public MessageBladeforgeSmeltingStart(){}
	public MessageBladeforgeSmeltingStart(int x, int y, int z){
		posx = x;
		posy = y;
		posz = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posx = buf.readInt();
		posy = buf.readInt();
		posz = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posx);
		buf.writeInt(posy);
		buf.writeInt(posz);
	}

	@Override
	public IMessage onMessage(MessageBladeforgeSmeltingStart message, MessageContext ctx){
		EntityPlayer player = ctx.getServerHandler().player;
		BlockPos pos = new BlockPos(message.posx,message.posy,message.posz);
		TileEntity ent = player.world.getTileEntity(pos);
		if (ent instanceof TileEntityBladeforge){
			((TileEntityBladeforge)ent).StartSmelting(player);
		}
		return null;
	}
}