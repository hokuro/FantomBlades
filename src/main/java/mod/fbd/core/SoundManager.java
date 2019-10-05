package mod.fbd.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundManager {
	public static String SOUND_SMITH_HART = "entity.fbd.smithhart";
	public static String SOUND_SMITH_DAMAGE = "entity.fbd.smithdamage";
	public static String SOUND_SMITH_DEAD = "entity.fbd.smithdead";
	public static String SOUND_SMITH_WORK = "entity.fbd.smithwork";
	public static String SOUND_GUN_GUNSHOT = "item.fbd.gunshot";
	public static String SOUND_GUN_RELOAD ="item.fbd.reload";
	public static String SOUND_GUN_NOBURRET = "item.fbd.noburret";

	public static SoundEvent sound_smith_hart = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_SMITH_HART))
			.setRegistryName(SOUND_SMITH_HART);
	public static SoundEvent sound_smith_damage = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_SMITH_DAMAGE))
			.setRegistryName(SOUND_SMITH_DAMAGE);
	public static SoundEvent sound_smith_dead = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_SMITH_DEAD))
			.setRegistryName(SOUND_SMITH_DEAD);
	public static SoundEvent sound_smith_work = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_SMITH_WORK))
			.setRegistryName(SOUND_SMITH_WORK);
	public static SoundEvent sound_gun_gunshot = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_GUN_GUNSHOT))
			.setRegistryName(SOUND_GUN_GUNSHOT);
	public static SoundEvent sound_gun_reload = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_GUN_RELOAD))
			.setRegistryName(SOUND_GUN_RELOAD);
	public static SoundEvent sound_gun_noburret = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_GUN_NOBURRET))
			.setRegistryName(SOUND_GUN_NOBURRET);
}
