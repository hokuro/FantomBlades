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
import net.minecraft.inventory.EntityEquipmentSlot;
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
	public static final String NAME_SUMMON_SEAL_BS = "summon_bladesmith";
	public static final String NAME_SUMMON_SEAL_AS = "summon_armorsmith";
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

	public static final String NAME_BYAKOBODY = "byakobody";
	public static final String NAME_BYAKOBOOTS = "byakoboots";
	public static final String NAME_BYAKOHELMET = "byakohelmet";
	public static final String NAME_BYAKOLEGS = "byakolegs";
	public static final String NAME_GENBUBODY = "genbubody";
	public static final String NAME_GENBUBOOTS = "genbuboots";
	public static final String NAME_GENBUHELMET = "genbuhelmet";
	public static final String NAME_GENBULEGS = "genbulegs";
	public static final String NAME_HAGANEBODY = "haganebody";
	public static final String NAME_HAGANEBOOTS = "haganeboots";
	public static final String NAME_HAGANEHELMET = "haganehelmet";
	public static final String NAME_HAGANELEGS = "haganelegs";
	public static final String NAME_KIRINBODY = "kirinbody";
	public static final String NAME_KIRINBOOTS = "kirinboots";
	public static final String NAME_KIRINHELMET = "kirinhelmet";
	public static final String NAME_KIRINLEGS = "kirinlegs";
	public static final String NAME_NIJIBODY = "nijibody";
	public static final String NAME_NIJIBOOTS = "nijiboots";
	public static final String NAME_NIJIHELMET = "nijihelmet";
	public static final String NAME_NIJILEGS = "nijilegs";
	public static final String NAME_SEIRYUBODY = "seiryubody";
	public static final String NAME_SEIRYUBOOTS = "seiryuboots";
	public static final String NAME_SEIRYUHELMET = "seiryuhelmet";
	public static final String NAME_SEIRYULEGS = "seiryulegs";
	public static final String NAME_SUZAKUBODY = "suzakubody";
	public static final String NAME_SUZAKUBOOTS = "suzakuboots";
	public static final String NAME_SUZAKUHELMET = "suzakuhelmet";
	public static final String NAME_SUZAKULEGS = "suzakulegs";





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
			NAME_SUMMON_SEAL_BS,
			NAME_SUMMON_SEAL_AS,
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
			NAME_GUNPOWDER_WATER,
			NAME_BYAKOBODY,
			NAME_BYAKOBOOTS,
			NAME_BYAKOHELMET,
			NAME_BYAKOLEGS,
			NAME_GENBUBODY,
			NAME_GENBUBOOTS,
			NAME_GENBUHELMET,
			NAME_GENBULEGS,
			NAME_HAGANEBODY,
			NAME_HAGANEBOOTS,
			NAME_HAGANEHELMET,
			NAME_HAGANELEGS,
			NAME_KIRINBODY,
			NAME_KIRINBOOTS,
			NAME_KIRINHELMET,
			NAME_KIRINLEGS,
			NAME_NIJIBODY,
			NAME_NIJIBOOTS,
			NAME_NIJIHELMET,
			NAME_NIJILEGS,
			NAME_SEIRYUBODY,
			NAME_SEIRYUBOOTS,
			NAME_SEIRYUHELMET,
			NAME_SEIRYULEGS,
			NAME_SUZAKUBODY,
			NAME_SUZAKUBOOTS,
			NAME_SUZAKUHELMET,
			NAME_SUZAKULEGS
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
	public static Item item_summon_seal_bladesmith;
	public static Item item_summon_seal_armorsmith;
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

	public static Item item_byakobody;
	public static Item item_byakoboots;
	public static Item item_byakohelmet;
	public static Item item_byakolegs;
	public static Item item_genbubody;
	public static Item item_genbuboots;
	public static Item item_genbuhelmet;
	public static Item item_genbulegs;
	public static Item item_haganebody;
	public static Item item_haganeboots;
	public static Item item_haganehelmet;
	public static Item item_haganelegs;
	public static Item item_kirinbody;
	public static Item item_kirinboots;
	public static Item item_kirinhelmet;
	public static Item item_kirinlegs;
	public static Item item_nijibody;
	public static Item item_nijiboots;
	public static Item item_nijihelmet;
	public static Item item_nijilegs;
	public static Item item_seiryubody;
	public static Item item_seiryuboots;
	public static Item item_seiryuhelmet;
	public static Item item_seiryulegs;
	public static Item item_suzakubody;
	public static Item item_suzakuboots;
	public static Item item_suzakuhelmet;
	public static Item item_suzakulegs;


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

		item_summon_seal_bladesmith = new ItemSummonSeal(EnumSummonType.BLADESMITH)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUMMON_SEAL_BS)
				.setUnlocalizedName(NAME_SUMMON_SEAL_BS);

		item_summon_seal_armorsmith = new ItemSummonSeal(EnumSummonType.ARMORSMITH)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUMMON_SEAL_AS)
				.setUnlocalizedName(NAME_SUMMON_SEAL_AS);

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

		item_byakobody= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BYAKOBODY)
				.setUnlocalizedName(NAME_BYAKOBODY);
		item_byakoboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BYAKOBOOTS)
				.setUnlocalizedName(NAME_BYAKOBOOTS);
		item_byakohelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BYAKOHELMET)
				.setUnlocalizedName(NAME_BYAKOHELMET);
		item_byakolegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BYAKOLEGS)
				.setUnlocalizedName(NAME_BYAKOLEGS);

		item_genbubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GENBUBODY)
				.setUnlocalizedName(NAME_GENBUBODY);
		item_genbuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GENBUBOOTS)
				.setUnlocalizedName(NAME_GENBUBOOTS);
		item_genbuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GENBUHELMET)
				.setUnlocalizedName(NAME_GENBUHELMET);
		item_genbulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GENBULEGS)
				.setUnlocalizedName(NAME_GENBULEGS);

		item_haganebody= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_HAGANEBODY)
				.setUnlocalizedName(NAME_HAGANEBODY);
		item_haganeboots= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_HAGANEBOOTS)
				.setUnlocalizedName(NAME_HAGANEBOOTS);
		item_haganehelmet= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_HAGANEHELMET)
				.setUnlocalizedName(NAME_HAGANEHELMET);
		item_haganelegs= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_HAGANELEGS)
				.setUnlocalizedName(NAME_HAGANELEGS);

		item_kirinbody= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KIRINBODY)
				.setUnlocalizedName(NAME_KIRINBODY);
		item_kirinboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KIRINBOOTS)
				.setUnlocalizedName(NAME_KIRINBOOTS);
		item_kirinhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KIRINHELMET)
				.setUnlocalizedName(NAME_KIRINHELMET);
		item_kirinlegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_KIRINLEGS)
				.setUnlocalizedName(NAME_KIRINLEGS);

		item_nijibody= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_NIJIBODY)
				.setUnlocalizedName(NAME_NIJIBODY);
		item_nijiboots= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_NIJIBOOTS)
				.setUnlocalizedName(NAME_NIJIBOOTS);
		item_nijihelmet= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_NIJIHELMET)
				.setUnlocalizedName(NAME_NIJIHELMET);
		item_nijilegs= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_NIJILEGS)
				.setUnlocalizedName(NAME_NIJILEGS);

		item_seiryubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SEIRYUBODY)
				.setUnlocalizedName(NAME_SEIRYUBODY);
		item_seiryuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SEIRYUBOOTS)
				.setUnlocalizedName(NAME_SEIRYUBOOTS);
		item_seiryuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SEIRYUHELMET)
				.setUnlocalizedName(NAME_SEIRYUHELMET);
		item_seiryulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SEIRYULEGS)
				.setUnlocalizedName(NAME_SEIRYULEGS);

		item_suzakubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,3,EntityEquipmentSlot.CHEST)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUZAKUBODY)
				.setUnlocalizedName(NAME_SUZAKUBODY);
		item_suzakuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,3,EntityEquipmentSlot.FEET)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUZAKUBOOTS)
				.setUnlocalizedName(NAME_SUZAKUBOOTS);
		item_suzakuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,3,EntityEquipmentSlot.HEAD)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUZAKUHELMET)
				.setUnlocalizedName(NAME_SUZAKUHELMET);
		item_suzakulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,3,EntityEquipmentSlot.LEGS)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_SUZAKULEGS)
				.setUnlocalizedName(NAME_SUZAKULEGS);



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
			{put(NAME_SUMMON_SEAL_BS,item_summon_seal_bladesmith);}
			{put(NAME_SUMMON_SEAL_AS,item_summon_seal_armorsmith);}
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
			{put(NAME_BYAKOBODY,item_byakobody);}
			{put(NAME_BYAKOBOOTS,item_byakoboots);}
			{put(NAME_BYAKOHELMET,item_byakohelmet);}
			{put(NAME_BYAKOLEGS,item_byakolegs);}
			{put(NAME_GENBUBODY,item_genbubody);}
			{put(NAME_GENBUBOOTS,item_genbuboots);}
			{put(NAME_GENBUHELMET,item_genbuhelmet);}
			{put(NAME_GENBULEGS,item_genbulegs);}
			{put(NAME_HAGANEBODY,item_haganebody);}
			{put(NAME_HAGANEBOOTS,item_haganeboots);}
			{put(NAME_HAGANEHELMET,item_haganehelmet);}
			{put(NAME_HAGANELEGS,item_haganelegs);}
			{put(NAME_KIRINBODY,item_kirinbody);}
			{put(NAME_KIRINBOOTS,item_kirinboots);}
			{put(NAME_KIRINHELMET,item_kirinhelmet);}
			{put(NAME_KIRINLEGS,item_kirinlegs);}
			{put(NAME_NIJIBODY,item_nijibody);}
			{put(NAME_NIJIBOOTS,item_nijiboots);}
			{put(NAME_NIJIHELMET,item_nijihelmet);}
			{put(NAME_NIJILEGS,item_nijilegs);}
			{put(NAME_SEIRYUBODY,item_seiryubody);}
			{put(NAME_SEIRYUBOOTS,item_seiryuboots);}
			{put(NAME_SEIRYUHELMET,item_seiryuhelmet);}
			{put(NAME_SEIRYULEGS,item_seiryulegs);}
			{put(NAME_SUZAKUBODY,item_suzakubody);}
			{put(NAME_SUZAKUBOOTS,item_suzakuboots);}
			{put(NAME_SUZAKUHELMET,item_suzakuhelmet);}
			{put(NAME_SUZAKULEGS,item_suzakulegs);}
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
			{put(NAME_SUMMON_SEAL_BS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUMMON_SEAL_BS, "inventory")});}
			{put(NAME_SUMMON_SEAL_AS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUMMON_SEAL_AS, "inventory")});}
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

			{put(NAME_BYAKOBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BYAKOBODY, "inventory")});}
			{put(NAME_BYAKOBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BYAKOBOOTS, "inventory")});}
			{put(NAME_BYAKOHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BYAKOHELMET, "inventory")});}
			{put(NAME_BYAKOLEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_BYAKOLEGS, "inventory")});}
			{put(NAME_GENBUBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GENBUBODY, "inventory")});}
			{put(NAME_GENBUBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GENBUBOOTS, "inventory")});}
			{put(NAME_GENBUHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GENBUHELMET, "inventory")});}
			{put(NAME_GENBULEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_GENBULEGS, "inventory")});}
			{put(NAME_HAGANEBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_HAGANEBODY, "inventory")});}
			{put(NAME_HAGANEBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_HAGANEBOOTS, "inventory")});}
			{put(NAME_HAGANEHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_HAGANEHELMET, "inventory")});}
			{put(NAME_HAGANELEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_HAGANELEGS, "inventory")});}
			{put(NAME_KIRINBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KIRINBODY, "inventory")});}
			{put(NAME_KIRINBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KIRINBOOTS, "inventory")});}
			{put(NAME_KIRINHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KIRINHELMET, "inventory")});}
			{put(NAME_KIRINLEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_KIRINLEGS, "inventory")});}
			{put(NAME_NIJIBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_NIJIBODY, "inventory")});}
			{put(NAME_NIJIBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_NIJIBOOTS, "inventory")});}
			{put(NAME_NIJIHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_NIJIHELMET, "inventory")});}
			{put(NAME_NIJILEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_NIJILEGS, "inventory")});}
			{put(NAME_SEIRYUBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SEIRYUBODY, "inventory")});}
			{put(NAME_SEIRYUBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SEIRYUBOOTS, "inventory")});}
			{put(NAME_SEIRYUHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SEIRYUHELMET, "inventory")});}
			{put(NAME_SEIRYULEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SEIRYULEGS, "inventory")});}
			{put(NAME_SUZAKUBODY,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUZAKUBODY, "inventory")});}
			{put(NAME_SUZAKUBOOTS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUZAKUBOOTS, "inventory")});}
			{put(NAME_SUZAKUHELMET,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUZAKUHELMET, "inventory")});}
			{put(NAME_SUZAKULEGS,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID + ":" + NAME_SUZAKULEGS, "inventory")});}
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
