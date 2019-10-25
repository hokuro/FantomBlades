package mod.fbd.core;

import java.lang.reflect.Method;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mod.fbd.block.BlockCore;
import mod.fbd.config.ConfigValue;
import mod.fbd.creative.CreativeTabElmWepon;
import mod.fbd.entity.EntityBurret;
import mod.fbd.entity.EntityGravity;
import mod.fbd.entity.EntityLevitate;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.event.EventHandler;
import mod.fbd.gui.GuiArmorSmith;
import mod.fbd.gui.GuiAutomatic;
import mod.fbd.gui.GuiBladeAlter;
import mod.fbd.gui.GuiBladeSmith;
import mod.fbd.gui.GuiBladeforge;
import mod.fbd.gui.GuiCartridge;
import mod.fbd.gui.GuiGunCustomize;
import mod.fbd.gui.GuiRevolver;
import mod.fbd.inventory.ContainerArmorSmith;
import mod.fbd.inventory.ContainerAutomatic;
import mod.fbd.inventory.ContainerBladeAlter;
import mod.fbd.inventory.ContainerBladeSmith;
import mod.fbd.inventory.ContainerBladeforge;
import mod.fbd.inventory.ContainerCartridge;
import mod.fbd.inventory.ContainerGunCustomizer;
import mod.fbd.inventory.ContainerRevolver;
import mod.fbd.item.ItemCore;
import mod.fbd.item.guns.ItemAutomatic;
import mod.fbd.item.guns.ItemCartridge;
import mod.fbd.item.guns.ItemRevolver;
import mod.fbd.item.tire.ItemTireHagane;
import mod.fbd.item.tire.ItemTireNiji;
import mod.fbd.network.MessageHandler;
import mod.fbd.render.RenderEntityBurret;
import mod.fbd.render.RenderEntitySmith;
import mod.fbd.render.RenderTileEntityBladeAlter;
import mod.fbd.render.RenderTileEntityBladeStand;
import mod.fbd.render.RenderTileEntityBladeforge;
import mod.fbd.render.RenderVoid;
import mod.fbd.render.RendernTileEntityAirPomp;
import mod.fbd.tileentity.TileEntityAirPomp;
import mod.fbd.tileentity.TileEntityBladeAlter;
import mod.fbd.tileentity.TileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeforge;
import mod.fbd.trade.Trades;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;



@Mod(ModCommon.MOD_ID)
public class Mod_FantomBlade {
	public static final ModCommon guiInstance = new ModCommon();
	public static final ItemGroup tabElmWepon = new CreativeTabElmWepon("fantomblade");
	public static Random rnd;


