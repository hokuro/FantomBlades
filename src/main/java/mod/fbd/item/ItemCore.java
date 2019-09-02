package mod.fbd.item;

import java.util.HashMap;
import java.util.Map;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemBladePiece.EnumBladePieceType;
import mod.fbd.item.ItemBurret.EnumBurret;
import mod.fbd.item.ItemHummer.EnumHunmmerType;
import mod.fbd.item.ItemSummonSeal.EnumSummonType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

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
	public static final String NAME_IRONSCRAP = "ironscrap";
	public static final String NAME_BLADEPIECE_NORMAL = "bladepiece_normal";
	public static final String NAME_BLADEPIECE_SEIRYU = "bladepiece_seiryu";
	public static final String NAME_BLADEPIECE_BYAKO = "bladepiece_byako";
	public static final String NAME_BLADEPIECE_GENBU = "bladepiece_genbu";
	public static final String NAME_BLADEPIECE_SUZAKU = "bladepiece_suzaku";
	public static final String NAME_BLADEPIECE_KIRIN = "bladepiece_kirin";
	public static final String NAME_BLADEPIECE_NIJI = "bladepiece_niji";

	public static final String NAME_MOBSOULE = "mobsoule";
	public static final String NAME_STEEL = "steel";
	public static final String NAME_REVOLVER = "revolver";
	public static final String NAME_AUTOMATIC = "automatic";
	public static final String NAME_CARTRIDGE = "cartridge";

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
			NAME_BLADEPIECE_NORMAL,
			NAME_BLADEPIECE_SEIRYU,
			NAME_BLADEPIECE_BYAKO,
			NAME_BLADEPIECE_GENBU,
			NAME_BLADEPIECE_SUZAKU,
			NAME_BLADEPIECE_KIRIN,
			NAME_BLADEPIECE_NIJI,
			NAME_MOBSOULE,
			NAME_STEEL,
			NAME_REVOLVER,
			NAME_AUTOMATIC,
			NAME_CARTRIDGE,
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



	public static Item item_magnet = new ItemMagnet(new Item.Properties().maxStackSize(1).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_MAGNET);
	public static Item item_satetu = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_IRONSAND);
	public static Item item_tamahagane = new ItemTamahagane(new Item.Properties().group(Mod_FantomBlade.tabElmWepon),2.5F).setRegistryName(NAME_TAMAHAGANE);
	public static Item item_katana = new ItemKatana(new Item.Properties().group(Mod_FantomBlade.tabElmWepon).defaultMaxDamage(100)).setRegistryName(NAME_KATANA);
	public static Item item_katana_seiryu = new ItemKatanaSeiryu(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_SEIRYU);
	public static Item item_katana_suzaku = new ItemKatanaSuzaku(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_SUZAKU);
	public static Item item_katana_byako = new ItemKatanaByako(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_BYAKO);
	public static Item item_katana_genbu = new ItemKatanaGenbu(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_GENBU);
	public static Item item_katana_kirin = new ItemKatanaKirin(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_KIRIN);
	public static Item item_katana_niji = new ItemKatanaNiji(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_NIJI);
	public static Item item_katana_mugen = new ItemKatanaMugen(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KATANA_MUGEN);
	public static Item item_hunmmer_small = new ItemHummer(EnumHunmmerType.SMALL, new Item.Properties().defaultMaxDamage(EnumHunmmerType.SMALL.getMaxDamage()).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KODUTI);
	public static Item item_hunmmer_big = new ItemHummer(EnumHunmmerType.BIG, new Item.Properties().defaultMaxDamage(EnumHunmmerType.BIG.getMaxDamage()).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_ODUTI);
	public static Item item_summon_seal_bladesmith = new ItemSummonSeal(EnumSummonType.BLADESMITH,new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUMMON_SEAL_BS);
	public static Item item_summon_seal_armorsmith = new ItemSummonSeal(EnumSummonType.ARMORSMITH,new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUMMON_SEAL_AS);
	public static Item item_ironscrap = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_IRONSCRAP);

	public static Item item_bladepiece_normal = new ItemBladePiece(EnumBladePieceType.NORMAL, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_NORMAL);
	public static Item item_bladepiece_seiryu = new ItemBladePiece(EnumBladePieceType.SEIRYU, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_SEIRYU);
	public static Item item_bladepiece_byako = new ItemBladePiece(EnumBladePieceType.BYAKO, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_BYAKO);
	public static Item item_bladepiece_genbu = new ItemBladePiece(EnumBladePieceType.GENBU, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_GENBU);
	public static Item item_bladepiece_suzaku = new ItemBladePiece(EnumBladePieceType.SUZAKU, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_SUZAKU);
	public static Item item_bladepiece_kirin = new ItemBladePiece(EnumBladePieceType.KIRIN, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_KIRIN);
	public static Item item_bladepiece_niji = new ItemBladePiece(EnumBladePieceType.NIJI, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BLADEPIECE_NIJI);

	public static Item item_mobsoule = new ItemMobSoule(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_MOBSOULE);
	public static Item item_steel = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_STEEL);
	public static Item item_revolver = new ItemRevolver(new Item.Properties().defaultMaxDamage(500).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_REVOLVER);
	public static Item item_autmatic = new ItemAutomatic(new Item.Properties().maxStackSize(1).defaultMaxDamage(500).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_AUTOMATIC);
	public static Item item_cartridge = new ItemCartridge(new Item.Properties().maxStackSize(1).group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_CARTRIDGE);
	public static Item item_burret = new ItemBurret(EnumBurret.NORMAL, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET);
	public static Item item_burret_potion = new ItemBurretPotion(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_POTION);
	public static Item item_burret_teleport = new ItemBurret(EnumBurret.TELEPORT, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_TELEPORT);

	public static Item item_burret_draw = new ItemBurret(EnumBurret.DRAW, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_DRAW);
	public static Item item_burret_explode = new ItemBurret(EnumBurret.EXPLOSION, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_EXPLOSION);
	public static Item item_burret_assasination = new ItemBurret(EnumBurret.ASSASINATION, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_ASSASINATION);
	public static Item item_burret_flame = new ItemBurret(EnumBurret.FLAME, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_FLAME);
	public static Item item_burret_thunder = new ItemBurret(EnumBurret.THUNDER, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_THUNDER);
	public static Item item_burret_blow = new ItemBurret(EnumBurret.BLOW, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_BLOW);
	public static Item item_burret_drain = new ItemBurret(EnumBurret.DRAIN, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_DRAIN);
	public static Item item_burret_golem = new ItemBurret(EnumBurret.GOLEM, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BURRET_GOLEM);
	public static Item item_burret_snowman = new ItemBurret(EnumBurret.SNOWMAN, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAEM_BURRET_SNOWMAN);
	public static Item item_gunpowder_teleport = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAEM_GUNPOWDER_TELEPORT);
	public static Item item_gunpowder_draw = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GUNPOWDER_DRAW);
	public static Item item_gunpowder_expload = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GUNPOWDER_EXPLOAD);
	public static Item item_gunpwoder_flame = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GUNPOWDER_FLAME);
	public static Item item_gunpowder_water = new Item(new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GUNPOWDER_WATER);
	public static Item item_byakobody= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BYAKOBODY);
	public static Item item_byakoboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BYAKOBOOTS);
	public static Item item_byakohelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BYAKOHELMET);
	public static Item item_byakolegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORBYAKO,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_BYAKOLEGS);
	public static Item item_genbubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GENBUBODY);
	public static Item item_genbuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GENBUBOOTS);
	public static Item item_genbuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GENBUHELMET);
	public static Item item_genbulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORGENBU,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_GENBULEGS);
	public static Item item_haganebody= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_HAGANEBODY);
	public static Item item_haganeboots= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_HAGANEBOOTS);
	public static Item item_haganehelmet= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_HAGANEHELMET);
	public static Item item_haganelegs= new ItemHaganeAromor(Mod_FantomBlade.ARMORHAGANE,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_HAGANELEGS);
	public static Item item_kirinbody= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KIRINBODY);
	public static Item item_kirinboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KIRINBOOTS);
	public static Item item_kirinhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KIRINHELMET);
	public static Item item_kirinlegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORKIRIN,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_KIRINLEGS);
	public static Item item_nijibody= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_NIJIBODY);
	public static Item item_nijiboots= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_NIJIBOOTS);
	public static Item item_nijihelmet= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_NIJIHELMET);
	public static Item item_nijilegs= new ItemHaganeAromor(Mod_FantomBlade.ARMORNIJI,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_NIJILEGS);
	public static Item item_seiryubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SEIRYUBODY);
	public static Item item_seiryuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SEIRYUBOOTS);
	public static Item item_seiryuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SEIRYUHELMET);
	public static Item item_seiryulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORSEIRYU,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SEIRYULEGS);
	public static Item item_suzakubody= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,EntityEquipmentSlot.CHEST, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUZAKUBODY);
	public static Item item_suzakuboots= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,EntityEquipmentSlot.FEET, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUZAKUBOOTS);
	public static Item item_suzakuhelmet= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,EntityEquipmentSlot.HEAD, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUZAKUHELMET);
	public static Item item_suzakulegs= new ItemHaganeAromor(Mod_FantomBlade.AROMORSUZAKU,EntityEquipmentSlot.LEGS, new Item.Properties().group(Mod_FantomBlade.tabElmWepon)).setRegistryName(NAME_SUZAKULEGS);


	private static Map<String,Item> itemMap;
	public static void init(){
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
			{put(NAME_IRONSCRAP,item_ironscrap);}
			{put(NAME_BLADEPIECE_NORMAL,item_bladepiece_normal);}
			{put(NAME_BLADEPIECE_SEIRYU,item_bladepiece_seiryu);}
			{put(NAME_BLADEPIECE_BYAKO,item_bladepiece_byako);}
			{put(NAME_BLADEPIECE_GENBU,item_bladepiece_genbu);}
			{put(NAME_BLADEPIECE_SUZAKU,item_bladepiece_suzaku);}
			{put(NAME_BLADEPIECE_KIRIN,item_bladepiece_kirin);}
			{put(NAME_BLADEPIECE_NIJI,item_bladepiece_niji);}
			{put(NAME_MOBSOULE,item_mobsoule);}
			{put(NAME_STEEL,item_steel);}
			{put(NAME_REVOLVER,item_revolver);}
			{put(NAME_AUTOMATIC,item_autmatic);}
			{put(NAME_CARTRIDGE,item_cartridge);}
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

	}

	public static void register(final RegistryEvent.Register<Item> event) {
		init();
		for (String key : NAME_LIST){
			if (itemMap.containsKey(key)) {
				event.getRegistry().register(itemMap.get(key));
			}
		}
	}
}
