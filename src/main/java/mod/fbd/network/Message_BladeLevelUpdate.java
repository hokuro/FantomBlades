package mod.fbd.network;

import io.netty.buffer.ByteBuf;
import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message_BladeLevelUpdate  implements IMessage, IMessageHandler<Message_BladeLevelUpdate, IMessage> {

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


	@Override
	public void fromBytes(ByteBuf buf) {
		level = EnumLevelKind.getFromIndex(buf.readInt());
		index = buf.readInt();
		value = buf.readInt();
		pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(EnumLevelKind.getIndex(level));
		buf.writeInt(index);
		buf.writeInt(value);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public IMessage onMessage(Message_BladeLevelUpdate message, MessageContext ctx){
		TileEntity ent = ctx.getServerHandler().player.world.getTileEntity(message.pos);
		if (ent instanceof TileEntityBladeAlter){
			TileEntityBladeAlter alter = ((TileEntityBladeAlter)ent);
			switch(message.level){
			case LEVEL_BLADE:
				alter.BladeLevelUp();
				break;
			case LEVEL_ENCHANT:
				alter.BladeEnchantUpdate(message.index,message.value);
				break;
			case LEVEL_POTION:
				alter.BladePotionUpdate(message.index,message.value);
				break;
			case LEVEL_POTION_DURATION:
				alter.BladePotionDurationUpdate(message.index,message.value);
				break;
			case NIJI:
				alter.BladeUpdateMugen();
				break;
			default:
				break;
			}
		}
		return null;
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
