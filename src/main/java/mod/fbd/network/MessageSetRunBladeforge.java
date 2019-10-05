package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSetRunBladeforge {
	BlockPos ownPos;
	boolean isRun;

	public MessageSetRunBladeforge(){}
	public MessageSetRunBladeforge(BlockPos pos, boolean run){
		ownPos = pos;
		isRun = run;
	}

	public static void encode(MessageSetRunBladeforge pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.ownPos.getX());
		buf.writeInt(pkt.ownPos.getY());
		buf.writeInt(pkt.ownPos.getZ());
		buf.writeBoolean(pkt.isRun);
	}

	public static MessageSetRunBladeforge decode(PacketBuffer buf)
	{
		BlockPos ownPos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt());

		boolean isRun = buf.readBoolean();
		return new MessageSetRunBladeforge(ownPos, isRun);
	}

	public static class Handler
	{
		public static void handle(final MessageSetRunBladeforge pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				TileEntity te;
				if (ctx.get().getSender() == null) {
					te = Minecraft.getInstance().world.getTileEntity(pkt.ownPos);
				}else {
					te = ctx.get().getSender().world.getTileEntity(pkt.ownPos);
				}
				if (te instanceof TileEntityBladeforge){
					((TileEntityBladeforge)te).setRun(pkt.isRun);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
