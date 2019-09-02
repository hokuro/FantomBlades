package mod.fbd.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.fbd.block.BlockCore;
import mod.fbd.config.ConfigValue;
import mod.fbd.core.log.ModLog;
import mod.fbd.creative.CreativeTabElmWepon;
import mod.fbd.entity.EntityBurret;
import mod.fbd.entity.ProfessionGunSmith;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.equipmenteffect.EnchantmentCore;
import mod.fbd.gui.GuiArmorSmith;
import mod.fbd.gui.GuiAutomatic;
import mod.fbd.gui.GuiBladeAlter;
import mod.fbd.gui.GuiBladeSmith;
import mod.fbd.gui.GuiBladeforge;
import mod.fbd.gui.GuiCartridge;
import mod.fbd.gui.GuiGunCustomize;
import mod.fbd.gui.GuiRevolver;
import mod.fbd.item.ItemAutomatic;
import mod.fbd.item.ItemCartridge;
import mod.fbd.item.ItemCore;
import mod.fbd.item.ItemRevolver;
import mod.fbd.network.MessageHandler;
import mod.fbd.potion.PotionCore;
import mod.fbd.render.RenderEntityBladeSmith;
import mod.fbd.render.RenderEntityBurret;
import mod.fbd.render.RenderTileEntityBladeAlter;
import mod.fbd.render.RenderTileEntityBladeStand;
import mod.fbd.render.RenderTileEntityBladeforge;
import mod.fbd.render.RendernTileEntityAirPomp;
import mod.fbd.resource.ResourceManager;
import mod.fbd.tileentity.TileEntityAirPomp;
import mod.fbd.tileentity.TileEntityBladeAlter;
import mod.fbd.tileentity.TileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeforge;
import mod.fbd.trade.VillagerTrade;
import mod.fbd.util.EnumHelper;
import mod.fbd.world.village.ComponentVillageGunSmithHouse;
import mod.fbd.world.village.VillageCrationHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureIO;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(ModCommon.MOD_ID)
public class Mod_FantomBlade {
	public static final ModCommon guiInstance = new ModCommon();
	public static final ItemGroup tabElmWepon = new CreativeTabElmWepon("Elm Wepon");
	public static Random rnd;

	private static ResourceManager texturemanager;

	public static ItemTier HAGANE;
	public static ItemTier NIJI;
	public static ArmorMaterial ARMORHAGANE;
	public static ArmorMaterial AROMORSEIRYU;
	public static ArmorMaterial AROMORSUZAKU;
	public static ArmorMaterial AROMORGENBU;
	public static ArmorMaterial AROMORBYAKO;
	public static ArmorMaterial AROMORKIRIN;
	public static ArmorMaterial ARMORNIJI;

	public static List<Enchantment> swordEnchantments = new ArrayList<Enchantment>();
	public static List<PotionEffect> swordPotion = new ArrayList<PotionEffect>();

