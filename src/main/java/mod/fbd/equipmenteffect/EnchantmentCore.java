package mod.fbd.equipmenteffect;

import mod.fbd.core.ModCommon;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EnchantmentCore {
	public static Enchantment enc_enderkillre;
	public static Enchantment enc_monsterkiller;
	public static Enchantment enc_livingkiller;
	public static Enchantment enc_waterkiller;

	private static void init(){
		enc_enderkillre = new EnchantEnderKiller().setRegistryName(ModCommon.MOD_ID, "enderkiller");
		enc_monsterkiller=new EnchantMonsterKiller().setRegistryName(ModCommon.MOD_ID,"mosterkiller");
		enc_livingkiller = new EnchantLivingLikiller().setRegistryName(ModCommon.MOD_ID,"livingkiller");
		enc_waterkiller = new EnchantWaterKiller().setRegistryName(ModCommon.MOD_ID,"waterkiller");
	}

    @SubscribeEvent
    protected static void registerEnchantments(RegistryEvent.Register<Enchantment> event){
    	init();
        event.getRegistry().registerAll(
        		enc_enderkillre,
        		enc_monsterkiller,
        		enc_livingkiller,
        		enc_waterkiller
        );
    }
}
