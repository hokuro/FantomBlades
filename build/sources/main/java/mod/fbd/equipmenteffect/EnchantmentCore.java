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

	private static void init(){
		enc_enderkillre = new EnchantEnderKiller().setRegistryName(ModCommon.MOD_ID, "enderkiller");
		enc_monsterkiller=new EnchantMonsterKiller().setRegistryName(ModCommon.MOD_ID,"mosterkiller");
	}

    @SubscribeEvent
    protected static void registerEnchantments(RegistryEvent.Register<Enchantment> event){
    	init();
        event.getRegistry().registerAll(
        		enc_enderkillre,
        		enc_monsterkiller
        );
    }
}
