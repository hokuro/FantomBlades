package mod.fbd.core;

import net.minecraft.util.math.Vec3i;

public final class ModCommon {
	// デバッグモードかどうか
	public static boolean isDebug = false;

	public static boolean isDevelop= false;

	// モッドID
	public static final String MOD_ID = "fbd";
	// iモッド名
	public static final String MOD_NAME = "FantomBlads";

	// iコンフィグ カテゴリー general
	public static final String MOD_CHANEL ="mod_channel_fantomblads";
	public static final String GUI_ID_BLADEFORGE = "gui_bladeforge";
	public static final String GUI_ID_BLADESMITH = "gui_bladesmith";
	public static final String GUI_ID_BLADEALTER = "gui_bladealter";
	public static final String GUI_ID_REVOLVER = "gui_revolver";
	public static final String GUI_ID_AUTOMATIC = "gui_automatic";
	public static final String GUI_ID_CARTRIDGE = "gui_cartridge";
	public static final String GUI_ID_GUNCUSTOMAIZER = "gui_guncustomizer";
	public static final String GUI_ID_ARMORSMITH = "gui_aromorsmith";

	// iエンチャント
	public static final String ENCHANTMENT_BLADEGENBU="enc_bladegenbu";
	public static final String ENCHANTMENT_BLADESEIRYU="enc_bladeseiryu";
	public static final String ENCHANTMENT_BLADESUZAKU="enc_bladesuzaku";
	public static final String ENCHANTMENT_BLADEBYAKO="enc_bladebyako";
	public static final String ENCHANTMENT_BLADEKIRIN="enc_bladekirin";
	public static final String ENCHANTMENT_BLADENIJI = "enc_bladeniji";

	// Container ID
	public static final String MOD_CONTAINERID_REVOLVER = "container_revolver";
	public static final String MOD_CONTAINERID_AUTOMATIC = "container_automatic";
	public static final String MOD_CONTAINERID_CARTRIDGE = "container_cartridge";
	public static final String MOD_CONTAINERID_BLADEFORGE = "container_bladeforge";
	public static final String MOD_CONTAINERID_BLADEALTER = "container_bladealter";
	public static final String MOD_CONTAINERID_GUNCUSTOMIZER = "container_guncustomizer";
	public static final String MOD_CONTAINERID_BLADESMITH = "container_bladesmith";
	public static final String MOD_CONTAINERID_ARMORSMITH = "container_armorsmith";

