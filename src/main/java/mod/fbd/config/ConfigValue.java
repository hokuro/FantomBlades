package mod.fbd.config;

import mod.fbd.util.ModUtil;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ConfigValue{
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General{
		public final ForgeConfigSpec.ConfigValue<String> bladeNames;
		public final ForgeConfigSpec.ConfigValue<Boolean> gunGuiAutoOpen;;

		public General(Builder builder) {
			builder.push("General");
			bladeNames = builder
				.comment("show debutlog")
				.define("BladeNames","刀");
			gunGuiAutoOpen = builder
				.comment("show debutlog")
				.define("GunGuiAutoOpen",false);
			builder.pop();
		}

		public Boolean GunGuiAutoOpen() {
			return gunGuiAutoOpen.get();
		}

		public String getRandomName() {
			String ret = "刀";
			String[] blades = bladeNames.get().split(",");
			if (blades.length != 0){
				ret = blades[ModUtil.random(blades.length)];
			}
			return ret;
		}
	}
}