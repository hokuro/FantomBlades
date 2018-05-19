package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetRunBladeforge implements IMessage, IMessageHandler<MessageSetRunBladeforge, IMessage> {


	BlockPos ownPos;
	boolean isRun;

	public MessageSetRunBladeforge(){}
	public MessageSetRunBladeforge(BlockPos pos, boolean run){
		ownPos = pos;
		isRun = run;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ownPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());

		isRun = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(ownPos.getX());
		buf.writeInt(ownPos.getY());
		buf.writeInt(ownPos.getZ());
		buf.writeBoolean(isRun);
	}

	@Override
	public IMessage onMessage(MessageSetRunBladeforge message, MessageContext ctx){
		TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.ownPos);
		if (te instanceof TileEntityBladeforge){
			((TileEntityBladeforge)te).setRun(message.isRun);
		}
		return null;
	}
}
