package mod.fbd.network;

import java.util.function.Supplier;

import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class Message_BladeLevelUpdate {

	private EnumLevelKind level;
	private int index;
	private int value;
	private BlockPos pos;

	public Message_BladeLevelUpdate(){}

	public Message_BladeLevelUpdate(EnumLevelKind kind, BlockPos posIn) {
		this(kind,posIn,0,0);
	}

	public Message_BladeLevelUpdate(EnumLevelKind kind, BlockPos posIn, int idx, int add) {
		level = kind;
		index = idx;
		value = add;
		pos  = posIn;
	}

	public static void encode(Message_BladeLevelUpdate pkt, PacketBuffer buf)
	{
		buf.writeInt(EnumLevelKind.getIndex(pkt.level));
		buf.writeInt(pkt.index);
		buf.writeInt(pkt.value);
		buf.writeInt(pkt.pos.getX());
		buf.writeInt(pkt.pos.getY());
		buf.writeInt(pkt.pos.getZ());
	}

	public static Message_BladeLevelUpdate decode(PacketBuffer buf)
	{
		EnumLevelKind level = EnumLevelKind.getFromIndex(buf.readInt());
		int index = buf.readInt();
		int value = buf.readInt();
		BlockPos pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
		return new Message_BladeLevelUpdate(level,pos,index,value);
	}

	public static class Handler
	{
		public static void handle(final Message_BladeLevelUpdate pkt, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() -> {
				TileEntity ent = ctx.get().getSender().world.getTileEntity(pkt.pos);
				if (ent instanceof TileEntityBladeAlter){
					TileEntityBladeAlter alter = ((TileEntityBladeAlter)ent);
					switch(pkt.level){
					case LEVEL_BLADE:
						alter.BladeLevelUp();
						break;
					case LEVEL_ENCHANT:
						alter.BladeEnchantUpdate(pkt.index,pkt.value);
						break;
					case LEVEL_POTION:
						alter.BladePotionUpdate(pkt.index,pkt.value);
						break;
					case LEVEL_POTION_DURATION:
						alter.BladePotionDurationUpdate(pkt.index,pkt.value);
						break;
					case NIJI:
						alter.BladeUpdateMugen();
						break;
					default:
						break;
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}

	public static enum EnumLevelKind{
		LEVEL_BLADE,
		LEVEL_ENCHANT,
		LEVEL_POTION,
		LEVEL_POTION_DURATION,
		NIJI;

		private static final EnumLevelKind[] values = {LEVEL_BLADE,LEVEL_ENCHANT,LEVEL_POTION,LEVEL_POTION_DURATION,NIJI};
		public static int getIndex(EnumLevelKind kind){
			for (int i = 0; i < values.length; i++){
				if (values[i] == kind){
					return i;
				}
			}
			return 0;
		}

		public static EnumLevelKind getFromIndex(int index){
			try{
				return values[index];
			}catch(Exception ex){
				return values[0];
			}
		}

	}
}
