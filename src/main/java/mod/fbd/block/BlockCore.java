package mod.fbd.block;

import java.util.HashMap;
import java.util.Map;

import mod.fbd.block.BlockBladeStand.EnumBladeStand;
import mod.fbd.block.item.BlockItemDummy;
import mod.fbd.core.Mod_FantomBlade;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class BlockCore {
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

	public static Block block_bladeforge = new BlockBladeforge(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.8F).sound(SoundType.STONE)).setRegistryName(NAME_BLADEFORGE);
	public static Block block_bladealter = new BlockBladeAlter(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)).setRegistryName(NAME_BLADEALTAR);
	public static Block block_bladestandtype1 = new BlockBladeStand(Block.Properties.create(Material.GOURD).hardnessAndResistance(1.0F,3.0F).sound(SoundType.WOOD), EnumBladeStand.STAND1).setRegistryName(NAME_BLADESTANDT1);
	public static Block block_bladestandtype2 = new BlockBladeStand(Block.Properties.create(Material.GOURD).hardnessAndResistance(1.0F,3.0F).sound(SoundType.WOOD), EnumBladeStand.STAND2).setRegistryName(NAME_BLADESTANDT2);
	public static Block block_furncedummy = new BlockDummyFurnce(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)).setRegistryName(NAME_FURNCEDUMMY);
	public static Block block_anvildummy = new BlockDummyAnvil(Block.Properties.create(Material.ANVIL, MaterialColor.IRON).hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.ANVIL)).setRegistryName(NAME_ANVILEDUMMY);
	public static Block block_airpomp = new BlockAirPomp(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.5F).sound(SoundType.WOOD)).setRegistryName(NAME_AIRPOMP);
	public static Block block_guncustomizer = new BlockGunCustomizer(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.5F,10.0F).sound(SoundType.WOOD)).setRegistryName(NAME_GUNCUSTOMIZER);


	private static Map<String,Block> blockMap;
	private static Map<String,Item> itemMap;
	private static Map<String,ResourceLocation[]> resourceMap;

	public static void init(){
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
			{put(NAME_BLADEFORGE,(new BlockItem(block_bladeforge,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_BLADEFORGE));}
			{put(NAME_BLADEALTAR,(new BlockItem(block_bladealter,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_BLADEALTAR));}
			{put(NAME_BLADESTANDT1,(new BlockItem(block_bladestandtype1,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_BLADESTANDT1));}
			{put(NAME_BLADESTANDT2,(new BlockItem(block_bladestandtype2,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_BLADESTANDT2));}
			{put(NAME_AIRPOMP,(new BlockItem(block_airpomp,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_AIRPOMP));}
			{put(NAME_FURNCEDUMMY,(new BlockItemDummy(block_furncedummy,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_FURNCEDUMMY));}
			{put(NAME_ANVILEDUMMY,(new BlockItemDummy(block_anvildummy,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_ANVILEDUMMY));}
			{put(NAME_GUNCUSTOMIZER,(new BlockItem(block_guncustomizer,new Item.Properties().group(Mod_FantomBlade.tabElmWepon))).setRegistryName(NAME_GUNCUSTOMIZER));}
		};
	}


	public static void registerBlock(final RegistryEvent.Register<Block> event){
		for (String name : NAME_LIST){
			if (blockMap.containsKey(name)) {
				event.getRegistry().register(blockMap.get(name));
			}
		}
	}

	public static void registerBlockItem(final RegistryEvent.Register<Item> event){
		for (String name : NAME_LIST){
			if (itemMap.containsKey(name)) {
				event.getRegistry().register(itemMap.get(name));
			}
		}
	}
}
