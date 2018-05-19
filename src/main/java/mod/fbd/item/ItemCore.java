package mod.fbd.item;

import java.util.HashMap;
import java.util.Map;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.ItemBurret.EnumBurret;
import mod.fbd.item.ItemHummer.EnumHunmmerType;
import mod.fbd.item.ItemSummonSeal.EnumSummonType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemCore {
	public static final String NAME_MAGNET = "magnet";
	public static final String NAME_IRONSAND = "ironsand";
	public static final String NAME_TAMAHAGANE = "tamahagane";
	public static final String NAME_KATANA = "katana";
	public static final String NAME_KATANA_SEIRYU="katana_seiryu";
	public static final String NAME_KATANA_SUZAKU="katana_suzaku";
	public static final String NAME_KATANA_BYAKO ="katana_byako";
	public static final String NAME_KATANA_GENBU="katana_genbu";
	public static final String NAME_KATANA_KIRIN ="katana_kirin";
	public static final String NAME_KATANA_NIJI = "katana_niji";
	public static final String NAME_KATANA_MUGEN = "katana_mugen";
	public static final String NAME_SUMMON_SEAL = "summonseal";
	public static final String NAME_KODUTI = "small_hunmmer";
	public static final String NAME_ODUTI = "big_hunmmer";
	public static final String NAME_NORO = "noro";
	public static final String NAME_ZUKU = "zuku";
	public static final String NAME_BLADEPIECE = "bladepiece";
	public static final String NAME_MOBSOULE = "mobsoule";
	public static final String NAME_STEEL = "steel";
	public static final String NAME_REVOLVER = "revolver";
	public static final String NAME_AUTOMATIC = "automatic";
	public static final String NAME_CARTRIDGE = "cartridge";
	public static final String NAME_BARREL = "barrel";
	public static final String NAME_TRRIGER = "trriger";
	public static final String NAME_MAGAZINE_REVO = "magazine_revolver";
	public static final String NAME_MAGAZINE_AUTO = "magazine_automatic";
	public static final String NAME_GRIP = "grip";
	public static final String NAME_BURRET = "burret_normal";
	public static final String NAME_BURRET_POTION = "burret_potion";
	public static final String NAME_BURRET_TELEPORT = "burret_teleport";
	public static final String NAME_BURRET_DRAW = "burret_draw";
	public static final String NAME_BURRET_EXPLOSION = "burret_explosion";
	public static final String NAME_BURRET_ASSASINATION = "burret_assasination";
	public static final String NAME_BURRET_FLAME = "burret_flame";
	public static final String NAME_BURRET_THUNDER = "burret_thunder";
	public static final String NAME_BURRET_BLOW = "burret_blow";
	public static final String NAME_BURRET_DRAIN = "burret_drain";
	public static final String NAME_BURRET_GOLEM = "burret_golem";
	public static final String NAEM_BURRET_SNOWMAN = "burret_snowman";

	public static final String NAEM_GUNPOWDER_TELEPORT = "gunpowder_teleport";
	public static final String NAME_GUNPOWDER_DRAW = "gunpowder_draw";
	public static final String NAME_GUNPOWDER_EXPLOAD = "gunpowder_expload";
	public static final String NAME_GUNPOWDER_FLAME = "gunpowder_flame";
	public static final String NAME_GUNPOWDER_WATER = "gunpowder_water";


	public static final String[] NAME_LIST = new String[]{
			NAME_MAGNET,
			NAME_IRONSAND,
			NAME_TAMAHAGANE,
			NAME_NORO,
			NAME_ZUKU,
			NAME_KATANA,
			NAME_KATANA_SEIRYU,
			NAME_KATANA_SUZAKU,
			NAME_KATANA_BYAKO,
			NAME_KATANA_GENBU,
			NAME_KATANA_KIRIN,
			NAME_KATANA_NIJI,
			NAME_KATANA_MUGEN,
			NAME_KODUTI,
			NAME_ODUTI,
			NAME_SUMMON_SEAL,
			NAME_BLADEPIECE,
			NAME_MOBSOULE,
			NAME_STEEL,
			NAME_REVOLVER,
			NAME_AUTOMATIC,
			NAME_CARTRIDGE,
//			NAME_BARREL,
//			NAME_TRRIGER,
//			NAME_MAGAZINE_REVO,
//			NAME_MAGAZINE_AUTO,
//			NAME_GRIP,
			NAME_BURRET,
			NAME_BURRET_POTION,
			NAME_BURRET_TELEPORT,
			NAME_BURRET_DRAW,
			NAME_BURRET_EXPLOSION,
			NAME_BURRET_ASSASINATION,
			NAME_BURRET_FLAME,
			NAME_BURRET_THUNDER,
			NAME_BURRET_BLOW,
			NAME_BURRET_DRAIN,
			NAME_BURRET_GOLEM,
			NAEM_BURRET_SNOWMAN,
			NAEM_GUNPOWDER_TELEPORT,
			NAME_GUNPOWDER_DRAW,
			NAME_GUNPOWDER_EXPLOAD,
			NAME_GUNPOWDER_FLAME,
			NAME_GUNPOWDER_WATER
	};


	public static Item item_airpomp;
	public static Item item_magnet;
	public static Item item_satetu;
	public static Item item_tamahagane;
	public static Item item_katana;
	public static Item item_katana_seiryu;
	public static Item item_katana_suzaku;
	public static Item item_katana_byako;
	public static Item item_katana_genbu;
	public static Item item_katana_kirin;
	public static Item item_katana_niji;
	public static Item item_katana_mugen;
	public static Item item_hunmmer_small;
	public static Item item_hunmmer_big;
	public static Item item_summon_seal;
	public static Item item_noro;
	public static Item item_zuku;
	public static Item item_bladepiece;
	public static Item item_mobsoule;

	public static Item item_steel;
	public static Item item_revolver;
	public static Item item_autmatic;
	public static Item item_cartridge;

	public static Item item_barrel;
	public static Item item_trriger;
	public static Item item_grip;
	public static Item item_magazine_revo;
	public static Item item_magazine_auto;

	public static Item item_burret;

	public static Item item_burret_potion;
	public static Item item_burret_teleport;
	public static Item item_burret_draw;
	public static Item item_burret_explode;
	public static Item item_burret_assasination;
	public static Item item_burret_flame;
	public static Item item_burret_thunder;
	public static Item item_burret_blow;
	public static Item item_burret_drain;
	public static Item item_burret_golem;
	public static Item item_burret_snowman;

	public static Item item_gunpowder_teleport;
	public static Item item_gunpowder_draw;
	public static Item item_gunpowder_expload;
	public static Item item_gunpwoder_flame;
	public static Item item_gunpowder_water;




	private static Map<String,Item> itemMap;
	private static Map<String,ModelResourceLocation[]> resourceMap;

	private static void init(){

		item_magnet = new ItemMagnet()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_MAGNET)
				.setUnlocalizedName(NAME_MAGNET);
		item_satetu = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_IRONSAND)
				.setUnlocalizedName(NAME_IRONSAND);

		item_tamahagane = new ItemTamahagane()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_TAMAHAGANE)
				.setUnlocalizedName(NAME_TAMAHAGANE);

		item_katana = new ItemKatana()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA)
				.setUnlocalizedName(NAME_KATANA);

		item_katana_seiryu = new ItemKatanaSeiryu()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_SEIRYU)
				.setUnlocalizedName(NAME_KATANA_SEIRYU);

		item_katana_suzaku = new ItemKatanaSuzaku()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_SUZAKU)
				.setUnlocalizedName(NAME_KATANA_SUZAKU);

		item_katana_byako = new ItemKatanaByako()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_BYAKO)
				.setUnlocalizedName(NAME_KATANA_BYAKO);

		item_katana_genbu = new ItemKatanaGenbu()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_GENBU)
				.setUnlocalizedName(NAME_KATANA_GENBU);

		item_katana_kirin = new ItemKatanaKirin()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_KIRIN)
				.setUnlocalizedName(NAME_KATANA_KIRIN);

		item_katana_niji = new ItemKatanaNiji()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_NIJI)
				.setUnlocalizedName(NAME_KATANA_NIJI);

		item_katana_mugen = new ItemKatanaMugen()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KATANA_MUGEN)
				.setUnlocalizedName(NAME_KATANA_MUGEN);

		item_hunmmer_small = new ItemHummer(EnumHunmmerType.SMALL)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KODUTI)
				.setUnlocalizedName(NAME_KODUTI);

		item_hunmmer_big = new ItemHummer(EnumHunmmerType.BIG)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_ODUTI)
				.setUnlocalizedName(NAME_ODUTI);

		item_summon_seal = new ItemSummonSeal(EnumSummonType.BLADESMITH)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUMMON_SEAL)
				.setUnlocalizedName(NAME_SUMMON_SEAL);

		item_noro = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_NORO)
				.setUnlocalizedName(NAME_NORO);

		item_zuku = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_ZUKU)
				.setUnlocalizedName(NAME_ZUKU);

		item_bladepiece = new ItemBladePiece()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BLADEPIECE)
				.setUnlocalizedName(NAME_BLADEPIECE);
		item_mobsoule = new ItemMobSoule()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_MOBSOULE)
				.setUnlocalizedName(NAME_MOBSOULE);

		item_steel = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_STEEL)
				.setUnlocalizedName(NAME_STEEL);
		item_revolver = new ItemRevolver()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_REVOLVER)
				.setUnlocalizedName(NAME_REVOLVER);
		item_autmatic = new ItemAutomatic()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_AUTOMATIC)
				.setUnlocalizedName(NAME_AUTOMATIC);
		item_cartridge = new ItemCartridge()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_CARTRIDGE)
				.setUnlocalizedName(NAME_CARTRIDGE);
		item_barrel = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BARREL)
				.setUnlocalizedName(NAME_BARREL);
		item_trriger = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_TRRIGER)
				.setUnlocalizedName(NAME_TRRIGER);
		item_magazine_revo = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_MAGAZINE_REVO)
				.setUnlocalizedName(NAME_MAGAZINE_REVO);
		item_magazine_auto = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_MAGAZINE_AUTO)
				.setUnlocalizedName(NAME_MAGAZINE_AUTO);
		item_grip = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GRIP)
				.setUnlocalizedName(NAME_GRIP);


		item_burret = new ItemBurret(EnumBurret.NORMAL)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET)
				.setUnlocalizedName(NAME_BURRET);
		item_burret_potion = new ItemBurretPotion()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_POTION)
				.setUnlocalizedName(NAME_BURRET_POTION);
		item_burret_teleport = new ItemBurret(EnumBurret.TELEPORT)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_TELEPORT)
				.setUnlocalizedName(NAME_BURRET_TELEPORT);
		item_burret_draw = new ItemBurret(EnumBurret.DRAW)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_DRAW)
				.setUnlocalizedName(NAME_BURRET_DRAW);
		item_burret_explode = new ItemBurret(EnumBurret.EXPLOSION)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_EXPLOSION)
				.setUnlocalizedName(NAME_BURRET_EXPLOSION);
		item_burret_assasination = new ItemBurret(EnumBurret.ASSASINATION)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_ASSASINATION)
				.setUnlocalizedName(NAME_BURRET_ASSASINATION);
		item_burret_flame = new ItemBurret(EnumBurret.FLAME)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_FLAME)
				.setUnlocalizedName(NAME_BURRET_FLAME);
		item_burret_thunder = new ItemBurret(EnumBurret.THUNDER)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_THUNDER)
				.setUnlocalizedName(NAME_BURRET_THUNDER);
		item_burret_blow = new ItemBurret(EnumBurret.BLOW)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_BLOW)
				.setUnlocalizedName(NAME_BURRET_BLOW);
		item_burret_drain = new ItemBurret(EnumBurret.DRAIN)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_DRAIN)
				.setUnlocalizedName(NAME_BURRET_DRAIN);
		item_burret_golem = new ItemBurret(EnumBurret.GOLEM)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BURRET_GOLEM)
				.setUnlocalizedName(NAME_BURRET_GOLEM);
		item_burret_snowman = new ItemBurret(EnumBurret.SNOWMAN)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAEM_BURRET_SNOWMAN)
				.setUnlocalizedName(NAEM_BURRET_SNOWMAN);


		item_gunpowder_teleport = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAEM_GUNPOWDER_TELEPORT)
				.setUnlocalizedName(NAEM_GUNPOWDER_TELEPORT);

		item_gunpowder_draw = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GUNPOWDER_DRAW)
				.setUnlocalizedName(NAME_GUNPOWDER_DRAW);

		item_gunpowder_expload = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GUNPOWDER_EXPLOAD)
				.setUnlocalizedName(NAME_GUNPOWDER_EXPLOAD);

		item_gunpwoder_flame = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GUNPOWDER_FLAME)
				.setUnlocalizedName(NAME_GUNPOWDER_FLAME);

		item_gunpowder_water = new Item()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GUNPOWDER_WATER)
				.setUnlocalizedName(NAME_GUNPOWDER_WATER);


		itemMap = new HashMap<String,Item>(){
			{put(NAME_MAGNET,item_magnet);}
			{put(NAME_IRONSAND,item_satetu);}
			{put(NAME_TAMAHAGANE,item_tamahagane);}
			{put(NAME_KATANA,item_katana);}
			{put(NAME_KATANA_SEIRYU,item_katana_seiryu);}
			{put(NAME_KATANA_SUZAKU,item_katana_suzaku);}
			{put(NAME_KATANA_BYAKO,item_katana_byako);}
			{put(NAME_KATANA_GENBU,item_katana_genbu);}
			{put(NAME_KATANA_KIRIN,item_katana_kirin);}
			{put(NAME_KATANA_NIJI,item_katana_niji);}
			{put(NAME_KATANA_MUGEN,item_katana_mugen);}
			{put(NAME_KODUTI,item_hunmmer_small);}
			{put(NAME_ODUTI,item_hunmmer_big);}
			{put(NAME_SUMMON_SEAL,item_summon_seal);}
			{put(NAME_NORO,item_noro);}
			{put(NAME_ZUKU,item_zuku);}
			{put(NAME_BLADEPIECE,item_bladepiece);}
			{put(NAME_MOBSOULE,item_mobsoule);}
			{put(NAME_STEEL,item_steel);}
			{put(NAME_REVOLVER,item_revolver);}
			{put(NAME_AUTOMATIC,item_autmatic);}
			{put(NAME_CARTRIDGE,item_cartridge);}
			{put(NAME_BARREL,item_barrel);}
			{put(NAME_TRRIGER,item_trriger);}
			{put(NAME_MAGAZINE_REVO,item_magazine_revo);}
			{put(NAME_MAGAZINE_AUTO,item_magazine_auto);}
			{put(NAME_GRIP,item_grip);}
			{put(NAME_BURRET,item_burret);}
			{put(NAME_BURRET_POTION,item_burret_potion);}
			{put(NAME_BURRET_TELEPORT,item_burret_teleport);}
			{put(NAME_BURRET_DRAW,item_burret_draw);}
			{put(NAME_BURRET_EXPLOSION,item_burret_explode);}
			{put(NAME_BURRET_ASSASINATION,item_burret_assasination);}
			{put(NAME_BURRET_FLAME,item_burret_flame);}
			{put(NAME_BURRET_THUNDER,item_burret_thunder);}
			{put(NAME_BURRET_BLOW,item_burret_blow);}
			{put(NAME_BURRET_DRAIN,item_burret_drain);}
			{put(NAME_BURRET_GOLEM,item_burret_golem);}
			{put(NAEM_BURRET_SNOWMAN,item_burret_snowman);}
			{put(NAEM_GUNPOWDER_TELEPORT,item_gunpowder_teleport);}
			{put(NAME_GUNPOWDER_DRAW,item_gunpowder_draw);}
			{put(NAME_GUNPOWDER_EXPLOAD,item_gunpowder_expload);}
			{put(NAME_GUNPOWDER_FLAME,item_gunpwoder_flame);}
			{put(NAME_GUNPOWDER_WATER,item_gunpowder_water);}
		};

		resourceMap = new HashMap<String,ModelResourceLocation[]>(){
			{put(NAME_MAGNET,new ModelResourceLocation[]{
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGNET+"_0", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGNET+"_1", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGNET+"_2", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGNET+"_3", "inventory")
					});}
			{put(NAME_IRONSAND,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_IRONSAND, "inventory")});}
			{put(NAME_TAMAHAGANE,new ModelResourceLocation[]{
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_TAMAHAGANE+"_1", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_TAMAHAGANE+"_2", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_TAMAHAGANE+"_3", "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_TAMAHAGANE+"_4", "inventory")
					});}
			{put(NAME_KATANA,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA, "inventory")});}
			{put(NAME_KATANA_SEIRYU,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_SEIRYU, "inventory")});}
			{put(NAME_KATANA_SUZAKU,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_SUZAKU, "inventory")});}
			{put(NAME_KATANA_BYAKO,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_BYAKO, "inventory")});}
			{put(NAME_KATANA_GENBU,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_GENBU, "inventory")});}
			{put(NAME_KATANA_KIRIN,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_KIRIN, "inventory")});}
			{put(NAME_KATANA_NIJI,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_NIJI, "inventory")});}
			{put(NAME_KATANA_MUGEN,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KATANA_MUGEN, "inventory")});}
			{put(NAME_KODUTI,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KODUTI, "inventory")});}
			{put(NAME_ODUTI,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_ODUTI, "inventory")});}
			{put(NAME_SUMMON_SEAL,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUMMON_SEAL, "inventory")});}
			{put(NAME_NORO,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_NORO, "inventory")});}
			{put(NAME_ZUKU,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_ZUKU, "inventory")});}
			{put(NAME_BLADEPIECE,new ModelResourceLocation[]{
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(0).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(1).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(2).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(3).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(4).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(5).getRegisterName(), "inventory"),
					new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEPIECE + "_" + EnumBladePieceType.getFromIndex(6).getRegisterName(), "inventory")
			});}
			{put(NAME_MOBSOULE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MOBSOULE, "inventory")});}
			{put(NAME_STEEL,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_STEEL, "inventory")});}
			{put(NAME_REVOLVER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_REVOLVER, "inventory")});}
			{put(NAME_AUTOMATIC,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_AUTOMATIC, "inventory")});}
			{put(NAME_CARTRIDGE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_CARTRIDGE, "inventory")});}
			{put(NAME_BARREL,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BARREL, "inventory")});}
			{put(NAME_TRRIGER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_TRRIGER, "inventory")});}
			{put(NAME_MAGAZINE_REVO,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGAZINE_REVO, "inventory")});}
			{put(NAME_MAGAZINE_AUTO,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_MAGAZINE_AUTO, "inventory")});}
			{put(NAME_GRIP,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GRIP, "inventory")});}

			{put(NAME_BURRET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET, "inventory")});}
			{put(NAME_BURRET_POTION,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_POTION, "inventory")});}
			{put(NAME_BURRET_TELEPORT,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_TELEPORT, "inventory")});}
			{put(NAME_BURRET_DRAW,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_DRAW, "inventory")});}
			{put(NAME_BURRET_EXPLOSION,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_EXPLOSION, "inventory")});}
			{put(NAME_BURRET_ASSASINATION,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_ASSASINATION, "inventory")});}
			{put(NAME_BURRET_FLAME,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_FLAME, "inventory")});}
			{put(NAME_BURRET_THUNDER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_THUNDER, "inventory")});}
			{put(NAME_BURRET_BLOW,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_BLOW, "inventory")});}
			{put(NAME_BURRET_DRAIN,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_DRAIN, "inventory")});}
			{put(NAME_BURRET_GOLEM,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BURRET_GOLEM, "inventory")});}
			{put(NAEM_BURRET_SNOWMAN,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAEM_BURRET_SNOWMAN, "inventory")});}
			{put(NAEM_GUNPOWDER_TELEPORT,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAEM_GUNPOWDER_TELEPORT, "inventory")});}
			{put(NAME_GUNPOWDER_DRAW,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GUNPOWDER_DRAW, "inventory")});}
			{put(NAME_GUNPOWDER_EXPLOAD,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GUNPOWDER_EXPLOAD, "inventory")});}
			{put(NAME_GUNPOWDER_FLAME,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GUNPOWDER_FLAME, "inventory")});}
			{put(NAME_GUNPOWDER_WATER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GUNPOWDER_WATER, "inventory")});}
		};
	}

	public static void register(FMLPreInitializationEvent event) {
		init();
		for (String key : NAME_LIST){
			ForgeRegistries.ITEMS.register(itemMap.get(key));
		}

        //テクスチャ・モデル指定JSONファイル名の登録。
        if (event.getSide().isClient()) {
        	for (String key : NAME_LIST){
        		//1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        		int cnt = 0;
        		for (ModelResourceLocation rc : resourceMap.get(key)){
        			ModelLoader.setCustomModelResourceLocation(itemMap.get(key), cnt, rc);
        			cnt++;
        		}
        	}
        }
	}
}
