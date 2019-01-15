package mod.fbd.potion;

import mod.fbd.core.ModCommon;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionCore {
	public static Potion seiryupotion;
	public static PotionType seiryupotiontype;

	private static void initPotion(){
		seiryupotion = new PotionSlipDamage().setRegistryName(ModCommon.MOD_ID, "slipdamage");


	}
	private static void initPotionType(){
		seiryupotiontype = new PotionType().setRegistryName(ModCommon.MOD_ID, "slipdamagetype");
	}

    @SubscribeEvent
    protected static void registerPotions(RegistryEvent.Register<Potion> event){
    	initPotion();
    	event.getRegistry().register(seiryupotion);
    }


    @SubscribeEvent
    protected static void registerPotionTypes(RegistryEvent.Register<PotionType> event){
    	initPotionType();
    	event.getRegistry().register(seiryupotiontype);
    }
}
