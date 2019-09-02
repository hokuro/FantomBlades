package mod.fbd.equipmenteffect;

import mod.fbd.core.ModCommon;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EnchantmentCore {
	public static Enchantment enc_enderkillre = new EnchantEnderKiller().setRegistryName(ModCommon.MOD_ID, "enderkiller");
	public static Enchantment enc_monsterkiller=new EnchantMonsterKiller().setRegistryName(ModCommon.MOD_ID,"mosterkiller");
	public static Enchantment enc_livingkiller = new EnchantLivingLikiller().setRegistryName(ModCommon.MOD_ID,"livingkiller");
	public static Enchantment enc_waterkiller = new EnchantWaterKiller().setRegistryName(ModCommon.MOD_ID,"waterkiller");

    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event){
        event.getRegistry().registerAll(
        		enc_enderkillre,
        		enc_monsterkiller,
        		enc_livingkiller,
        		enc_waterkiller
        );
    }
}
