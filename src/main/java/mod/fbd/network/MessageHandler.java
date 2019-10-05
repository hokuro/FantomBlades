package mod.fbd.network;

import mod.fbd.core.ModCommon;
import mod.fbd.network.Message_BladeLevelUpdate.EnumLevelKind;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MessageHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static final SimpleChannel Handler = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ModCommon.MOD_ID,ModCommon.MOD_CHANEL))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	public static void register()
	{
		int disc = 0;

		Handler.registerMessage(disc++, MessageBladeforgeSmeltingStart.class, MessageBladeforgeSmeltingStart::encode, MessageBladeforgeSmeltingStart::decode, MessageBladeforgeSmeltingStart.Handler::handle);
		Handler.registerMessage(disc++, MessageSetRunAirPomp.class, MessageSetRunAirPomp::encode, MessageSetRunAirPomp::decode, MessageSetRunAirPomp.Handler::handle);
		Handler.registerMessage(disc++, MessageSetRunBladeforge.class, MessageSetRunBladeforge::encode, MessageSetRunBladeforge::decode, MessageSetRunBladeforge.Handler::handle);
		Handler.registerMessage(disc++, MessageCreateEquipment.class, MessageCreateEquipment::encode, MessageCreateEquipment::decode, MessageCreateEquipment.Handler::handle);
		Handler.registerMessage(disc++, MessageRepairEquipment.class, MessageRepairEquipment::encode, MessageRepairEquipment::decode, MessageRepairEquipment.Handler::handle);
		Handler.registerMessage(disc++, Message_BladeLevelUpdate.class, Message_BladeLevelUpdate::encode, Message_BladeLevelUpdate::decode, Message_BladeLevelUpdate.Handler::handle);
	}

	public static void Send_MessageBladeforgeSmeltingStart(int x, int y, int z) {
		Handler.sendToServer(new MessageBladeforgeSmeltingStart(x,y,z));
	}

	public static void Send_MessageSetRunAirPomp(ServerWorld world, BlockPos pos, BlockPos requestPos, int index) {
		for (ServerPlayerEntity player : world.getPlayers()){
			Handler.sendTo(new MessageSetRunAirPomp(pos, requestPos, index), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);;
		}
	}

	public static void Send_MessageSetRunBladeforge(ServerWorld world, BlockPos pos, boolean run) {
		for (ServerPlayerEntity player : world.getPlayers()){
			Handler.sendTo(new MessageSetRunBladeforge(pos, run), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	public static void Send_ToClient() {
//		SocketAddress socketaddress  = Minecraft.getInstance().getIntegratedServer().getNetworkSystem().addLocalEndpoint();
//		NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
//		Handler.sendTo(message, networkmanager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void Send_MessageBladeLevelUpdate(EnumLevelKind levelBlade, BlockPos pos) {
		Handler.sendToServer(new Message_BladeLevelUpdate(levelBlade, pos));
	}

	public static void Send_MessageBladeLevelUpdate(EnumLevelKind levelEnchant, BlockPos pos, int enchantIndex, int i) {
		Handler.sendToServer(new Message_BladeLevelUpdate(levelEnchant, pos,enchantIndex,i));
	}

	public static void Send_MessageCreateEquipment(int id) {
		Handler.sendToServer(new MessageCreateEquipment(id));
	}

	public static void Send_MessageRepairEquipment(int id) {
		Handler.sendToServer(new MessageRepairEquipment(id));
	}
}
