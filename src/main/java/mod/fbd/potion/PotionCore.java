package mod.fbd.potion;

import mod.fbd.core.ModCommon;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;

public class PotionCore {
	public static Potion seiryupotion = new PotionSlipDamage().setRegistryName(ModCommon.MOD_ID, "slipdamage");
	public static PotionType seiryupotiontype = new PotionType().setRegistryName(ModCommon.MOD_ID, "slipdamagetype");

    public static void registerPotion(RegistryEvent.Register<Potion> event){
    	event.getRegistry().register(seiryupotion);
    }

    public static void registerType(RegistryEvent.Register<PotionType> event){
    	event.getRegistry().register(seiryupotiontype);
    }
}