	   public Mod_FantomBlade() {
	        // Register the doClientStuff method for modloading
	        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

	        // コンフィグ読み込み
	    	ModLoadingContext.get().
	        registerConfig(
	        		net.minecraftforge.fml.config.ModConfig.Type.COMMON,
	        		ConfigValue.spec);

	    	BlockCore.init();
	    	ItemCore.init();

	    	// メッセージ登録
	    	MessageHandler.register();

	        MinecraftForge.EVENT_BUS.register(this);
	        MinecraftForge.EVENT_BUS.register(new EventHandler());

			HAGANE = EnumHelper.addToolMaterial("HAGANE", 3, 1000, 7.5F, 4.0F, 30);
			NIJI = EnumHelper.addToolMaterial("NIJI", 3, 2000, 20.0F, 10.0F, 50);
			ARMORHAGANE = EnumHelper.addArmorMaterial("HAGANE", ModCommon.MOD_ID + ":" + "hagane", 30, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
			AROMORSEIRYU = EnumHelper.addArmorMaterial("SEIRYU", ModCommon.MOD_ID + ":" + "seiryu", 40, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			AROMORSUZAKU = EnumHelper.addArmorMaterial("SUZAKU", ModCommon.MOD_ID + ":" + "suzaku", 40, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			AROMORGENBU = EnumHelper.addArmorMaterial("GENBU", ModCommon.MOD_ID + ":" + "genbu", 40, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			AROMORBYAKO = EnumHelper.addArmorMaterial("BYAKO", ModCommon.MOD_ID + ":" + "byako", 40, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			AROMORKIRIN = EnumHelper.addArmorMaterial("KIRIN", ModCommon.MOD_ID + ":" + "kirin", 40, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			ARMORNIJI = EnumHelper.addArmorMaterial("NIJI", ModCommon.MOD_ID + ":" + "niji", 50, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);
			RegisterVillager();
			RegisterVillagerTrade();

	    }

	    private void doClientStuff(final FMLClientSetupEvent event) {
	    	registRender();
	    	guiHandler();

	    }

		@OnlyIn(Dist.CLIENT)
		public void registRender(){
			RenderingRegistry.registerEntityRenderingHandler(EntitySmithBase.class, RenderEntityBladeSmith::new);
			RenderingRegistry.registerEntityRenderingHandler(EntityBurret.class, RenderEntityBurret::new);

			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeforge.class, new RenderTileEntityBladeforge());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAirPomp.class, new RendernTileEntityAirPomp());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeStand.class, new RenderTileEntityBladeStand());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeAlter.class, new RenderTileEntityBladeAlter());
		}

		@OnlyIn(Dist.CLIENT)
		public void guiHandler(){
	        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> (openContainer) -> {
	    		ResourceLocation location = openContainer.getId();
	    		EntityPlayer player = Minecraft.getInstance().player;
	    		World world = Minecraft.getInstance().world;

	    		if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_BLADEFORGE)){
	    			int x = openContainer.getAdditionalData().readInt();
	        		int y = openContainer.getAdditionalData().readInt();
	        		int z = openContainer.getAdditionalData().readInt();

	    			TileEntity ent = world.getTileEntity(new BlockPos(x,y,z));
	    			if((ent instanceof TileEntityBladeforge)){
	    				return new GuiBladeforge(player, (IInventory)ent);
	    			}

	    		}else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_BLADEALTER)){
	    			int x = openContainer.getAdditionalData().readInt();
	        		int y = openContainer.getAdditionalData().readInt();
	        		int z = openContainer.getAdditionalData().readInt();
	        		TileEntity ent = world.getTileEntity(new BlockPos(x,y,z));
	    			if (ent instanceof TileEntityBladeAlter){
	    				return new GuiBladeAlter(player, (IInventory)ent);
	    			}

	    		}else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_REVOLVER)){
	    			int hand = openContainer.getAdditionalData().readInt();
	        		ItemStack gunStack;
	    			if (EnumHand.OFF_HAND == EnumHand.values()[hand]){
	    				gunStack = player.getHeldItemOffhand();
	    			}else{
	    				gunStack = player.getHeldItemMainhand();
	    			}
	    			if(!gunStack.isEmpty() && gunStack.getItem() instanceof ItemRevolver){
	    				return new GuiRevolver(player,((ItemRevolver)gunStack.getItem()).getInventory(gunStack));
	    			}
	    		} else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_AUTOMATIC)){
	    			int hand = openContainer.getAdditionalData().readInt();
	        		ItemStack gunStack;
	    			if (EnumHand.OFF_HAND == EnumHand.values()[hand]){
	    				gunStack = player.getHeldItemOffhand();
	    			}else{
	    				gunStack = player.getHeldItemMainhand();
	    			}
	    			if(!gunStack.isEmpty() && gunStack.getItem() instanceof ItemAutomatic){
	    				return new GuiAutomatic(player, ((ItemAutomatic)gunStack.getItem()).getInventory(gunStack));
	    			}
	    		} else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_CARTRIDGE) ){
	    			int hand = openContainer.getAdditionalData().readInt();
	    			ItemStack gunStack;
	    			if (EnumHand.OFF_HAND == EnumHand.values()[hand]) {
	    				gunStack = player.getHeldItemOffhand();
	    			}else {
	    				gunStack = player.getHeldItemMainhand();
	    			}
	    			if(!gunStack.isEmpty() && gunStack.getItem() instanceof ItemCartridge){
	    				return new GuiCartridge(player, ((ItemCartridge)gunStack.getItem()).getInventory(gunStack));
	    			}
	    		} else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_GUNCUSTOMAIZER) ){
	    			int x = openContainer.getAdditionalData().readInt();
	        		int y = openContainer.getAdditionalData().readInt();
	        		int z = openContainer.getAdditionalData().readInt();
	        		return new GuiGunCustomize(player, world);
	    		} else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_BLADESMITH) ){
	        		int entid = openContainer.getAdditionalData().readInt();
	    			ModLog.log().debug("client gui entity id:"+entid);
	    			Entity entity = world.getEntityByID(entid);
	    			if (entity instanceof EntityBladeSmith){
	    				return new GuiBladeSmith(player, (EntityBladeSmith)entity);
	    			}
	    		} else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.GUI_ID_ARMORSMITH) ){
	        		int entid = openContainer.getAdditionalData().readInt();
	    			ModLog.log().debug("client gui entity id:"+entid);
	    			Entity entity = world.getEntityByID(entid);
	    			if (entity instanceof EntityArmorSmith){
	    				return new GuiArmorSmith(player, (EntityArmorSmith)entity);
	    			}
	    		}
	    		return null;
	        });
		}

		@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	    public static class RegistryEvents {
	        @SubscribeEvent
	        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
	            // register a new block here
	        	BlockCore.registerBlock(blockRegistryEvent);
	        }

	        @SubscribeEvent
	        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
	            BlockCore.registerItemBlock(itemRegistryEvent);
	            ItemCore.register(itemRegistryEvent);
	        }


	        public static EntityType<EntityBladeSmith> BLADESMITH;
	        public static EntityType<EntityArmorSmith> ARMORSMITH;
	        public static EntityType<EntityBurret> BURRET;

	        @SubscribeEvent
	        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> etRegistryEvent){
	        	BLADESMITH = EntityType.Builder.create(EntityBladeSmith.class, EntityBladeSmith::new).tracker(10, 1, false).build(ModCommon.MOD_ID + ":" + EntityBladeSmith.NAME);
	        	BLADESMITH.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityBladeSmith.NAME));

	        	ARMORSMITH = EntityType.Builder.create(EntityArmorSmith.class, EntityArmorSmith::new).tracker(10, 1, false).build(ModCommon.MOD_ID + ":" + EntityArmorSmith.NAME);
	        	ARMORSMITH.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityArmorSmith.NAME));

	        	BURRET = EntityType.Builder.create(EntityBurret.class, EntityBurret::new).tracker(10, 1, false).build(ModCommon.MOD_ID + ":" + EntityBurret.NAME);
	        	BURRET.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityBurret.NAME));

	        	etRegistryEvent.getRegistry().registerAll(BLADESMITH, ARMORSMITH, BURRET);
	        }

	    	public static TileEntityType<?> BLADEFORGE;
	    	public static TileEntityType<?> AIRPOMP;
	    	public static TileEntityType<?> BLADESTAND;
	    	public static TileEntityType<?> BLADEALTER;

	        @SubscribeEvent
	        public static void onTERegistyr(final RegistryEvent.Register<TileEntityType<?>> teRegistryEvent){
	        	BLADEFORGE = TileEntityType.Builder.create(TileEntityBladeforge::new).build(null);
	        	BLADEFORGE.setRegistryName(ModCommon.MOD_ID, TileEntityBladeforge.NAME);
	        	teRegistryEvent.getRegistry().register(BLADEFORGE);

	        	AIRPOMP = TileEntityType.Builder.create(TileEntityAirPomp::new).build(null);
	        	AIRPOMP.setRegistryName(ModCommon.MOD_ID, TileEntityAirPomp.NAME);
	        	teRegistryEvent.getRegistry().register(AIRPOMP);

	        	BLADESTAND = TileEntityType.Builder.create(TileEntityBladeStand::new).build(null);
	        	BLADESTAND.setRegistryName(ModCommon.MOD_ID, TileEntityBladeStand.NAME);
	        	teRegistryEvent.getRegistry().register(BLADESTAND);

	        	BLADEALTER = TileEntityType.Builder.create(TileEntityBladeAlter::new).build(null);
	        	BLADEALTER.setRegistryName(ModCommon.MOD_ID, TileEntityBladeAlter.NAME);
	        	teRegistryEvent.getRegistry().register(BLADEALTER);
	        }

	        @SubscribeEvent
	        public static void onSoundRegistyr(final RegistryEvent.Register<SoundEvent> teRegistryEvent){
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_bladesmith_hart);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_bladesmith_damage);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_bladesmith_dead);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_bladesmith_work);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_gunshot);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_reload);
	    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_noburret);
	        }

	        @SubscribeEvent
	        public static void onEnchantRegistyr(final RegistryEvent.Register<Enchantment> teRegistryEvent){
	    		EnchantmentCore.registerEnchantments(teRegistryEvent);
	        }

	        @SubscribeEvent
	        public static void onPotionTypeRegistyr(final RegistryEvent.Register<PotionType> teRegistryEvent){
	    		PotionCore.registerType(teRegistryEvent);
	        }

	        @SubscribeEvent
	        public static void onPotionRegistyr(final RegistryEvent.Register<Potion> teRegistryEvent){
	        	PotionCore.registerPotion(teRegistryEvent);
	        }
	    }

		/**
		 * 指定した名称のモブのクラスを取得する
		 * @param name クラス名
		 * @return クラス
		 */
		public static String checkModelName(String name){
			int idx=-1;
			if ( ((idx = modelNames.indexOf(name)) >= 0)){
				return modelNames.get(idx);
			}
			return null;
		}

		public static ResourceManager TextureManager(){
			if ( texturemanager == null){
				texturemanager = new ResourceManager();
				try{
				texturemanager.initResource();
				}catch(Exception ex){
					ModLog.log().info("Can't Use CusutomTexture");
				}
			}
			return texturemanager;
		}

		public static final List<String> modelNames = new ArrayList<String>(){
			{add("bladesmith");}
			{add("normalblade");}
		};


		public static final ResourceLocation VILLAGER_GUNSMITH_NAME = new ResourceLocation(ModCommon.MOD_ID,"villagergunsmith");
		public static final ResourceLocation VILLAGER_GUNSMITH_TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/villagergunsmith.png");
		public static final ResourceLocation VILLAGER_GUNSMITHZOMBIE_TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/villagergunsmithzombi.png");
		public static VillagerProfession gunSmith;
		public static void RegisterVillager() {
			gunSmith = new ProfessionGunSmith(VILLAGER_GUNSMITH_NAME.toString(), VILLAGER_GUNSMITH_TEXTURE.toString(), VILLAGER_GUNSMITHZOMBIE_TEXTURE.toString());
			VillagerCareer carry_gunsmith = new VillagerCareer(gunSmith, "villager.gunsmith");
			carry_gunsmith.addTrade(1, new EntityVillager.ITradeList[]{
					new EntityVillager.EmeraldForItems(ItemCore.item_steel, new EntityVillager.PriceInfo(8,12)),
					new EntityVillager.EmeraldForItems(Items.IRON_INGOT, new EntityVillager.PriceInfo(12,16)),
					new EntityVillager.ListItemForEmeralds(Items.IRON_INGOT, new EntityVillager.PriceInfo(-4,-6)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_steel, new EntityVillager.PriceInfo(-2,-6))
			});
			carry_gunsmith.addTrade(2, new EntityVillager.ITradeList[]{
					new EntityVillager.ListItemForEmeralds(ItemCore.item_revolver, new EntityVillager.PriceInfo(-30,-40)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_autmatic, new EntityVillager.PriceInfo(-30,-40)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_cartridge, new EntityVillager.PriceInfo(-10,-15))
			});
			carry_gunsmith.addTrade(3, new EntityVillager.ITradeList[]{
					new EntityVillager.ListItemForEmeralds(new ItemStack(BlockCore.block_guncustomizer), new EntityVillager.PriceInfo(-40,-64))
			});
			VillagerCareer carry_burretsmith = new VillagerCareer(gunSmith, "villager.burretsmith");
			carry_burretsmith.addTrade(1, new EntityVillager.ITradeList[]{
					new EntityVillager.EmeraldForItems(Items.GUNPOWDER, new EntityVillager.PriceInfo(6,10)),
					new EntityVillager.ListItemForEmeralds(Items.GUNPOWDER, new EntityVillager.PriceInfo(-4,-6)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret, new EntityVillager.PriceInfo(-12,-24))
			});
			carry_burretsmith.addTrade(2, new EntityVillager.ITradeList[]{
					new EntityVillager.EmeraldForItems(ItemCore.item_gunpwoder_flame, new EntityVillager.PriceInfo(6,10)),
					new EntityVillager.EmeraldForItems(ItemCore.item_burret_drain, new EntityVillager.PriceInfo(6,10)),
					new EntityVillager.EmeraldForItems(ItemCore.item_burret_teleport, new EntityVillager.PriceInfo(6,10)),
					new EntityVillager.EmeraldForItems(ItemCore.item_burret_explode, new EntityVillager.PriceInfo(6,10)),
			});
			carry_burretsmith.addTrade(3, new EntityVillager.ITradeList[]{
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_teleport, new EntityVillager.PriceInfo(-12,-24)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_explode, new EntityVillager.PriceInfo(-12,-24)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_draw, new EntityVillager.PriceInfo(-12,-24)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_flame, new EntityVillager.PriceInfo(-12,-24))
			});
			carry_burretsmith.addTrade(4, new EntityVillager.ITradeList[]{
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_thunder, new EntityVillager.PriceInfo(-12,-24)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_drain, new EntityVillager.PriceInfo(-12,-24)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_blow, new EntityVillager.PriceInfo(-12,-24))
			});
			carry_burretsmith.addTrade(5, new EntityVillager.ITradeList[]{
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_golem, new EntityVillager.PriceInfo(-8,-12)),
					new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_snowman, new EntityVillager.PriceInfo(-8,-12))
			});
			((ProfessionGunSmith)gunSmith).init(carry_gunsmith,carry_burretsmith);
			ForgeRegistries.VILLAGER_PROFESSIONS.register(gunSmith);
			VillagerRegistry.instance().registerVillageCreationHandler(new VillageCrationHandler());
			StructureIO.registerStructureComponent(ComponentVillageGunSmithHouse.class,"VCHGS");
		}

		public void RegisterVillagerTrade(){
			// トレード追加
			// 鍛冶屋の職業を取り出す
			VillagerProfession prof = VillagerRegistry.getById(3);
			// 防具職人
			VillagerCareer c0 = prof.getCareer(0);
			c0.addTrade(1, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(4, 7)),
					new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
			});
			c0.addTrade(2, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_HELMET, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_CHESTPLATE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 14)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_LEGGINGS,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(10, 14)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_BOOTS,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(8, 12))
			});
			c0.addTrade(3, new ITradeList[]{
				new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_BOOTS, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
				new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_LEGGINGS, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 16)),
				new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_HELMET, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 16)),
				new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_CHESTPLATE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(13, 17))
			});

			// 武器職人
			VillagerCareer c1 = prof.getCareer(1);
			c1.addTrade(1, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.COAL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(16, 24)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_AXE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(9, 11)),
					new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
			});
			c1.addTrade(2, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(4, 7)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_SWORD, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(12, 13))
			});


			// ツール職人
			VillagerCareer c2 = prof.getCareer(2);
			c2.addTrade(1, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.COAL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(16, 24)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_SHOVEL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
					new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
			});
			c2.addTrade(2, new ITradeList[]{
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 15)),
					new VillagerTrade.ListItemForAnotherItem(Items.IRON_PICKAXE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(14, 17))
			});
		}

		public static void createSwordEnchant() {
			ItemStack test = new ItemStack(Items.DIAMOND_SWORD,1);
			IRegistry.field_212628_q.forEach((enc)->{
				if (enc.canApply(test)) {
					swordEnchantments.add(enc);
				}
			});
		}

		public static void createSwordPotion() {
			IRegistry.field_212621_j.forEach((ptype)->{
				swordPotion.addAll(ptype.getEffects());
			});
		}
}