	public static IItemTier HAGANE = new ItemTireHagane();
	public static IItemTier NIJI = new ItemTireNiji();
	public Mod_FantomBlade() {
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::onContainerRegistry);

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

//		RegisterVillager();
//		RegisterVillagerTrade();

	}

	private void doClientStuff(final FMLClientSetupEvent event) {
    	registRender();
    	guiHandler();
	}

	@OnlyIn(Dist.CLIENT)
	public void registRender(){
		RenderingRegistry.registerEntityRenderingHandler(EntitySmithBase.class, RenderEntitySmith::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBurret.class, RenderEntityBurret::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGravity.class, RenderVoid::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLevitate.class, RenderVoid::new);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeforge.class, new RenderTileEntityBladeforge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAirPomp.class, new RendernTileEntityAirPomp());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeStand.class, new RenderTileEntityBladeStand());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBladeAlter.class, new RenderTileEntityBladeAlter());
	}

	@OnlyIn(Dist.CLIENT)
	public void guiHandler(){
		ScreenManager.registerFactory(CONTAINER_REVOLVER, GuiRevolver::new);
		ScreenManager.registerFactory(CONTAINER_AUTOMATIC, GuiAutomatic::new);
		ScreenManager.registerFactory(CONTAINER_CARTRIDGE, GuiCartridge::new);
		ScreenManager.registerFactory(CONTAINER_BLADEFORGE, GuiBladeforge::new);
		ScreenManager.registerFactory(CONTAINER_BLADEALTER, GuiBladeAlter::new);
		ScreenManager.registerFactory(CONTAINER_GUNCUSTOMIZER, GuiGunCustomize::new);
		ScreenManager.registerFactory(CONTAINER_BLADESMITH, GuiBladeSmith::new);
		ScreenManager.registerFactory(CONTAINER_ARMORSMITH, GuiArmorSmith::new);
	}

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_REVOLVER)
	public static ContainerType<ContainerRevolver> CONTAINER_REVOLVER;
	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_AUTOMATIC)
	public static ContainerType<ContainerAutomatic> CONTAINER_AUTOMATIC;
	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_CARTRIDGE)
	public static ContainerType<ContainerCartridge> CONTAINER_CARTRIDGE;
	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_GUNCUSTOMIZER)
	public static ContainerType<ContainerGunCustomizer> CONTAINER_GUNCUSTOMIZER;

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADEFORGE)
	public static ContainerType<ContainerBladeforge> CONTAINER_BLADEFORGE;
	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADEALTER)
	public static ContainerType<ContainerBladeAlter> CONTAINER_BLADEALTER;

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADESMITH)
	public static ContainerType<ContainerBladeSmith> CONTAINER_BLADESMITH;
	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_ARMORSMITH)
	public static ContainerType<ContainerArmorSmith> CONTAINER_ARMORSMITH;


    @SubscribeEvent
	public void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int h = extraData.readInt();
			Hand hand = Hand.values()[h];
			ItemStack stack = playerInv.player.getHeldItem(hand);
			if (stack.getItem() == ItemCore.item_revolver) {
				return new ContainerRevolver(wid, playerInv, ((ItemRevolver)stack.getItem()).getInventory(stack));
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_REVOLVER));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int h = extraData.readInt();
			Hand hand = Hand.values()[h];
			ItemStack stack = playerInv.player.getHeldItem(hand);
			if (stack.getItem() == ItemCore.item_automatic) {
				return new ContainerAutomatic(wid, playerInv, ((ItemAutomatic)stack.getItem()).getInventory(stack));
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_AUTOMATIC));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int h = extraData.readInt();
			Hand hand = Hand.values()[h];
			ItemStack stack = playerInv.player.getHeldItem(hand);
			if (stack.getItem() == ItemCore.item_cartridge) {
				return new ContainerCartridge(wid, playerInv, ((ItemCartridge)stack.getItem()).getInventory(stack));
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_CARTRIDGE));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			return new ContainerGunCustomizer(wid, playerInv);
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_GUNCUSTOMIZER));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int x = extraData.readInt();
			int y = extraData.readInt();
			int z = extraData.readInt();
			TileEntity ent = playerInv.player.world.getTileEntity(new BlockPos(x,y,z));
			if (ent instanceof TileEntityBladeforge) {
				return new ContainerBladeforge(wid, playerInv, (TileEntityBladeforge)ent);
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADEFORGE));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int x = extraData.readInt();
			int y = extraData.readInt();
			int z = extraData.readInt();
			TileEntity ent = playerInv.player.world.getTileEntity(new BlockPos(x,y,z));
			if (ent instanceof TileEntityBladeAlter) {
				return new ContainerBladeAlter(wid, playerInv, (TileEntityBladeAlter)ent);
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADEALTER));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int id = extraData.readInt();
			Entity ent = playerInv.player.world.getEntityByID(id);
			if (ent instanceof EntityBladeSmith) {
				return new ContainerBladeSmith(wid, playerInv, ((EntityBladeSmith)ent).getInventory());
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_BLADESMITH));

		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int id = extraData.readInt();
			Entity ent = playerInv.player.world.getEntityByID(id);
			if (ent instanceof EntityArmorSmith) {
				return new ContainerArmorSmith(wid, playerInv, ((EntityArmorSmith)ent).getInventory());
			}
			return null;
		}).setRegistryName(ModCommon.MOD_ID + ":" + ModCommon.MOD_CONTAINERID_ARMORSMITH));
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
            BlockCore.registerBlockItem(itemRegistryEvent);
            ItemCore.register(itemRegistryEvent);
        }


        public static EntityType<EntityBladeSmith> BLADESMITH;
        public static EntityType<EntityArmorSmith> ARMORSMITH;
        public static EntityType<EntityBurret> BURRET;
        public static EntityType<EntityGravity> GRAVITY;
        public static EntityType<EntityLevitate> LEVITATE;

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> etRegistryEvent){
        	BLADESMITH = EntityType.Builder.<EntityBladeSmith>create(EntityBladeSmith::new, EntityClassification.CREATURE)
        				.setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(0.6F, 1.95F)
        				.setCustomClientFactory(EntityBladeSmith::new).build(ModCommon.MOD_ID + ":" + EntityBladeSmith.NAME);
        	BLADESMITH.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityBladeSmith.NAME));
        	etRegistryEvent.getRegistry().register(BLADESMITH);

        	ARMORSMITH = EntityType.Builder.<EntityArmorSmith>create(EntityArmorSmith::new, EntityClassification.CREATURE)
    				.setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(0.6F, 1.95F)
    				.setCustomClientFactory(EntityArmorSmith::new).build(ModCommon.MOD_ID + ":" + EntityArmorSmith.NAME);
        	ARMORSMITH.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityArmorSmith.NAME));
        	etRegistryEvent.getRegistry().register(ARMORSMITH);

        	BURRET = EntityType.Builder.<EntityBurret>create(EntityBurret::new, EntityClassification.MISC)
        			 .setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(0.5F, 0.5F)
        			 .setCustomClientFactory(EntityBurret::new).build(ModCommon.MOD_ID + ":" + EntityBurret.NAME);
        	BURRET.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityBurret.NAME));
        	etRegistryEvent.getRegistry().register(BURRET);


            GRAVITY = EntityType.Builder.<EntityGravity>create(EntityGravity::new, EntityClassification.MISC)
       			 .setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(1.0F, 1.0F)
       			 .setCustomClientFactory(EntityGravity::new).build(ModCommon.MOD_ID + ":" + EntityBurret.NAME);
            GRAVITY.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityGravity.NAME));
        	etRegistryEvent.getRegistry().register(GRAVITY);

            LEVITATE = EntityType.Builder.<EntityLevitate>create(EntityLevitate::new, EntityClassification.MISC)
       			 .setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(false).size(1.0F, 1.0F)
       			 .setCustomClientFactory(EntityLevitate::new).build(ModCommon.MOD_ID + ":" + EntityLevitate.NAME);
            LEVITATE.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,EntityLevitate.NAME));
        	etRegistryEvent.getRegistry().register(LEVITATE);
        }

    	public static TileEntityType<?> BLADEFORGE;
    	public static TileEntityType<?> AIRPOMP;
    	public static TileEntityType<?> BLADESTAND;
    	public static TileEntityType<?> BLADEALTER;

        @SubscribeEvent
        public static void onTERegistyr(final RegistryEvent.Register<TileEntityType<?>> teRegistryEvent){
        	BLADEFORGE = TileEntityType.Builder.create(TileEntityBladeforge::new, BlockCore.block_bladeforge).build(null);
        	BLADEFORGE.setRegistryName(ModCommon.MOD_ID, TileEntityBladeforge.NAME);
        	teRegistryEvent.getRegistry().register(BLADEFORGE);

        	AIRPOMP = TileEntityType.Builder.create(TileEntityAirPomp::new, BlockCore.block_airpomp).build(null);
        	AIRPOMP.setRegistryName(ModCommon.MOD_ID, TileEntityAirPomp.NAME);
        	teRegistryEvent.getRegistry().register(AIRPOMP);

        	BLADESTAND = TileEntityType.Builder.create(TileEntityBladeStand::new, BlockCore.block_bladestandtype1, BlockCore.block_bladestandtype2).build(null);
        	BLADESTAND.setRegistryName(ModCommon.MOD_ID, TileEntityBladeStand.NAME);
        	teRegistryEvent.getRegistry().register(BLADESTAND);

        	BLADEALTER = TileEntityType.Builder.create(TileEntityBladeAlter::new, BlockCore.block_bladealter).build(null);
        	BLADEALTER.setRegistryName(ModCommon.MOD_ID, TileEntityBladeAlter.NAME);
        	teRegistryEvent.getRegistry().register(BLADEALTER);
        }

        @SubscribeEvent
        public static void onSoundRegistyr(final RegistryEvent.Register<SoundEvent> teRegistryEvent){
    		teRegistryEvent.getRegistry().register(SoundManager.sound_smith_hart);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_smith_damage);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_smith_dead);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_smith_work);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_gunshot);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_reload);
    		teRegistryEvent.getRegistry().register(SoundManager.sound_gun_noburret);
        }


        public static PointOfInterestType INTAREST_GUNSMITH;
        public static PointOfInterestType INTAREST_SCRAPPER;
        @SubscribeEvent
        public static void onVillagerPointOfIntarestType(final RegistryEvent.Register<PointOfInterestType> event) {
        	INTAREST_GUNSMITH = new PointOfInterestType("gunsmith", ImmutableSet.copyOf(BlockCore.block_guncustomizer.getStateContainer().getValidStates()),
        			1, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER, 1);
        	INTAREST_GUNSMITH.setRegistryName(ModCommon.MOD_ID + ":" + "intarest_gunsmith");
        	event.getRegistry().register(INTAREST_GUNSMITH);

        	INTAREST_SCRAPPER = new PointOfInterestType("scrapper", ImmutableSet.copyOf(BlockCore.block_bladeforge.getStateContainer().getValidStates()),
        			1, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER, 1);
        	INTAREST_SCRAPPER.setRegistryName(ModCommon.MOD_ID + ":" + "intarest_scrapper");
        	event.getRegistry().register(INTAREST_SCRAPPER);
        	try {
        		Method func_221052_a = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
        		func_221052_a.invoke(null, INTAREST_GUNSMITH);
        		func_221052_a.invoke(null, INTAREST_SCRAPPER);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }

        public static VillagerProfession PROFESSION_GUNSMITH;
        public static VillagerProfession PROFESSION_SCRAPPER;
        @SubscribeEvent
        public static void onVillagerProfession(final RegistryEvent.Register<VillagerProfession> event) {
        	PROFESSION_GUNSMITH = new VillagerProfession("gunsmith", INTAREST_GUNSMITH, ImmutableSet.of(), ImmutableSet.of());
        	PROFESSION_GUNSMITH.setRegistryName(ModCommon.MOD_ID + ":" + "profession_gunsmith");
        	event.getRegistry().register(PROFESSION_GUNSMITH);

        	PROFESSION_SCRAPPER = new VillagerProfession("scrapper", INTAREST_SCRAPPER, ImmutableSet.of(), ImmutableSet.of());
        	PROFESSION_SCRAPPER.setRegistryName(ModCommon.MOD_ID + ":" + "profession_scrapper");
        	event.getRegistry().register(PROFESSION_SCRAPPER);

        	registerTrade();
        }

        public static void registerTrade() {
        	VillagerTrades.field_221239_a.put(PROFESSION_GUNSMITH, makeMap(
        			ImmutableMap.of(
        					1, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(Items.EMERALD, 30, ItemCore.item_automatic, 1, 64, 5),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 30, ItemCore.item_revolver, 1, 64, 5),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 30, ItemCore.item_musket, 1, 64, 5),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret, 1, 22, 1)
        					},
        					2, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_blow, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_drain, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_draw, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_explode, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_flame, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_heal, 8, 44, 3),
        					},
        					3, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_golem, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_snowman, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_revitate, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_gravity, 8, 44, 3),
        					},
        					4, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret_byako, 8, 22, 2),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret_suzaku, 8, 22, 2),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret_kirin, 8, 22, 2),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret_seiryu, 8, 22, 2),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_burret_genbu, 8, 22, 2)
        					},
        					5, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(Items.EMERALD, 30, ItemCore.item_burret_assasination, 8, 64, 5),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_teleport, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_thunder, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_wither, 8, 44, 3),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 20, ItemCore.item_burret_silver, 8, 44, 3),
        							})));


        	VillagerTrades.field_221239_a.put(PROFESSION_SCRAPPER, makeMap(
        			ImmutableMap.of(
        					1, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 10, Items.EMERALD, 1, 24, 1),
        							new Trades.ItemForItemToTrade(Items.EMERALD, 10, ItemCore.item_steel, 8, 24, 1),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 10, ItemCore.item_steel, 1, 32, 1),
        					},
        					2, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 32, ItemCore.item_gold_bighammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 32, ItemCore.item_gold_smallhammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 32, ItemCore.item_stone_bighammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 32, ItemCore.item_stone_smallhammer, 1, 64, 2),
        					},
        					3, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 36, ItemCore.item_diamond_bighammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 36, ItemCore.item_diamond_smallhammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 36, ItemCore.item_iron_bighammer, 1, 64, 2),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 36, ItemCore.item_iron_smallhammer, 1, 64, 2),
        					},
        					4, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 44, ItemCore.item_steel_bighammer, 1, 64, 5),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 44, ItemCore.item_steel_smallhammer, 1, 64, 5),
        					},
        					5, new VillagerTrades.ITrade[]{
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 44, ItemCore.item_bilder_smallhammer, 1, 64, 5),
        							new Trades.ItemForItemToTrade(ItemCore.item_ironscrap, 44, ItemCore.item_bilder_bighammer, 1, 64, 5),
        							})));
        }

        private static Int2ObjectMap<VillagerTrades.ITrade[]> makeMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> trade) {
            return new Int2ObjectOpenHashMap<>(trade);
         }

	}



}
