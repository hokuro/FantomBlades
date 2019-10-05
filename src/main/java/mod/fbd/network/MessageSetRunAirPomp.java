package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.tileentity.TileEntityAirPomp;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSetRunAirPomp {
	BlockPos ownPos;
	BlockPos targetPos;
	int facing;

	public MessageSetRunAirPomp(){}
	public MessageSetRunAirPomp(BlockPos own, BlockPos target, int face){
		ownPos = own;
		targetPos = target;
		facing = face;
	}

	public static void encode(MessageSetRunAirPomp pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.ownPos.getX());
		buf.writeInt(pkt.ownPos.getY());
		buf.writeInt(pkt.ownPos.getZ());
		buf.writeInt(pkt.targetPos.getX());
		buf.writeInt(pkt.targetPos.getY());
		buf.writeInt(pkt.targetPos.getZ());
		buf.writeInt(pkt.facing);
	}

	public static MessageSetRunAirPomp decode(PacketBuffer buf)
	{
		BlockPos ownPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());

		BlockPos targetPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());
		int facing = buf.readInt();
		return new MessageSetRunAirPomp(ownPos,targetPos,facing);
	}

	public static class Handler
	{
		public static void handle(final MessageSetRunAirPomp pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				TileEntity te;
				if (ctx.get().getSender() == null) {
					te = Minecraft.getInstance().world.getTileEntity(pkt.ownPos);
				}else {
					te = ctx.get().getSender().world.getTileEntity(pkt.ownPos);
				}
				if (te instanceof TileEntityAirPomp){
					((TileEntityAirPomp)te).setRun(pkt.targetPos);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
