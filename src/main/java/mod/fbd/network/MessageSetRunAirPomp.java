package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.tileentity.TileEntityAirPomp;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSetRunAirPomp implements IMessage, IMessageHandler<MessageSetRunAirPomp, IMessage> {


	BlockPos ownPos;
	BlockPos targetPos;
	int facing;

	public MessageSetRunAirPomp(){}
	public MessageSetRunAirPomp(BlockPos own, BlockPos target, int face){
		ownPos = own;
		targetPos = target;
		facing = face;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		ownPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());

		targetPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());
		facing = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(ownPos.getX());
		buf.writeInt(ownPos.getY());
		buf.writeInt(ownPos.getZ());
		buf.writeInt(targetPos.getX());
		buf.writeInt(targetPos.getY());
		buf.writeInt(targetPos.getZ());
		buf.writeInt(facing);
	}

	@Override
	public IMessage onMessage(MessageSetRunAirPomp message, MessageContext ctx){
		TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.ownPos);
		if (te instanceof TileEntityAirPomp){
			((TileEntityAirPomp)te).setRun(message.targetPos);
		}
		return null;
	}
}