	public static final Vec3i[] SearchBlock11to11 =new Vec3i[] {
			new Vec3i(-5, -5, -5), new Vec3i(-4, -5, -5), new Vec3i(-3, -5, -5), new Vec3i(-2, -5, -5), new Vec3i(-1, -5, -5), new Vec3i(0, -5, -5), new Vec3i(1, -5, -5), new Vec3i(2, -5, -5), new Vec3i(3, -5, -5), new Vec3i(4, -5, -5), new Vec3i(5, -5, -5),
			new Vec3i(-5, -4, -5), new Vec3i(-4, -4, -5), new Vec3i(-3, -4, -5), new Vec3i(-2, -4, -5), new Vec3i(-1, -4, -5), new Vec3i(0, -4, -5), new Vec3i(1, -4, -5), new Vec3i(2, -4, -5), new Vec3i(3, -4, -5), new Vec3i(4, -4, -5), new Vec3i(5, -4, -5),
			new Vec3i(-5, -3, -5), new Vec3i(-4, -3, -5), new Vec3i(-3, -3, -5), new Vec3i(-2, -3, -5), new Vec3i(-1, -3, -5), new Vec3i(0, -3, -5), new Vec3i(1, -3, -5), new Vec3i(2, -3, -5), new Vec3i(3, -3, -5), new Vec3i(4, -3, -5), new Vec3i(5, -3, -5),
			new Vec3i(-5, -2, -5), new Vec3i(-4, -2, -5), new Vec3i(-3, -2, -5), new Vec3i(-2, -2, -5), new Vec3i(-1, -2, -5), new Vec3i(0, -2, -5), new Vec3i(1, -2, -5), new Vec3i(2, -2, -5), new Vec3i(3, -2, -5), new Vec3i(4, -2, -5), new Vec3i(5, -2, -5),
			new Vec3i(-5, -1, -5), new Vec3i(-4, -1, -5), new Vec3i(-3, -1, -5), new Vec3i(-2, -1, -5), new Vec3i(-1, -1, -5), new Vec3i(0, -1, -5), new Vec3i(1, -1, -5), new Vec3i(2, -1, -5), new Vec3i(3, -1, -5), new Vec3i(4, -1, -5), new Vec3i(5, -1, -5),
			new Vec3i(-5,  0, -5), new Vec3i(-4,  0, -5), new Vec3i(-3,  0, -5), new Vec3i(-2,  0, -5), new Vec3i(-1,  0, -5), new Vec3i(0,  0, -5), new Vec3i(1,  0, -5), new Vec3i(2,  0, -5), new Vec3i(3,  0, -5), new Vec3i(4,  0, -5), new Vec3i(5,  0, -5),
			new Vec3i(-5,  1, -5), new Vec3i(-4,  1, -5), new Vec3i(-3,  1, -5), new Vec3i(-2,  1, -5), new Vec3i(-1,  1, -5), new Vec3i(0,  1, -5), new Vec3i(1,  1, -5), new Vec3i(2,  1, -5), new Vec3i(3,  1, -5), new Vec3i(4,  1, -5), new Vec3i(5,  1, -5),
			new Vec3i(-5,  2, -5), new Vec3i(-4,  2, -5), new Vec3i(-3,  2, -5), new Vec3i(-2,  2, -5), new Vec3i(-1,  2, -5), new Vec3i(0,  2, -5), new Vec3i(1,  2, -5), new Vec3i(2,  2, -5), new Vec3i(3,  2, -5), new Vec3i(4,  2, -5), new Vec3i(5,  2, -5),
			new Vec3i(-5,  3, -5), new Vec3i(-4,  3, -5), new Vec3i(-3,  3, -5), new Vec3i(-2,  3, -5), new Vec3i(-1,  3, -5), new Vec3i(0,  3, -5), new Vec3i(1,  3, -5), new Vec3i(2,  3, -5), new Vec3i(3,  3, -5), new Vec3i(4,  3, -5), new Vec3i(5,  3, -5),
			new Vec3i(-5,  4, -5), new Vec3i(-4,  4, -5), new Vec3i(-3,  4, -5), new Vec3i(-2,  4, -5), new Vec3i(-1,  4, -5), new Vec3i(0,  4, -5), new Vec3i(1,  4, -5), new Vec3i(2,  4, -5), new Vec3i(3,  4, -5), new Vec3i(4,  4, -5), new Vec3i(5,  4, -5),
			new Vec3i(-5,  5, -5), new Vec3i(-4,  5, -5), new Vec3i(-3,  5, -5), new Vec3i(-2,  5, -5), new Vec3i(-1,  5, -5), new Vec3i(0,  5, -5), new Vec3i(1,  5, -5), new Vec3i(2,  5, -5), new Vec3i(3,  5, -5), new Vec3i(4,  5, -5), new Vec3i(5,  5, -5),
			new Vec3i(-5, -5, -4), new Vec3i(-4, -5, -4), new Vec3i(-3, -5, -4), new Vec3i(-2, -5, -4), new Vec3i(-1, -5, -4), new Vec3i(0, -5, -4), new Vec3i(1, -5, -4), new Vec3i(2, -5, -4), new Vec3i(3, -5, -4), new Vec3i(4, -5, -4), new Vec3i(5, -5, -4),
			new Vec3i(-5, -4, -4), new Vec3i(-4, -4, -4), new Vec3i(-3, -4, -4), new Vec3i(-2, -4, -4), new Vec3i(-1, -4, -4), new Vec3i(0, -4, -4), new Vec3i(1, -4, -4), new Vec3i(2, -4, -4), new Vec3i(3, -4, -4), new Vec3i(4, -4, -4), new Vec3i(5, -4, -4),
			new Vec3i(-5, -3, -4), new Vec3i(-4, -3, -4), new Vec3i(-3, -3, -4), new Vec3i(-2, -3, -4), new Vec3i(-1, -3, -4), new Vec3i(0, -3, -4), new Vec3i(1, -3, -4), new Vec3i(2, -3, -4), new Vec3i(3, -3, -4), new Vec3i(4, -3, -4), new Vec3i(5, -3, -4),
			new Vec3i(-5, -2, -4), new Vec3i(-4, -2, -4), new Vec3i(-3, -2, -4), new Vec3i(-2, -2, -4), new Vec3i(-1, -2, -4), new Vec3i(0, -2, -4), new Vec3i(1, -2, -4), new Vec3i(2, -2, -4), new Vec3i(3, -2, -4), new Vec3i(4, -2, -4), new Vec3i(5, -2, -4),
			new Vec3i(-5, -1, -4), new Vec3i(-4, -1, -4), new Vec3i(-3, -1, -4), new Vec3i(-2, -1, -4), new Vec3i(-1, -1, -4), new Vec3i(0, -1, -4), new Vec3i(1, -1, -4), new Vec3i(2, -1, -4), new Vec3i(3, -1, -4), new Vec3i(4, -1, -4), new Vec3i(5, -1, -4),
			new Vec3i(-5,  0, -4), new Vec3i(-4,  0, -4), new Vec3i(-3,  0, -4), new Vec3i(-2,  0, -4), new Vec3i(-1,  0, -4), new Vec3i(0,  0, -4), new Vec3i(1,  0, -4), new Vec3i(2,  0, -4), new Vec3i(3,  0, -4), new Vec3i(4,  0, -4), new Vec3i(5,  0, -4),
			new Vec3i(-5,  1, -4), new Vec3i(-4,  1, -4), new Vec3i(-3,  1, -4), new Vec3i(-2,  1, -4), new Vec3i(-1,  1, -4), new Vec3i(0,  1, -4), new Vec3i(1,  1, -4), new Vec3i(2,  1, -4), new Vec3i(3,  1, -4), new Vec3i(4,  1, -4), new Vec3i(5,  1, -4),
			new Vec3i(-5,  2, -4), new Vec3i(-4,  2, -4), new Vec3i(-3,  2, -4), new Vec3i(-2,  2, -4), new Vec3i(-1,  2, -4), new Vec3i(0,  2, -4), new Vec3i(1,  2, -4), new Vec3i(2,  2, -4), new Vec3i(3,  2, -4), new Vec3i(4,  2, -4), new Vec3i(5,  2, -4),
			new Vec3i(-5,  3, -4), new Vec3i(-4,  3, -4), new Vec3i(-3,  3, -4), new Vec3i(-2,  3, -4), new Vec3i(-1,  3, -4), new Vec3i(0,  3, -4), new Vec3i(1,  3, -4), new Vec3i(2,  3, -4), new Vec3i(3,  3, -4), new Vec3i(4,  3, -4), new Vec3i(5,  3, -4),
			new Vec3i(-5,  4, -4), new Vec3i(-4,  4, -4), new Vec3i(-3,  4, -4), new Vec3i(-2,  4, -4), new Vec3i(-1,  4, -4), new Vec3i(0,  4, -4), new Vec3i(1,  4, -4), new Vec3i(2,  4, -4), new Vec3i(3,  4, -4), new Vec3i(4,  4, -4), new Vec3i(5,  4, -4),
			new Vec3i(-5,  5, -4), new Vec3i(-4,  5, -4), new Vec3i(-3,  5, -4), new Vec3i(-2,  5, -4), new Vec3i(-1,  5, -4), new Vec3i(0,  5, -4), new Vec3i(1,  5, -4), new Vec3i(2,  5, -4), new Vec3i(3,  5, -4), new Vec3i(4,  5, -4), new Vec3i(5,  5, -4),
			new Vec3i(-5, -5, -3), new Vec3i(-4, -5, -3), new Vec3i(-3, -5, -3), new Vec3i(-2, -5, -3), new Vec3i(-1, -5, -3), new Vec3i(0, -5, -3), new Vec3i(1, -5, -3), new Vec3i(2, -5, -3), new Vec3i(3, -5, -3), new Vec3i(4, -5, -3), new Vec3i(5, -5, -3),
			new Vec3i(-5, -4, -3), new Vec3i(-4, -4, -3), new Vec3i(-3, -4, -3), new Vec3i(-2, -4, -3), new Vec3i(-1, -4, -3), new Vec3i(0, -4, -3), new Vec3i(1, -4, -3), new Vec3i(2, -4, -3), new Vec3i(3, -4, -3), new Vec3i(4, -4, -3), new Vec3i(5, -4, -3),
			new Vec3i(-5, -3, -3), new Vec3i(-4, -3, -3), new Vec3i(-3, -3, -3), new Vec3i(-2, -3, -3), new Vec3i(-1, -3, -3), new Vec3i(0, -3, -3), new Vec3i(1, -3, -3), new Vec3i(2, -3, -3), new Vec3i(3, -3, -3), new Vec3i(4, -3, -3), new Vec3i(5, -3, -3),
			new Vec3i(-5, -2, -3), new Vec3i(-4, -2, -3), new Vec3i(-3, -2, -3), new Vec3i(-2, -2, -3), new Vec3i(-1, -2, -3), new Vec3i(0, -2, -3), new Vec3i(1, -2, -3), new Vec3i(2, -2, -3), new Vec3i(3, -2, -3), new Vec3i(4, -2, -3), new Vec3i(5, -2, -3),
			new Vec3i(-5, -1, -3), new Vec3i(-4, -1, -3), new Vec3i(-3, -1, -3), new Vec3i(-2, -1, -3), new Vec3i(-1, -1, -3), new Vec3i(0, -1, -3), new Vec3i(1, -1, -3), new Vec3i(2, -1, -3), new Vec3i(3, -1, -3), new Vec3i(4, -1, -3), new Vec3i(5, -1, -3),
			new Vec3i(-5,  0, -3), new Vec3i(-4,  0, -3), new Vec3i(-3,  0, -3), new Vec3i(-2,  0, -3), new Vec3i(-1,  0, -3), new Vec3i(0,  0, -3), new Vec3i(1,  0, -3), new Vec3i(2,  0, -3), new Vec3i(3,  0, -3), new Vec3i(4,  0, -3), new Vec3i(5,  0, -3),
			new Vec3i(-5,  1, -3), new Vec3i(-4,  1, -3), new Vec3i(-3,  1, -3), new Vec3i(-2,  1, -3), new Vec3i(-1,  1, -3), new Vec3i(0,  1, -3), new Vec3i(1,  1, -3), new Vec3i(2,  1, -3), new Vec3i(3,  1, -3), new Vec3i(4,  1, -3), new Vec3i(5,  1, -3),
			new Vec3i(-5,  2, -3), new Vec3i(-4,  2, -3), new Vec3i(-3,  2, -3), new Vec3i(-2,  2, -3), new Vec3i(-1,  2, -3), new Vec3i(0,  2, -3), new Vec3i(1,  2, -3), new Vec3i(2,  2, -3), new Vec3i(3,  2, -3), new Vec3i(4,  2, -3), new Vec3i(5,  2, -3),
			new Vec3i(-5,  3, -3), new Vec3i(-4,  3, -3), new Vec3i(-3,  3, -3), new Vec3i(-2,  3, -3), new Vec3i(-1,  3, -3), new Vec3i(0,  3, -3), new Vec3i(1,  3, -3), new Vec3i(2,  3, -3), new Vec3i(3,  3, -3), new Vec3i(4,  3, -3), new Vec3i(5,  3, -3),
			new Vec3i(-5,  4, -3), new Vec3i(-4,  4, -3), new Vec3i(-3,  4, -3), new Vec3i(-2,  4, -3), new Vec3i(-1,  4, -3), new Vec3i(0,  4, -3), new Vec3i(1,  4, -3), new Vec3i(2,  4, -3), new Vec3i(3,  4, -3), new Vec3i(4,  4, -3), new Vec3i(5,  4, -3),
			new Vec3i(-5,  5, -3), new Vec3i(-4,  5, -3), new Vec3i(-3,  5, -3), new Vec3i(-2,  5, -3), new Vec3i(-1,  5, -3), new Vec3i(0,  5, -3), new Vec3i(1,  5, -3), new Vec3i(2,  5, -3), new Vec3i(3,  5, -3), new Vec3i(4,  5, -3), new Vec3i(5,  5, -3),
			new Vec3i(-5, -5, -2), new Vec3i(-4, -5, -2), new Vec3i(-3, -5, -2), new Vec3i(-2, -5, -2), new Vec3i(-1, -5, -2), new Vec3i(0, -5, -2), new Vec3i(1, -5, -2), new Vec3i(2, -5, -2), new Vec3i(3, -5, -2), new Vec3i(4, -5, -2), new Vec3i(5, -5, -2),
			new Vec3i(-5, -4, -2), new Vec3i(-4, -4, -2), new Vec3i(-3, -4, -2), new Vec3i(-2, -4, -2), new Vec3i(-1, -4, -2), new Vec3i(0, -4, -2), new Vec3i(1, -4, -2), new Vec3i(2, -4, -2), new Vec3i(3, -4, -2), new Vec3i(4, -4, -2), new Vec3i(5, -4, -2),
			new Vec3i(-5, -3, -2), new Vec3i(-4, -3, -2), new Vec3i(-3, -3, -2), new Vec3i(-2, -3, -2), new Vec3i(-1, -3, -2), new Vec3i(0, -3, -2), new Vec3i(1, -3, -2), new Vec3i(2, -3, -2), new Vec3i(3, -3, -2), new Vec3i(4, -3, -2), new Vec3i(5, -3, -2),
			new Vec3i(-5, -2, -2), new Vec3i(-4, -2, -2), new Vec3i(-3, -2, -2), new Vec3i(-2, -2, -2), new Vec3i(-1, -2, -2), new Vec3i(0, -2, -2), new Vec3i(1, -2, -2), new Vec3i(2, -2, -2), new Vec3i(3, -2, -2), new Vec3i(4, -2, -2), new Vec3i(5, -2, -2),
			new Vec3i(-5, -1, -2), new Vec3i(-4, -1, -2), new Vec3i(-3, -1, -2), new Vec3i(-2, -1, -2), new Vec3i(-1, -1, -2), new Vec3i(0, -1, -2), new Vec3i(1, -1, -2), new Vec3i(2, -1, -2), new Vec3i(3, -1, -2), new Vec3i(4, -1, -2), new Vec3i(5, -1, -2),
			new Vec3i(-5,  0, -2), new Vec3i(-4,  0, -2), new Vec3i(-3,  0, -2), new Vec3i(-2,  0, -2), new Vec3i(-1,  0, -2), new Vec3i(0,  0, -2), new Vec3i(1,  0, -2), new Vec3i(2,  0, -2), new Vec3i(3,  0, -2), new Vec3i(4,  0, -2), new Vec3i(5,  0, -2),
			new Vec3i(-5,  1, -2), new Vec3i(-4,  1, -2), new Vec3i(-3,  1, -2), new Vec3i(-2,  1, -2), new Vec3i(-1,  1, -2), new Vec3i(0,  1, -2), new Vec3i(1,  1, -2), new Vec3i(2,  1, -2), new Vec3i(3,  1, -2), new Vec3i(4,  1, -2), new Vec3i(5,  1, -2),
			new Vec3i(-5,  2, -2), new Vec3i(-4,  2, -2), new Vec3i(-3,  2, -2), new Vec3i(-2,  2, -2), new Vec3i(-1,  2, -2), new Vec3i(0,  2, -2), new Vec3i(1,  2, -2), new Vec3i(2,  2, -2), new Vec3i(3,  2, -2), new Vec3i(4,  2, -2), new Vec3i(5,  2, -2),
			new Vec3i(-5,  3, -2), new Vec3i(-4,  3, -2), new Vec3i(-3,  3, -2), new Vec3i(-2,  3, -2), new Vec3i(-1,  3, -2), new Vec3i(0,  3, -2), new Vec3i(1,  3, -2), new Vec3i(2,  3, -2), new Vec3i(3,  3, -2), new Vec3i(4,  3, -2), new Vec3i(5,  3, -2),
			new Vec3i(-5,  4, -2), new Vec3i(-4,  4, -2), new Vec3i(-3,  4, -2), new Vec3i(-2,  4, -2), new Vec3i(-1,  4, -2), new Vec3i(0,  4, -2), new Vec3i(1,  4, -2), new Vec3i(2,  4, -2), new Vec3i(3,  4, -2), new Vec3i(4,  4, -2), new Vec3i(5,  4, -2),
			new Vec3i(-5,  5, -2), new Vec3i(-4,  5, -2), new Vec3i(-3,  5, -2), new Vec3i(-2,  5, -2), new Vec3i(-1,  5, -2), new Vec3i(0,  5, -2), new Vec3i(1,  5, -2), new Vec3i(2,  5, -2), new Vec3i(3,  5, -2), new Vec3i(4,  5, -2), new Vec3i(5,  5, -2),
			new Vec3i(-5, -5, -1), new Vec3i(-4, -5, -1), new Vec3i(-3, -5, -1), new Vec3i(-2, -5, -1), new Vec3i(-1, -5, -1), new Vec3i(0, -5, -1), new Vec3i(1, -5, -1), new Vec3i(2, -5, -1), new Vec3i(3, -5, -1), new Vec3i(4, -5, -1), new Vec3i(5, -5, -1),
			new Vec3i(-5, -4, -1), new Vec3i(-4, -4, -1), new Vec3i(-3, -4, -1), new Vec3i(-2, -4, -1), new Vec3i(-1, -4, -1), new Vec3i(0, -4, -1), new Vec3i(1, -4, -1), new Vec3i(2, -4, -1), new Vec3i(3, -4, -1), new Vec3i(4, -4, -1), new Vec3i(5, -4, -1),
			new Vec3i(-5, -3, -1), new Vec3i(-4, -3, -1), new Vec3i(-3, -3, -1), new Vec3i(-2, -3, -1), new Vec3i(-1, -3, -1), new Vec3i(0, -3, -1), new Vec3i(1, -3, -1), new Vec3i(2, -3, -1), new Vec3i(3, -3, -1), new Vec3i(4, -3, -1), new Vec3i(5, -3, -1),
			new Vec3i(-5, -2, -1), new Vec3i(-4, -2, -1), new Vec3i(-3, -2, -1), new Vec3i(-2, -2, -1), new Vec3i(-1, -2, -1), new Vec3i(0, -2, -1), new Vec3i(1, -2, -1), new Vec3i(2, -2, -1), new Vec3i(3, -2, -1), new Vec3i(4, -2, -1), new Vec3i(5, -2, -1),
			new Vec3i(-5, -1, -1), new Vec3i(-4, -1, -1), new Vec3i(-3, -1, -1), new Vec3i(-2, -1, -1), new Vec3i(-1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(1, -1, -1), new Vec3i(2, -1, -1), new Vec3i(3, -1, -1), new Vec3i(4, -1, -1), new Vec3i(5, -1, -1),
			new Vec3i(-5,  0, -1), new Vec3i(-4,  0, -1), new Vec3i(-3,  0, -1), new Vec3i(-2,  0, -1), new Vec3i(-1,  0, -1), new Vec3i(0,  0, -1), new Vec3i(1,  0, -1), new Vec3i(2,  0, -1), new Vec3i(3,  0, -1), new Vec3i(4,  0, -1), new Vec3i(5,  0, -1),
			new Vec3i(-5,  1, -1), new Vec3i(-4,  1, -1), new Vec3i(-3,  1, -1), new Vec3i(-2,  1, -1), new Vec3i(-1,  1, -1), new Vec3i(0,  1, -1), new Vec3i(1,  1, -1), new Vec3i(2,  1, -1), new Vec3i(3,  1, -1), new Vec3i(4,  1, -1), new Vec3i(5,  1, -1),
			new Vec3i(-5,  2, -1), new Vec3i(-4,  2, -1), new Vec3i(-3,  2, -1), new Vec3i(-2,  2, -1), new Vec3i(-1,  2, -1), new Vec3i(0,  2, -1), new Vec3i(1,  2, -1), new Vec3i(2,  2, -1), new Vec3i(3,  2, -1), new Vec3i(4,  2, -1), new Vec3i(5,  2, -1),
			new Vec3i(-5,  3, -1), new Vec3i(-4,  3, -1), new Vec3i(-3,  3, -1), new Vec3i(-2,  3, -1), new Vec3i(-1,  3, -1), new Vec3i(0,  3, -1), new Vec3i(1,  3, -1), new Vec3i(2,  3, -1), new Vec3i(3,  3, -1), new Vec3i(4,  3, -1), new Vec3i(5,  3, -1),
			new Vec3i(-5,  4, -1), new Vec3i(-4,  4, -1), new Vec3i(-3,  4, -1), new Vec3i(-2,  4, -1), new Vec3i(-1,  4, -1), new Vec3i(0,  4, -1), new Vec3i(1,  4, -1), new Vec3i(2,  4, -1), new Vec3i(3,  4, -1), new Vec3i(4,  4, -1), new Vec3i(5,  4, -1),
			new Vec3i(-5,  5, -1), new Vec3i(-4,  5, -1), new Vec3i(-3,  5, -1), new Vec3i(-2,  5, -1), new Vec3i(-1,  5, -1), new Vec3i(0,  5, -1), new Vec3i(1,  5, -1), new Vec3i(2,  5, -1), new Vec3i(3,  5, -1), new Vec3i(4,  5, -1), new Vec3i(5,  5, -1),
			new Vec3i(-5, -5,  0), new Vec3i(-4, -5,  0), new Vec3i(-3, -5,  0), new Vec3i(-2, -5,  0), new Vec3i(-1, -5,  0), new Vec3i(0, -5,  0), new Vec3i(1, -5,  0), new Vec3i(2, -5,  0), new Vec3i(3, -5,  0), new Vec3i(4, -5,  0), new Vec3i(5, -5,  0),
			new Vec3i(-5, -4,  0), new Vec3i(-4, -4,  0), new Vec3i(-3, -4,  0), new Vec3i(-2, -4,  0), new Vec3i(-1, -4,  0), new Vec3i(0, -4,  0), new Vec3i(1, -4,  0), new Vec3i(2, -4,  0), new Vec3i(3, -4,  0), new Vec3i(4, -4,  0), new Vec3i(5, -4,  0),
			new Vec3i(-5, -3,  0), new Vec3i(-4, -3,  0), new Vec3i(-3, -3,  0), new Vec3i(-2, -3,  0), new Vec3i(-1, -3,  0), new Vec3i(0, -3,  0), new Vec3i(1, -3,  0), new Vec3i(2, -3,  0), new Vec3i(3, -3,  0), new Vec3i(4, -3,  0), new Vec3i(5, -3,  0),
			new Vec3i(-5, -2,  0), new Vec3i(-4, -2,  0), new Vec3i(-3, -2,  0), new Vec3i(-2, -2,  0), new Vec3i(-1, -2,  0), new Vec3i(0, -2,  0), new Vec3i(1, -2,  0), new Vec3i(2, -2,  0), new Vec3i(3, -2,  0), new Vec3i(4, -2,  0), new Vec3i(5, -2,  0),
			new Vec3i(-5, -1,  0), new Vec3i(-4, -1,  0), new Vec3i(-3, -1,  0), new Vec3i(-2, -1,  0), new Vec3i(-1, -1,  0), new Vec3i(0, -1,  0), new Vec3i(1, -1,  0), new Vec3i(2, -1,  0), new Vec3i(3, -1,  0), new Vec3i(4, -1,  0), new Vec3i(5, -1,  0),
			new Vec3i(-5,  0,  0), new Vec3i(-4,  0,  0), new Vec3i(-3,  0,  0), new Vec3i(-2,  0,  0), new Vec3i(-1,  0,  0), new Vec3i(0,  0,  0), new Vec3i(1,  0,  0), new Vec3i(2,  0,  0), new Vec3i(3,  0,  0), new Vec3i(4,  0,  0), new Vec3i(5,  0,  0),
			new Vec3i(-5,  1,  0), new Vec3i(-4,  1,  0), new Vec3i(-3,  1,  0), new Vec3i(-2,  1,  0), new Vec3i(-1,  1,  0), new Vec3i(0,  1,  0), new Vec3i(1,  1,  0), new Vec3i(2,  1,  0), new Vec3i(3,  1,  0), new Vec3i(4,  1,  0), new Vec3i(5,  1,  0),
			new Vec3i(-5,  2,  0), new Vec3i(-4,  2,  0), new Vec3i(-3,  2,  0), new Vec3i(-2,  2,  0), new Vec3i(-1,  2,  0), new Vec3i(0,  2,  0), new Vec3i(1,  2,  0), new Vec3i(2,  2,  0), new Vec3i(3,  2,  0), new Vec3i(4,  2,  0), new Vec3i(5,  2,  0),
			new Vec3i(-5,  3,  0), new Vec3i(-4,  3,  0), new Vec3i(-3,  3,  0), new Vec3i(-2,  3,  0), new Vec3i(-1,  3,  0), new Vec3i(0,  3,  0), new Vec3i(1,  3,  0), new Vec3i(2,  3,  0), new Vec3i(3,  3,  0), new Vec3i(4,  3,  0), new Vec3i(5,  3,  0),
			new Vec3i(-5,  4,  0), new Vec3i(-4,  4,  0), new Vec3i(-3,  4,  0), new Vec3i(-2,  4,  0), new Vec3i(-1,  4,  0), new Vec3i(0,  4,  0), new Vec3i(1,  4,  0), new Vec3i(2,  4,  0), new Vec3i(3,  4,  0), new Vec3i(4,  4,  0), new Vec3i(5,  4,  0),
			new Vec3i(-5,  5,  0), new Vec3i(-4,  5,  0), new Vec3i(-3,  5,  0), new Vec3i(-2,  5,  0), new Vec3i(-1,  5,  0), new Vec3i(0,  5,  0), new Vec3i(1,  5,  0), new Vec3i(2,  5,  0), new Vec3i(3,  5,  0), new Vec3i(4,  5,  0), new Vec3i(5,  5,  0),
			new Vec3i(-5, -5,  1), new Vec3i(-4, -5,  1), new Vec3i(-3, -5,  1), new Vec3i(-2, -5,  1), new Vec3i(-1, -5,  1), new Vec3i(0, -5,  1), new Vec3i(1, -5,  1), new Vec3i(2, -5,  1), new Vec3i(3, -5,  1), new Vec3i(4, -5,  1), new Vec3i(5, -5,  1),
			new Vec3i(-5, -4,  1), new Vec3i(-4, -4,  1), new Vec3i(-3, -4,  1), new Vec3i(-2, -4,  1), new Vec3i(-1, -4,  1), new Vec3i(0, -4,  1), new Vec3i(1, -4,  1), new Vec3i(2, -4,  1), new Vec3i(3, -4,  1), new Vec3i(4, -4,  1), new Vec3i(5, -4,  1),
			new Vec3i(-5, -3,  1), new Vec3i(-4, -3,  1), new Vec3i(-3, -3,  1), new Vec3i(-2, -3,  1), new Vec3i(-1, -3,  1), new Vec3i(0, -3,  1), new Vec3i(1, -3,  1), new Vec3i(2, -3,  1), new Vec3i(3, -3,  1), new Vec3i(4, -3,  1), new Vec3i(5, -3,  1),
			new Vec3i(-5, -2,  1), new Vec3i(-4, -2,  1), new Vec3i(-3, -2,  1), new Vec3i(-2, -2,  1), new Vec3i(-1, -2,  1), new Vec3i(0, -2,  1), new Vec3i(1, -2,  1), new Vec3i(2, -2,  1), new Vec3i(3, -2,  1), new Vec3i(4, -2,  1), new Vec3i(5, -2,  1),
			new Vec3i(-5, -1,  1), new Vec3i(-4, -1,  1), new Vec3i(-3, -1,  1), new Vec3i(-2, -1,  1), new Vec3i(-1, -1,  1), new Vec3i(0, -1,  1), new Vec3i(1, -1,  1), new Vec3i(2, -1,  1), new Vec3i(3, -1,  1), new Vec3i(4, -1,  1), new Vec3i(5, -1,  1),
			new Vec3i(-5,  0,  1), new Vec3i(-4,  0,  1), new Vec3i(-3,  0,  1), new Vec3i(-2,  0,  1), new Vec3i(-1,  0,  1), new Vec3i(0,  0,  1), new Vec3i(1,  0,  1), new Vec3i(2,  0,  1), new Vec3i(3,  0,  1), new Vec3i(4,  0,  1), new Vec3i(5,  0,  1),
			new Vec3i(-5,  1,  1), new Vec3i(-4,  1,  1), new Vec3i(-3,  1,  1), new Vec3i(-2,  1,  1), new Vec3i(-1,  1,  1), new Vec3i(0,  1,  1), new Vec3i(1,  1,  1), new Vec3i(2,  1,  1), new Vec3i(3,  1,  1), new Vec3i(4,  1,  1), new Vec3i(5,  1,  1),
			new Vec3i(-5,  2,  1), new Vec3i(-4,  2,  1), new Vec3i(-3,  2,  1), new Vec3i(-2,  2,  1), new Vec3i(-1,  2,  1), new Vec3i(0,  2,  1), new Vec3i(1,  2,  1), new Vec3i(2,  2,  1), new Vec3i(3,  2,  1), new Vec3i(4,  2,  1), new Vec3i(5,  2,  1),
			new Vec3i(-5,  3,  1), new Vec3i(-4,  3,  1), new Vec3i(-3,  3,  1), new Vec3i(-2,  3,  1), new Vec3i(-1,  3,  1), new Vec3i(0,  3,  1), new Vec3i(1,  3,  1), new Vec3i(2,  3,  1), new Vec3i(3,  3,  1), new Vec3i(4,  3,  1), new Vec3i(5,  3,  1),
			new Vec3i(-5,  4,  1), new Vec3i(-4,  4,  1), new Vec3i(-3,  4,  1), new Vec3i(-2,  4,  1), new Vec3i(-1,  4,  1), new Vec3i(0,  4,  1), new Vec3i(1,  4,  1), new Vec3i(2,  4,  1), new Vec3i(3,  4,  1), new Vec3i(4,  4,  1), new Vec3i(5,  4,  1),
			new Vec3i(-5,  5,  1), new Vec3i(-4,  5,  1), new Vec3i(-3,  5,  1), new Vec3i(-2,  5,  1), new Vec3i(-1,  5,  1), new Vec3i(0,  5,  1), new Vec3i(1,  5,  1), new Vec3i(2,  5,  1), new Vec3i(3,  5,  1), new Vec3i(4,  5,  1), new Vec3i(5,  5,  1),
			new Vec3i(-5, -5,  2), new Vec3i(-4, -5,  2), new Vec3i(-3, -5,  2), new Vec3i(-2, -5,  2), new Vec3i(-1, -5,  2), new Vec3i(0, -5,  2), new Vec3i(1, -5,  2), new Vec3i(2, -5,  2), new Vec3i(3, -5,  2), new Vec3i(4, -5,  2), new Vec3i(5, -5,  2),
			new Vec3i(-5, -4,  2), new Vec3i(-4, -4,  2), new Vec3i(-3, -4,  2), new Vec3i(-2, -4,  2), new Vec3i(-1, -4,  2), new Vec3i(0, -4,  2), new Vec3i(1, -4,  2), new Vec3i(2, -4,  2), new Vec3i(3, -4,  2), new Vec3i(4, -4,  2), new Vec3i(5, -4,  2),
			new Vec3i(-5, -3,  2), new Vec3i(-4, -3,  2), new Vec3i(-3, -3,  2), new Vec3i(-2, -3,  2), new Vec3i(-1, -3,  2), new Vec3i(0, -3,  2), new Vec3i(1, -3,  2), new Vec3i(2, -3,  2), new Vec3i(3, -3,  2), new Vec3i(4, -3,  2), new Vec3i(5, -3,  2),
			new Vec3i(-5, -2,  2), new Vec3i(-4, -2,  2), new Vec3i(-3, -2,  2), new Vec3i(-2, -2,  2), new Vec3i(-1, -2,  2), new Vec3i(0, -2,  2), new Vec3i(1, -2,  2), new Vec3i(2, -2,  2), new Vec3i(3, -2,  2), new Vec3i(4, -2,  2), new Vec3i(5, -2,  2),
			new Vec3i(-5, -1,  2), new Vec3i(-4, -1,  2), new Vec3i(-3, -1,  2), new Vec3i(-2, -1,  2), new Vec3i(-1, -1,  2), new Vec3i(0, -1,  2), new Vec3i(1, -1,  2), new Vec3i(2, -1,  2), new Vec3i(3, -1,  2), new Vec3i(4, -1,  2), new Vec3i(5, -1,  2),
			new Vec3i(-5,  0,  2), new Vec3i(-4,  0,  2), new Vec3i(-3,  0,  2), new Vec3i(-2,  0,  2), new Vec3i(-1,  0,  2), new Vec3i(0,  0,  2), new Vec3i(1,  0,  2), new Vec3i(2,  0,  2), new Vec3i(3,  0,  2), new Vec3i(4,  0,  2), new Vec3i(5,  0,  2),
			new Vec3i(-5,  1,  2), new Vec3i(-4,  1,  2), new Vec3i(-3,  1,  2), new Vec3i(-2,  1,  2), new Vec3i(-1,  1,  2), new Vec3i(0,  1,  2), new Vec3i(1,  1,  2), new Vec3i(2,  1,  2), new Vec3i(3,  1,  2), new Vec3i(4,  1,  2), new Vec3i(5,  1,  2),
			new Vec3i(-5,  2,  2), new Vec3i(-4,  2,  2), new Vec3i(-3,  2,  2), new Vec3i(-2,  2,  2), new Vec3i(-1,  2,  2), new Vec3i(0,  2,  2), new Vec3i(1,  2,  2), new Vec3i(2,  2,  2), new Vec3i(3,  2,  2), new Vec3i(4,  2,  2), new Vec3i(5,  2,  2),
			new Vec3i(-5,  3,  2), new Vec3i(-4,  3,  2), new Vec3i(-3,  3,  2), new Vec3i(-2,  3,  2), new Vec3i(-1,  3,  2), new Vec3i(0,  3,  2), new Vec3i(1,  3,  2), new Vec3i(2,  3,  2), new Vec3i(3,  3,  2), new Vec3i(4,  3,  2), new Vec3i(5,  3,  2),
			new Vec3i(-5,  4,  2), new Vec3i(-4,  4,  2), new Vec3i(-3,  4,  2), new Vec3i(-2,  4,  2), new Vec3i(-1,  4,  2), new Vec3i(0,  4,  2), new Vec3i(1,  4,  2), new Vec3i(2,  4,  2), new Vec3i(3,  4,  2), new Vec3i(4,  4,  2), new Vec3i(5,  4,  2),
			new Vec3i(-5,  5,  2), new Vec3i(-4,  5,  2), new Vec3i(-3,  5,  2), new Vec3i(-2,  5,  2), new Vec3i(-1,  5,  2), new Vec3i(0,  5,  2), new Vec3i(1,  5,  2), new Vec3i(2,  5,  2), new Vec3i(3,  5,  2), new Vec3i(4,  5,  2), new Vec3i(5,  5,  2),
			new Vec3i(-5, -5,  3), new Vec3i(-4, -5,  3), new Vec3i(-3, -5,  3), new Vec3i(-2, -5,  3), new Vec3i(-1, -5,  3), new Vec3i(0, -5,  3), new Vec3i(1, -5,  3), new Vec3i(2, -5,  3), new Vec3i(3, -5,  3), new Vec3i(4, -5,  3), new Vec3i(5, -5,  3),
			new Vec3i(-5, -4,  3), new Vec3i(-4, -4,  3), new Vec3i(-3, -4,  3), new Vec3i(-2, -4,  3), new Vec3i(-1, -4,  3), new Vec3i(0, -4,  3), new Vec3i(1, -4,  3), new Vec3i(2, -4,  3), new Vec3i(3, -4,  3), new Vec3i(4, -4,  3), new Vec3i(5, -4,  3),
			new Vec3i(-5, -3,  3), new Vec3i(-4, -3,  3), new Vec3i(-3, -3,  3), new Vec3i(-2, -3,  3), new Vec3i(-1, -3,  3), new Vec3i(0, -3,  3), new Vec3i(1, -3,  3), new Vec3i(2, -3,  3), new Vec3i(3, -3,  3), new Vec3i(4, -3,  3), new Vec3i(5, -3,  3),
			new Vec3i(-5, -2,  3), new Vec3i(-4, -2,  3), new Vec3i(-3, -2,  3), new Vec3i(-2, -2,  3), new Vec3i(-1, -2,  3), new Vec3i(0, -2,  3), new Vec3i(1, -2,  3), new Vec3i(2, -2,  3), new Vec3i(3, -2,  3), new Vec3i(4, -2,  3), new Vec3i(5, -2,  3),
			new Vec3i(-5, -1,  3), new Vec3i(-4, -1,  3), new Vec3i(-3, -1,  3), new Vec3i(-2, -1,  3), new Vec3i(-1, -1,  3), new Vec3i(0, -1,  3), new Vec3i(1, -1,  3), new Vec3i(2, -1,  3), new Vec3i(3, -1,  3), new Vec3i(4, -1,  3), new Vec3i(5, -1,  3),
			new Vec3i(-5,  0,  3), new Vec3i(-4,  0,  3), new Vec3i(-3,  0,  3), new Vec3i(-2,  0,  3), new Vec3i(-1,  0,  3), new Vec3i(0,  0,  3), new Vec3i(1,  0,  3), new Vec3i(2,  0,  3), new Vec3i(3,  0,  3), new Vec3i(4,  0,  3), new Vec3i(5,  0,  3),
			new Vec3i(-5,  1,  3), new Vec3i(-4,  1,  3), new Vec3i(-3,  1,  3), new Vec3i(-2,  1,  3), new Vec3i(-1,  1,  3), new Vec3i(0,  1,  3), new Vec3i(1,  1,  3), new Vec3i(2,  1,  3), new Vec3i(3,  1,  3), new Vec3i(4,  1,  3), new Vec3i(5,  1,  3),
			new Vec3i(-5,  2,  3), new Vec3i(-4,  2,  3), new Vec3i(-3,  2,  3), new Vec3i(-2,  2,  3), new Vec3i(-1,  2,  3), new Vec3i(0,  2,  3), new Vec3i(1,  2,  3), new Vec3i(2,  2,  3), new Vec3i(3,  2,  3), new Vec3i(4,  2,  3), new Vec3i(5,  2,  3),
			new Vec3i(-5,  3,  3), new Vec3i(-4,  3,  3), new Vec3i(-3,  3,  3), new Vec3i(-2,  3,  3), new Vec3i(-1,  3,  3), new Vec3i(0,  3,  3), new Vec3i(1,  3,  3), new Vec3i(2,  3,  3), new Vec3i(3,  3,  3), new Vec3i(4,  3,  3), new Vec3i(5,  3,  3),
			new Vec3i(-5,  4,  3), new Vec3i(-4,  4,  3), new Vec3i(-3,  4,  3), new Vec3i(-2,  4,  3), new Vec3i(-1,  4,  3), new Vec3i(0,  4,  3), new Vec3i(1,  4,  3), new Vec3i(2,  4,  3), new Vec3i(3,  4,  3), new Vec3i(4,  4,  3), new Vec3i(5,  4,  3),
			new Vec3i(-5,  5,  3), new Vec3i(-4,  5,  3), new Vec3i(-3,  5,  3), new Vec3i(-2,  5,  3), new Vec3i(-1,  5,  3), new Vec3i(0,  5,  3), new Vec3i(1,  5,  3), new Vec3i(2,  5,  3), new Vec3i(3,  5,  3), new Vec3i(4,  5,  3), new Vec3i(5,  5,  3),
			new Vec3i(-5, -5,  4), new Vec3i(-4, -5,  4), new Vec3i(-3, -5,  4), new Vec3i(-2, -5,  4), new Vec3i(-1, -5,  4), new Vec3i(0, -5,  4), new Vec3i(1, -5,  4), new Vec3i(2, -5,  4), new Vec3i(3, -5,  4), new Vec3i(4, -5,  4), new Vec3i(5, -5,  4),
			new Vec3i(-5, -4,  4), new Vec3i(-4, -4,  4), new Vec3i(-3, -4,  4), new Vec3i(-2, -4,  4), new Vec3i(-1, -4,  4), new Vec3i(0, -4,  4), new Vec3i(1, -4,  4), new Vec3i(2, -4,  4), new Vec3i(3, -4,  4), new Vec3i(4, -4,  4), new Vec3i(5, -4,  4),
			new Vec3i(-5, -3,  4), new Vec3i(-4, -3,  4), new Vec3i(-3, -3,  4), new Vec3i(-2, -3,  4), new Vec3i(-1, -3,  4), new Vec3i(0, -3,  4), new Vec3i(1, -3,  4), new Vec3i(2, -3,  4), new Vec3i(3, -3,  4), new Vec3i(4, -3,  4), new Vec3i(5, -3,  4),
			new Vec3i(-5, -2,  4), new Vec3i(-4, -2,  4), new Vec3i(-3, -2,  4), new Vec3i(-2, -2,  4), new Vec3i(-1, -2,  4), new Vec3i(0, -2,  4), new Vec3i(1, -2,  4), new Vec3i(2, -2,  4), new Vec3i(3, -2,  4), new Vec3i(4, -2,  4), new Vec3i(5, -2,  4),
			new Vec3i(-5, -1,  4), new Vec3i(-4, -1,  4), new Vec3i(-3, -1,  4), new Vec3i(-2, -1,  4), new Vec3i(-1, -1,  4), new Vec3i(0, -1,  4), new Vec3i(1, -1,  4), new Vec3i(2, -1,  4), new Vec3i(3, -1,  4), new Vec3i(4, -1,  4), new Vec3i(5, -1,  4),
			new Vec3i(-5,  0,  4), new Vec3i(-4,  0,  4), new Vec3i(-3,  0,  4), new Vec3i(-2,  0,  4), new Vec3i(-1,  0,  4), new Vec3i(0,  0,  4), new Vec3i(1,  0,  4), new Vec3i(2,  0,  4), new Vec3i(3,  0,  4), new Vec3i(4,  0,  4), new Vec3i(5,  0,  4),
			new Vec3i(-5,  1,  4), new Vec3i(-4,  1,  4), new Vec3i(-3,  1,  4), new Vec3i(-2,  1,  4), new Vec3i(-1,  1,  4), new Vec3i(0,  1,  4), new Vec3i(1,  1,  4), new Vec3i(2,  1,  4), new Vec3i(3,  1,  4), new Vec3i(4,  1,  4), new Vec3i(5,  1,  4),
			new Vec3i(-5,  2,  4), new Vec3i(-4,  2,  4), new Vec3i(-3,  2,  4), new Vec3i(-2,  2,  4), new Vec3i(-1,  2,  4), new Vec3i(0,  2,  4), new Vec3i(1,  2,  4), new Vec3i(2,  2,  4), new Vec3i(3,  2,  4), new Vec3i(4,  2,  4), new Vec3i(5,  2,  4),
			new Vec3i(-5,  3,  4), new Vec3i(-4,  3,  4), new Vec3i(-3,  3,  4), new Vec3i(-2,  3,  4), new Vec3i(-1,  3,  4), new Vec3i(0,  3,  4), new Vec3i(1,  3,  4), new Vec3i(2,  3,  4), new Vec3i(3,  3,  4), new Vec3i(4,  3,  4), new Vec3i(5,  3,  4),
			new Vec3i(-5,  4,  4), new Vec3i(-4,  4,  4), new Vec3i(-3,  4,  4), new Vec3i(-2,  4,  4), new Vec3i(-1,  4,  4), new Vec3i(0,  4,  4), new Vec3i(1,  4,  4), new Vec3i(2,  4,  4), new Vec3i(3,  4,  4), new Vec3i(4,  4,  4), new Vec3i(5,  4,  4),
			new Vec3i(-5,  5,  4), new Vec3i(-4,  5,  4), new Vec3i(-3,  5,  4), new Vec3i(-2,  5,  4), new Vec3i(-1,  5,  4), new Vec3i(0,  5,  4), new Vec3i(1,  5,  4), new Vec3i(2,  5,  4), new Vec3i(3,  5,  4), new Vec3i(4,  5,  4), new Vec3i(5,  5,  4),
			new Vec3i(-5, -5,  5), new Vec3i(-4, -5,  5), new Vec3i(-3, -5,  5), new Vec3i(-2, -5,  5), new Vec3i(-1, -5,  5), new Vec3i(0, -5,  5), new Vec3i(1, -5,  5), new Vec3i(2, -5,  5), new Vec3i(3, -5,  5), new Vec3i(4, -5,  5), new Vec3i(5, -5,  5),
			new Vec3i(-5, -4,  5), new Vec3i(-4, -4,  5), new Vec3i(-3, -4,  5), new Vec3i(-2, -4,  5), new Vec3i(-1, -4,  5), new Vec3i(0, -4,  5), new Vec3i(1, -4,  5), new Vec3i(2, -4,  5), new Vec3i(3, -4,  5), new Vec3i(4, -4,  5), new Vec3i(5, -4,  5),
			new Vec3i(-5, -3,  5), new Vec3i(-4, -3,  5), new Vec3i(-3, -3,  5), new Vec3i(-2, -3,  5), new Vec3i(-1, -3,  5), new Vec3i(0, -3,  5), new Vec3i(1, -3,  5), new Vec3i(2, -3,  5), new Vec3i(3, -3,  5), new Vec3i(4, -3,  5), new Vec3i(5, -3,  5),
			new Vec3i(-5, -2,  5), new Vec3i(-4, -2,  5), new Vec3i(-3, -2,  5), new Vec3i(-2, -2,  5), new Vec3i(-1, -2,  5), new Vec3i(0, -2,  5), new Vec3i(1, -2,  5), new Vec3i(2, -2,  5), new Vec3i(3, -2,  5), new Vec3i(4, -2,  5), new Vec3i(5, -2,  5),
			new Vec3i(-5, -1,  5), new Vec3i(-4, -1,  5), new Vec3i(-3, -1,  5), new Vec3i(-2, -1,  5), new Vec3i(-1, -1,  5), new Vec3i(0, -1,  5), new Vec3i(1, -1,  5), new Vec3i(2, -1,  5), new Vec3i(3, -1,  5), new Vec3i(4, -1,  5), new Vec3i(5, -1,  5),
			new Vec3i(-5,  0,  5), new Vec3i(-4,  0,  5), new Vec3i(-3,  0,  5), new Vec3i(-2,  0,  5), new Vec3i(-1,  0,  5), new Vec3i(0,  0,  5), new Vec3i(1,  0,  5), new Vec3i(2,  0,  5), new Vec3i(3,  0,  5), new Vec3i(4,  0,  5), new Vec3i(5,  0,  5),
			new Vec3i(-5,  1,  5), new Vec3i(-4,  1,  5), new Vec3i(-3,  1,  5), new Vec3i(-2,  1,  5), new Vec3i(-1,  1,  5), new Vec3i(0,  1,  5), new Vec3i(1,  1,  5), new Vec3i(2,  1,  5), new Vec3i(3,  1,  5), new Vec3i(4,  1,  5), new Vec3i(5,  1,  5),
			new Vec3i(-5,  2,  5), new Vec3i(-4,  2,  5), new Vec3i(-3,  2,  5), new Vec3i(-2,  2,  5), new Vec3i(-1,  2,  5), new Vec3i(0,  2,  5), new Vec3i(1,  2,  5), new Vec3i(2,  2,  5), new Vec3i(3,  2,  5), new Vec3i(4,  2,  5), new Vec3i(5,  2,  5),
			new Vec3i(-5,  3,  5), new Vec3i(-4,  3,  5), new Vec3i(-3,  3,  5), new Vec3i(-2,  3,  5), new Vec3i(-1,  3,  5), new Vec3i(0,  3,  5), new Vec3i(1,  3,  5), new Vec3i(2,  3,  5), new Vec3i(3,  3,  5), new Vec3i(4,  3,  5), new Vec3i(5,  3,  5),
			new Vec3i(-5,  4,  5), new Vec3i(-4,  4,  5), new Vec3i(-3,  4,  5), new Vec3i(-2,  4,  5), new Vec3i(-1,  4,  5), new Vec3i(0,  4,  5), new Vec3i(1,  4,  5), new Vec3i(2,  4,  5), new Vec3i(3,  4,  5), new Vec3i(4,  4,  5), new Vec3i(5,  4,  5),
			new Vec3i(-5,  5,  5), new Vec3i(-4,  5,  5), new Vec3i(-3,  5,  5), new Vec3i(-2,  5,  5), new Vec3i(-1,  5,  5), new Vec3i(0,  5,  5), new Vec3i(1,  5,  5), new Vec3i(2,  5,  5), new Vec3i(3,  5,  5), new Vec3i(4,  5,  5), new Vec3i(5,  5,  5)
	};

	public static final Vec3i[] SearchBlock7to7 =new Vec3i[] {
			new Vec3i(-3, -3, -3), new Vec3i(-2, -3, -3), new Vec3i(-1, -3, -3), new Vec3i(0, -3, -3), new Vec3i(1, -3, -3), new Vec3i(2, -3, -3), new Vec3i(3, -3, -3),
			new Vec3i(-3, -2, -3), new Vec3i(-2, -2, -3), new Vec3i(-1, -2, -3), new Vec3i(0, -2, -3), new Vec3i(1, -2, -3), new Vec3i(2, -2, -3), new Vec3i(3, -2, -3),
			new Vec3i(-3, -1, -3), new Vec3i(-2, -1, -3), new Vec3i(-1, -1, -3), new Vec3i(0, -1, -3), new Vec3i(1, -1, -3), new Vec3i(2, -1, -3), new Vec3i(3, -1, -3),
			new Vec3i(-3,  0, -3), new Vec3i(-2,  0, -3), new Vec3i(-1,  0, -3), new Vec3i(0,  0, -3), new Vec3i(1,  0, -3), new Vec3i(2,  0, -3), new Vec3i(3,  0, -3),
			new Vec3i(-3,  1, -3), new Vec3i(-2,  1, -3), new Vec3i(-1,  1, -3), new Vec3i(0,  1, -3), new Vec3i(1,  1, -3), new Vec3i(2,  1, -3), new Vec3i(3,  1, -3),
			new Vec3i(-3,  2, -3), new Vec3i(-2,  2, -3), new Vec3i(-1,  2, -3), new Vec3i(0,  2, -3), new Vec3i(1,  2, -3), new Vec3i(2,  2, -3), new Vec3i(3,  2, -3),
			new Vec3i(-3,  3, -3), new Vec3i(-2,  3, -3), new Vec3i(-1,  3, -3), new Vec3i(0,  3, -3), new Vec3i(1,  3, -3), new Vec3i(2,  3, -3), new Vec3i(3,  3, -3),
			new Vec3i(-3, -3, -2), new Vec3i(-2, -3, -2), new Vec3i(-1, -3, -2), new Vec3i(0, -3, -2), new Vec3i(1, -3, -2), new Vec3i(2, -3, -2), new Vec3i(3, -3, -2),
			new Vec3i(-3, -2, -2), new Vec3i(-2, -2, -2), new Vec3i(-1, -2, -2), new Vec3i(0, -2, -2), new Vec3i(1, -2, -2), new Vec3i(2, -2, -2), new Vec3i(3, -2, -2),
			new Vec3i(-3, -1, -2), new Vec3i(-2, -1, -2), new Vec3i(-1, -1, -2), new Vec3i(0, -1, -2), new Vec3i(1, -1, -2), new Vec3i(2, -1, -2), new Vec3i(3, -1, -2),
			new Vec3i(-3,  0, -2), new Vec3i(-2,  0, -2), new Vec3i(-1,  0, -2), new Vec3i(0,  0, -2), new Vec3i(1,  0, -2), new Vec3i(2,  0, -2), new Vec3i(3,  0, -2),
			new Vec3i(-3,  1, -2), new Vec3i(-2,  1, -2), new Vec3i(-1,  1, -2), new Vec3i(0,  1, -2), new Vec3i(1,  1, -2), new Vec3i(2,  1, -2), new Vec3i(3,  1, -2),
			new Vec3i(-3,  2, -2), new Vec3i(-2,  2, -2), new Vec3i(-1,  2, -2), new Vec3i(0,  2, -2), new Vec3i(1,  2, -2), new Vec3i(2,  2, -2), new Vec3i(3,  2, -2),
			new Vec3i(-3,  3, -2), new Vec3i(-2,  3, -2), new Vec3i(-1,  3, -2), new Vec3i(0,  3, -2), new Vec3i(1,  3, -2), new Vec3i(2,  3, -2), new Vec3i(3,  3, -2),
			new Vec3i(-3, -3, -1), new Vec3i(-2, -3, -1), new Vec3i(-1, -3, -1), new Vec3i(0, -3, -1), new Vec3i(1, -3, -1), new Vec3i(2, -3, -1), new Vec3i(3, -3, -1),
			new Vec3i(-3, -2, -1), new Vec3i(-2, -2, -1), new Vec3i(-1, -2, -1), new Vec3i(0, -2, -1), new Vec3i(1, -2, -1), new Vec3i(2, -2, -1), new Vec3i(3, -2, -1),
			new Vec3i(-3, -1, -1), new Vec3i(-2, -1, -1), new Vec3i(-1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(1, -1, -1), new Vec3i(2, -1, -1), new Vec3i(3, -1, -1),
			new Vec3i(-3,  0, -1), new Vec3i(-2,  0, -1), new Vec3i(-1,  0, -1), new Vec3i(0,  0, -1), new Vec3i(1,  0, -1), new Vec3i(2,  0, -1), new Vec3i(3,  0, -1),
			new Vec3i(-3,  1, -1), new Vec3i(-2,  1, -1), new Vec3i(-1,  1, -1), new Vec3i(0,  1, -1), new Vec3i(1,  1, -1), new Vec3i(2,  1, -1), new Vec3i(3,  1, -1),
			new Vec3i(-3,  2, -1), new Vec3i(-2,  2, -1), new Vec3i(-1,  2, -1), new Vec3i(0,  2, -1), new Vec3i(1,  2, -1), new Vec3i(2,  2, -1), new Vec3i(3,  2, -1),
			new Vec3i(-3,  3, -1), new Vec3i(-2,  3, -1), new Vec3i(-1,  3, -1), new Vec3i(0,  3, -1), new Vec3i(1,  3, -1), new Vec3i(2,  3, -1), new Vec3i(3,  3, -1),
			new Vec3i(-3, -3,  0), new Vec3i(-2, -3,  0), new Vec3i(-1, -3,  0), new Vec3i(0, -3,  0), new Vec3i(1, -3,  0), new Vec3i(2, -3,  0), new Vec3i(3, -3,  0),
			new Vec3i(-3, -2,  0), new Vec3i(-2, -2,  0), new Vec3i(-1, -2,  0), new Vec3i(0, -2,  0), new Vec3i(1, -2,  0), new Vec3i(2, -2,  0), new Vec3i(3, -2,  0),
			new Vec3i(-3, -1,  0), new Vec3i(-2, -1,  0), new Vec3i(-1, -1,  0), new Vec3i(0, -1,  0), new Vec3i(1, -1,  0), new Vec3i(2, -1,  0), new Vec3i(3, -1,  0),
			new Vec3i(-3,  0,  0), new Vec3i(-2,  0,  0), new Vec3i(-1,  0,  0), new Vec3i(0,  0,  0), new Vec3i(1,  0,  0), new Vec3i(2,  0,  0), new Vec3i(3,  0,  0),
			new Vec3i(-3,  1,  0), new Vec3i(-2,  1,  0), new Vec3i(-1,  1,  0), new Vec3i(0,  1,  0), new Vec3i(1,  1,  0), new Vec3i(2,  1,  0), new Vec3i(3,  1,  0),
			new Vec3i(-3,  2,  0), new Vec3i(-2,  2,  0), new Vec3i(-1,  2,  0), new Vec3i(0,  2,  0), new Vec3i(1,  2,  0), new Vec3i(2,  2,  0), new Vec3i(3,  2,  0),
			new Vec3i(-3,  3,  0), new Vec3i(-2,  3,  0), new Vec3i(-1,  3,  0), new Vec3i(0,  3,  0), new Vec3i(1,  3,  0), new Vec3i(2,  3,  0), new Vec3i(3,  3,  0),
			new Vec3i(-3, -3,  1), new Vec3i(-2, -3,  1), new Vec3i(-1, -3,  1), new Vec3i(0, -3,  1), new Vec3i(1, -3,  1), new Vec3i(2, -3,  1), new Vec3i(3, -3,  1),
			new Vec3i(-3, -2,  1), new Vec3i(-2, -2,  1), new Vec3i(-1, -2,  1), new Vec3i(0, -2,  1), new Vec3i(1, -2,  1), new Vec3i(2, -2,  1), new Vec3i(3, -2,  1),
			new Vec3i(-3, -1,  1), new Vec3i(-2, -1,  1), new Vec3i(-1, -1,  1), new Vec3i(0, -1,  1), new Vec3i(1, -1,  1), new Vec3i(2, -1,  1), new Vec3i(3, -1,  1),
			new Vec3i(-3,  0,  1), new Vec3i(-2,  0,  1), new Vec3i(-1,  0,  1), new Vec3i(0,  0,  1), new Vec3i(1,  0,  1), new Vec3i(2,  0,  1), new Vec3i(3,  0,  1),
			new Vec3i(-3,  1,  1), new Vec3i(-2,  1,  1), new Vec3i(-1,  1,  1), new Vec3i(0,  1,  1), new Vec3i(1,  1,  1), new Vec3i(2,  1,  1), new Vec3i(3,  1,  1),
			new Vec3i(-3,  2,  1), new Vec3i(-2,  2,  1), new Vec3i(-1,  2,  1), new Vec3i(0,  2,  1), new Vec3i(1,  2,  1), new Vec3i(2,  2,  1), new Vec3i(3,  2,  1),
			new Vec3i(-3,  3,  1), new Vec3i(-2,  3,  1), new Vec3i(-1,  3,  1), new Vec3i(0,  3,  1), new Vec3i(1,  3,  1), new Vec3i(2,  3,  1), new Vec3i(3,  3,  1),
			new Vec3i(-3, -3,  2), new Vec3i(-2, -3,  2), new Vec3i(-1, -3,  2), new Vec3i(0, -3,  2), new Vec3i(1, -3,  2), new Vec3i(2, -3,  2), new Vec3i(3, -3,  2),
			new Vec3i(-3, -2,  2), new Vec3i(-2, -2,  2), new Vec3i(-1, -2,  2), new Vec3i(0, -2,  2), new Vec3i(1, -2,  2), new Vec3i(2, -2,  2), new Vec3i(3, -2,  2),
			new Vec3i(-3, -1,  2), new Vec3i(-2, -1,  2), new Vec3i(-1, -1,  2), new Vec3i(0, -1,  2), new Vec3i(1, -1,  2), new Vec3i(2, -1,  2), new Vec3i(3, -1,  2),
			new Vec3i(-3,  0,  2), new Vec3i(-2,  0,  2), new Vec3i(-1,  0,  2), new Vec3i(0,  0,  2), new Vec3i(1,  0,  2), new Vec3i(2,  0,  2), new Vec3i(3,  0,  2),
			new Vec3i(-3,  1,  2), new Vec3i(-2,  1,  2), new Vec3i(-1,  1,  2), new Vec3i(0,  1,  2), new Vec3i(1,  1,  2), new Vec3i(2,  1,  2), new Vec3i(3,  1,  2),
			new Vec3i(-3,  2,  2), new Vec3i(-2,  2,  2), new Vec3i(-1,  2,  2), new Vec3i(0,  2,  2), new Vec3i(1,  2,  2), new Vec3i(2,  2,  2), new Vec3i(3,  2,  2),
			new Vec3i(-3,  3,  2), new Vec3i(-2,  3,  2), new Vec3i(-1,  3,  2), new Vec3i(0,  3,  2), new Vec3i(1,  3,  2), new Vec3i(2,  3,  2), new Vec3i(3,  3,  2),
			new Vec3i(-3, -3,  3), new Vec3i(-2, -3,  3), new Vec3i(-1, -3,  3), new Vec3i(0, -3,  3), new Vec3i(1, -3,  3), new Vec3i(2, -3,  3), new Vec3i(3, -3,  3),
			new Vec3i(-3, -2,  3), new Vec3i(-2, -2,  3), new Vec3i(-1, -2,  3), new Vec3i(0, -2,  3), new Vec3i(1, -2,  3), new Vec3i(2, -2,  3), new Vec3i(3, -2,  3),
			new Vec3i(-3, -1,  3), new Vec3i(-2, -1,  3), new Vec3i(-1, -1,  3), new Vec3i(0, -1,  3), new Vec3i(1, -1,  3), new Vec3i(2, -1,  3), new Vec3i(3, -1,  3),
			new Vec3i(-3,  0,  3), new Vec3i(-2,  0,  3), new Vec3i(-1,  0,  3), new Vec3i(0,  0,  3), new Vec3i(1,  0,  3), new Vec3i(2,  0,  3), new Vec3i(3,  0,  3),
			new Vec3i(-3,  1,  3), new Vec3i(-2,  1,  3), new Vec3i(-1,  1,  3), new Vec3i(0,  1,  3), new Vec3i(1,  1,  3), new Vec3i(2,  1,  3), new Vec3i(3,  1,  3),
			new Vec3i(-3,  2,  3), new Vec3i(-2,  2,  3), new Vec3i(-1,  2,  3), new Vec3i(0,  2,  3), new Vec3i(1,  2,  3), new Vec3i(2,  2,  3), new Vec3i(3,  2,  3),
			new Vec3i(-3,  3,  3), new Vec3i(-2,  3,  3), new Vec3i(-1,  3,  3), new Vec3i(0,  3,  3), new Vec3i(1,  3,  3), new Vec3i(2,  3,  3), new Vec3i(3,  3,  3),
	};


	public static final Vec3i[] SearchBlock5to5 =new Vec3i[] {
			new Vec3i(-2, -2, -2), new Vec3i(-1, -2, -2), new Vec3i(0, -2, -2), new Vec3i(1, -2, -2), new Vec3i(2, -2, -2),
			new Vec3i(-2, -1, -2), new Vec3i(-1, -1, -2), new Vec3i(0, -1, -2), new Vec3i(1, -1, -2), new Vec3i(2, -1, -2),
			new Vec3i(-2,  0, -2), new Vec3i(-1,  0, -2), new Vec3i(0,  0, -2), new Vec3i(1,  0, -2), new Vec3i(2,  0, -2),
			new Vec3i(-2,  1, -2), new Vec3i(-1,  1, -2), new Vec3i(0,  1, -2), new Vec3i(1,  1, -2), new Vec3i(2,  1, -2),
			new Vec3i(-2,  2, -2), new Vec3i(-1,  2, -2), new Vec3i(0,  2, -2), new Vec3i(1,  2, -2), new Vec3i(2,  2, -2),
			new Vec3i(-2, -2, -1), new Vec3i(-1, -2, -1), new Vec3i(0, -2, -1), new Vec3i(1, -2, -1), new Vec3i(2, -2, -1),
			new Vec3i(-2, -1, -1), new Vec3i(-1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(1, -1, -1), new Vec3i(2, -1, -1),
			new Vec3i(-2,  0, -1), new Vec3i(-1,  0, -1), new Vec3i(0,  0, -1), new Vec3i(1,  0, -1), new Vec3i(2,  0, -1),
			new Vec3i(-2,  1, -1), new Vec3i(-1,  1, -1), new Vec3i(0,  1, -1), new Vec3i(1,  1, -1), new Vec3i(2,  1, -1),
			new Vec3i(-2,  2, -1), new Vec3i(-1,  2, -1), new Vec3i(0,  2, -1), new Vec3i(1,  2, -1), new Vec3i(2,  2, -1),
			new Vec3i(-2, -2,  0), new Vec3i(-1, -2,  0), new Vec3i(0, -2,  0), new Vec3i(1, -2,  0), new Vec3i(2, -2,  0),
			new Vec3i(-2, -1,  0), new Vec3i(-1, -1,  0), new Vec3i(0, -1,  0), new Vec3i(1, -1,  0), new Vec3i(2, -1,  0),
			new Vec3i(-2,  0,  0), new Vec3i(-1,  0,  0), new Vec3i(0,  0,  0), new Vec3i(1,  0,  0), new Vec3i(2,  0,  0),
			new Vec3i(-2,  1,  0), new Vec3i(-1,  1,  0), new Vec3i(0,  1,  0), new Vec3i(1,  1,  0), new Vec3i(2,  1,  0),
			new Vec3i(-2,  2,  0), new Vec3i(-1,  2,  0), new Vec3i(0,  2,  0), new Vec3i(1,  2,  0), new Vec3i(2,  2,  0),
			new Vec3i(-2, -2,  1), new Vec3i(-1, -2,  1), new Vec3i(0, -2,  1), new Vec3i(1, -2,  1), new Vec3i(2, -2,  1),
			new Vec3i(-2, -1,  1), new Vec3i(-1, -1,  1), new Vec3i(0, -1,  1), new Vec3i(1, -1,  1), new Vec3i(2, -1,  1),
			new Vec3i(-2,  0,  1), new Vec3i(-1,  0,  1), new Vec3i(0,  0,  1), new Vec3i(1,  0,  1), new Vec3i(2,  0,  1),
			new Vec3i(-2,  1,  1), new Vec3i(-1,  1,  1), new Vec3i(0,  1,  1), new Vec3i(1,  1,  1), new Vec3i(2,  1,  1),
			new Vec3i(-2,  2,  1), new Vec3i(-1,  2,  1), new Vec3i(0,  2,  1), new Vec3i(1,  2,  1), new Vec3i(2,  2,  1),
			new Vec3i(-2, -2,  2), new Vec3i(-1, -2,  2), new Vec3i(0, -2,  2), new Vec3i(1, -2,  2), new Vec3i(2, -2,  2),
			new Vec3i(-2, -1,  2), new Vec3i(-1, -1,  2), new Vec3i(0, -1,  2), new Vec3i(1, -1,  2), new Vec3i(2, -1,  2),
			new Vec3i(-2,  0,  2), new Vec3i(-1,  0,  2), new Vec3i(0,  0,  2), new Vec3i(1,  0,  2), new Vec3i(2,  0,  2),
			new Vec3i(-2,  1,  2), new Vec3i(-1,  1,  2), new Vec3i(0,  1,  2), new Vec3i(1,  1,  2), new Vec3i(2,  1,  2),
			new Vec3i(-2,  2,  2), new Vec3i(-1,  2,  2), new Vec3i(0,  2,  2), new Vec3i(1,  2,  2), new Vec3i(2,  2,  2),
	};

	public static final Vec3i[] SearchBlock3to3 =new Vec3i[] {
			new Vec3i(-1, -1, -2), new Vec3i(0, -1, -2), new Vec3i(1, -1, -2),
			new Vec3i(-1,  0, -2), new Vec3i(0,  0, -2), new Vec3i(1,  0, -2),
			new Vec3i(-1,  1, -2), new Vec3i(0,  1, -2), new Vec3i(1,  1, -2),
			new Vec3i(-1, -1, -1), new Vec3i(0, -1, -1), new Vec3i(1, -1, -1),
			new Vec3i(-1,  0, -1), new Vec3i(0,  0, -1), new Vec3i(1,  0, -1),
			new Vec3i(-1,  1, -1), new Vec3i(0,  1, -1), new Vec3i(1,  1, -1),
			new Vec3i(-1, -1,  0), new Vec3i(0, -1,  0), new Vec3i(1, -1,  0),
			new Vec3i(-1,  0,  0), new Vec3i(0,  0,  0), new Vec3i(1,  0,  0),
			new Vec3i(-1,  1,  0), new Vec3i(0,  1,  0), new Vec3i(1,  1,  0),
			new Vec3i(-1, -1,  1), new Vec3i(0, -1,  1), new Vec3i(1, -1,  1),
			new Vec3i(-1,  0,  1), new Vec3i(0,  0,  1), new Vec3i(1,  0,  1),
			new Vec3i(-1,  1,  1), new Vec3i(0,  1,  1), new Vec3i(1,  1,  1),
			new Vec3i(-1, -1,  2), new Vec3i(0, -1,  2), new Vec3i(1, -1,  2),
			new Vec3i(-1,  0,  2), new Vec3i(0,  0,  2), new Vec3i(1,  0,  2),
			new Vec3i(-1,  1,  2), new Vec3i(0,  1,  2), new Vec3i(1,  1,  2),
	};




































}
