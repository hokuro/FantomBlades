package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.tileentity.TileEntityBladeforge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;


public class MessageBladeforgeSmeltingStart {

	private int posx;
	private int posy;
	private int posz;

	public MessageBladeforgeSmeltingStart(){}
	public MessageBladeforgeSmeltingStart(int x, int y, int z){
		posx = x;
		posy = y;
		posz = z;
	}

	public static void encode(MessageBladeforgeSmeltingStart pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.posx);
		buf.writeInt(pkt.posy);
		buf.writeInt(pkt.posz);
	}

	public static MessageBladeforgeSmeltingStart decode(PacketBuffer buf)
	{
		int posx = buf.readInt();
		int posy = buf.readInt();
		int posz = buf.readInt();
		return new MessageBladeforgeSmeltingStart(posx,posy,posz);
	}

	public static class Handler
	{
		public static void handle(final MessageBladeforgeSmeltingStart pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				EntityPlayer player = ctx.get().getSender();
				BlockPos pos = new BlockPos(pkt.posx,pkt.posy,pkt.posz);
				TileEntity ent = player.world.getTileEntity(pos);
				if (ent instanceof TileEntityBladeforge){
					((TileEntityBladeforge)ent).StartSmelting(player);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}