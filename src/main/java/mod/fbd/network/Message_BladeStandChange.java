package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class Message_BladeStandChange{

	private boolean isSet;
	private BlockPos pos;

	public Message_BladeStandChange(){}

	public Message_BladeStandChange(boolean value, BlockPos posIn) {
		isSet = value;
		pos =posIn;
	}

	public static void encode(Message_BladeStandChange pkt, PacketBuffer buf)
	{
		buf.writeBoolean(pkt.isSet);
		buf.writeInt(pkt.pos.getX());
		buf.writeInt(pkt.pos.getY());
		buf.writeInt(pkt.pos.getZ());
	}

	public static Message_BladeStandChange decode(PacketBuffer buf)
	{
		boolean isSet = buf.readBoolean();
		BlockPos pos = new BlockPos(
				buf.readInt(),
				buf.readInt(),
				buf.readInt()
				);
		return new Message_BladeStandChange(isSet,pos);
	}

	public static class Handler
	{
		public static void handle(final Message_BladeStandChange pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				TileEntity ent = ctx.get().getSender().world.getTileEntity(pkt.pos);
				if (ent instanceof TileEntityBladeStand){
					TileEntityBladeStand stand = ((TileEntityBladeStand)ent);
					if (pkt.isSet){
						stand.setKatana(ctx.get().getSender().getHeldItemMainhand());
					}else{
						stand.getKatana();
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
