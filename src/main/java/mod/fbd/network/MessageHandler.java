package mod.fbd.network;

import mod.fbd.core.ModCommon;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.network.Message_BladeLevelUpdate.EnumLevelKind;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
		Handler.registerMessage(disc++, MessageCreateBlade.class, MessageCreateBlade::encode, MessageCreateBlade::decode, MessageCreateBlade.Handler::handle);
		Handler.registerMessage(disc++, MessageRepairBlade.class, MessageRepairBlade::encode, MessageRepairBlade::decode, MessageRepairBlade.Handler::handle);
		Handler.registerMessage(disc++, Message_BladeLevelUpdate.class, Message_BladeLevelUpdate::encode, Message_BladeLevelUpdate::decode, Message_BladeLevelUpdate.Handler::handle);
		Handler.registerMessage(disc++, MessageCreateArmor.class, MessageCreateArmor::encode, MessageCreateArmor::decode, MessageCreateArmor.Handler::handle);
		Handler.registerMessage(disc++, MessageRepairArmor.class, MessageRepairArmor::encode, MessageRepairArmor::decode, MessageRepairArmor.Handler::handle);
	}

	public static void Send_MessageBladeforgeSmeltingStart(int x, int y, int z) {
		Handler.sendToServer(new MessageBladeforgeSmeltingStart(x,y,z));
	}

	public static void Send_MessageCreateAromor(EntityArmorSmith entity) {
		Handler.sendToServer(new MessageCreateArmor(entity));
	}

	public static void SendMessageRepairArmor(EntityArmorSmith entity) {
		Handler.sendToServer(new MessageRepairArmor(entity));
	}

	public static void Send_MessageBladeLevelUpdate(EnumLevelKind levelBlade, BlockPos pos) {
		Handler.sendToServer(new Message_BladeLevelUpdate(levelBlade, pos));
	}

	public static void Send_MessageBladeLevelUpdate(EnumLevelKind levelEnchant, BlockPos pos, int enchantIndex, int i) {
		Handler.sendToServer(new Message_BladeLevelUpdate(levelEnchant, pos,enchantIndex,i));
	}

	public static void Send_MessageCreateBlade(EntityBladeSmith entity) {
		Handler.sendToServer(new MessageCreateBlade(entity));
	}

	public static void Send_MessageRepairBlade(EntityBladeSmith entity) {
		Handler.sendToServer(new MessageRepairBlade(entity));
	}
}
