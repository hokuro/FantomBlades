package mod.fbd.block;

import java.util.HashMap;
import java.util.Map;

import mod.fbd.block.BlockBladeStand.EnumBladeStand;
import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockCore{

	public static final String NAME_BLADEFORGE = "bladeforge";
	public static final String NAME_BLADEALTAR = "bladealter";
	public static final String NAME_BLADESTANDT1 = "bladestandt1";
	public static final String NAME_BLADESTANDT2 = "bladestandt2";
	public static final String NAME_FURNCEDUMMY = "dummyfurnce";
	public static final String NAME_ANVILEDUMMY = "dummyanvil";
	public static final String NAME_AIRPOMP     = "airpomp";
	public static final String NAME_GUNCUSTOMIZER = "guncustomizer";

	private static final String[] NAME_LIST = new String[]{
			NAME_BLADEFORGE,
			NAME_BLADEALTAR,
			NAME_BLADESTANDT1,
			NAME_BLADESTANDT2,
			NAME_FURNCEDUMMY,
			NAME_ANVILEDUMMY,
			NAME_AIRPOMP,
			NAME_GUNCUSTOMIZER,
	};

	public static Block block_bladeforge;
	public static Block block_bladealter;
	public static Block block_bladestandtype1;
	public static Block block_bladestandtype2;
	public static Block block_airpomp;
	public static Block block_furncedummy;
	public static Block block_anvildummy;
	public static Block block_guncustomizer;

	private static Map<String,Block> blockMap;
	private static Map<String,Item> itemMap;
	private static Map<String,ResourceLocation[]> resourceMap;

	private static void init(){

		block_bladeforge = new BlockBladeforge()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BLADEFORGE)
				.setUnlocalizedName(NAME_BLADEFORGE);

		block_bladealter = new BlockBladeAlter()
		.setCreativeTab(Mod_FantomBlade.tabElmWepon)
		.setRegistryName(NAME_BLADEALTAR)
		.setUnlocalizedName(NAME_BLADEALTAR);

		block_bladestandtype1 = new BlockBladeStand(EnumBladeStand.STAND1)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BLADESTANDT1)
				.setUnlocalizedName(NAME_BLADESTANDT1);

		block_bladestandtype2 = new BlockBladeStand(EnumBladeStand.STAND2)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_BLADESTANDT2)
				.setUnlocalizedName(NAME_BLADESTANDT2);

		block_furncedummy = new BlockDummyFurnce(Material.ROCK)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_FURNCEDUMMY)
				.setUnlocalizedName(NAME_FURNCEDUMMY);

		block_anvildummy = new BlockDummyAnvil(Material.ANVIL)
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_ANVILEDUMMY)
				.setUnlocalizedName(NAME_ANVILEDUMMY);

		block_airpomp = new BlockAirPomp()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_AIRPOMP)
				.setUnlocalizedName(NAME_AIRPOMP);

		block_guncustomizer = new BlockGunCustomizer()
				.setCreativeTab(Mod_FantomBlade.tabElmWepon)
				.setRegistryName(NAME_GUNCUSTOMIZER)
				.setUnlocalizedName(NAME_GUNCUSTOMIZER);


		blockMap = new HashMap<String,Block>(){
			{put(NAME_BLADEFORGE,block_bladeforge);}
			{put(NAME_BLADEALTAR,block_bladealter);}
			{put(NAME_BLADESTANDT1,block_bladestandtype1);}
			{put(NAME_BLADESTANDT2,block_bladestandtype2);}
			{put(NAME_AIRPOMP,block_airpomp);}
			{put(NAME_FURNCEDUMMY,block_furncedummy);}
			{put(NAME_ANVILEDUMMY,block_anvildummy);}
			{put(NAME_GUNCUSTOMIZER,block_guncustomizer);}
		};

		itemMap = new HashMap<String,Item>(){
			{put(NAME_BLADEFORGE,(new ItemBlock(block_bladeforge)).setRegistryName(NAME_BLADEFORGE));}
			{put(NAME_BLADEALTAR,(new ItemBlock(block_bladealter)).setRegistryName(NAME_BLADEALTAR));}
			{put(NAME_BLADESTANDT1,(new ItemBlock(block_bladestandtype1)).setRegistryName(NAME_BLADESTANDT1));}
			{put(NAME_BLADESTANDT2,(new ItemBlock(block_bladestandtype2)).setRegistryName(NAME_BLADESTANDT2));}
			{put(NAME_AIRPOMP,(new ItemBlock(block_airpomp)).setRegistryName(NAME_AIRPOMP));}
			{put(NAME_FURNCEDUMMY,(new ItemBlockDummy(block_furncedummy)).setRegistryName(NAME_FURNCEDUMMY));}
			{put(NAME_ANVILEDUMMY,(new ItemBlockDummy(block_anvildummy)).setRegistryName(NAME_ANVILEDUMMY));}
			{put(NAME_GUNCUSTOMIZER,(new ItemBlock(block_guncustomizer)).setRegistryName(NAME_GUNCUSTOMIZER));}
		};


		resourceMap = new HashMap<String,ResourceLocation[]>(){
			{put(NAME_BLADEFORGE,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEFORGE)});}
			{put(NAME_BLADEALTAR,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADEALTAR)});}
			{put(NAME_BLADESTANDT1,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADESTANDT1)});}
			{put(NAME_BLADESTANDT2,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_BLADESTANDT2)});}
			{put(NAME_AIRPOMP,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_AIRPOMP)});}
			{put(NAME_FURNCEDUMMY,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_FURNCEDUMMY)});}
			{put(NAME_ANVILEDUMMY,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_ANVILEDUMMY)});}
			{put(NAME_GUNCUSTOMIZER,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_GUNCUSTOMIZER)});}
		};
	}


	public static void register(FMLPreInitializationEvent event){
		init();
		for (String key: NAME_LIST){
			ForgeRegistries.BLOCKS.register(blockMap.get(key));
			ForgeRegistries.ITEMS.register(itemMap.get(key));
		}

		if (event.getSide().isClient()){
			for (String key : NAME_LIST){
				Item witem = itemMap.get(key);
				ResourceLocation[] wresource = resourceMap.get(key);
				if (wresource.length > 1){
					ModelLoader.registerItemVariants(witem, wresource);
				}
				for (int i = 0; i < wresource.length; i++){
					ModelLoader.setCustomModelResourceLocation(witem, i,
							new ModelResourceLocation(wresource[i], "inventory"));
				}
			}
		}
	}
}