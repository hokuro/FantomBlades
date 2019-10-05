package mod.fbd.core;

public class VillagerRegister {
//	public static final ResourceLocation VILLAGER_GUNSMITH_NAME = new ResourceLocation(ModCommon.MOD_ID,"villagergunsmith");
//	public static final ResourceLocation VILLAGER_GUNSMITH_TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/villagergunsmith.png");
//	public static final ResourceLocation VILLAGER_GUNSMITHZOMBIE_TEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/villagergunsmithzombi.png");
//	public static VillagerProfession gunSmith;
//	public static void RegisterVillager() {
//		gunSmith = new ProfessionGunSmith(VILLAGER_GUNSMITH_NAME.toString(), VILLAGER_GUNSMITH_TEXTURE.toString(), VILLAGER_GUNSMITHZOMBIE_TEXTURE.toString());
//		VillagerCareer carry_gunsmith = new VillagerCareer(gunSmith, "villager.gunsmith");
//		carry_gunsmith.addTrade(1, new EntityVillager.ITradeList[]{
//				new EntityVillager.EmeraldForItems(ItemCore.item_steel, new EntityVillager.PriceInfo(8,12)),
//				new EntityVillager.EmeraldForItems(Items.IRON_INGOT, new EntityVillager.PriceInfo(12,16)),
//				new EntityVillager.ListItemForEmeralds(Items.IRON_INGOT, new EntityVillager.PriceInfo(-4,-6)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_steel, new EntityVillager.PriceInfo(-2,-6))
//		});
//		carry_gunsmith.addTrade(2, new EntityVillager.ITradeList[]{
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_revolver, new EntityVillager.PriceInfo(-30,-40)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_autmatic, new EntityVillager.PriceInfo(-30,-40)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_cartridge, new EntityVillager.PriceInfo(-10,-15))
//		});
//		carry_gunsmith.addTrade(3, new EntityVillager.ITradeList[]{
//				new EntityVillager.ListItemForEmeralds(new ItemStack(BlockCore.block_guncustomizer), new EntityVillager.PriceInfo(-40,-64))
//		});
//		VillagerCareer carry_burretsmith = new VillagerCareer(gunSmith, "villager.burretsmith");
//		carry_burretsmith.addTrade(1, new EntityVillager.ITradeList[]{
//				new EntityVillager.EmeraldForItems(Items.GUNPOWDER, new EntityVillager.PriceInfo(6,10)),
//				new EntityVillager.ListItemForEmeralds(Items.GUNPOWDER, new EntityVillager.PriceInfo(-4,-6)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret, new EntityVillager.PriceInfo(-12,-24))
//		});
//		carry_burretsmith.addTrade(2, new EntityVillager.ITradeList[]{
//				new EntityVillager.EmeraldForItems(ItemCore.item_gunpwoder_flame, new EntityVillager.PriceInfo(6,10)),
//				new EntityVillager.EmeraldForItems(ItemCore.item_burret_drain, new EntityVillager.PriceInfo(6,10)),
//				new EntityVillager.EmeraldForItems(ItemCore.item_burret_teleport, new EntityVillager.PriceInfo(6,10)),
//				new EntityVillager.EmeraldForItems(ItemCore.item_burret_explode, new EntityVillager.PriceInfo(6,10)),
//		});
//		carry_burretsmith.addTrade(3, new EntityVillager.ITradeList[]{
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_teleport, new EntityVillager.PriceInfo(-12,-24)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_explode, new EntityVillager.PriceInfo(-12,-24)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_draw, new EntityVillager.PriceInfo(-12,-24)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_flame, new EntityVillager.PriceInfo(-12,-24))
//		});
//		carry_burretsmith.addTrade(4, new EntityVillager.ITradeList[]{
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_thunder, new EntityVillager.PriceInfo(-12,-24)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_drain, new EntityVillager.PriceInfo(-12,-24)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_blow, new EntityVillager.PriceInfo(-12,-24))
//		});
//		carry_burretsmith.addTrade(5, new EntityVillager.ITradeList[]{
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_golem, new EntityVillager.PriceInfo(-8,-12)),
//				new EntityVillager.ListItemForEmeralds(ItemCore.item_burret_snowman, new EntityVillager.PriceInfo(-8,-12))
//		});
//		((ProfessionGunSmith)gunSmith).init(carry_gunsmith,carry_burretsmith);
//		ForgeRegistries.VILLAGER_PROFESSIONS.register(gunSmith);
//		VillagerRegistry.instance().registerVillageCreationHandler(new VillageCrationHandler());
//		StructureIO.registerStructureComponent(ComponentVillageGunSmithHouse.class,"VCHGS");
//	}
//
//	public void RegisterVillagerTrade(){
//		// トレード追加
//		// 鍛冶屋の職業を取り出す
//		VillagerProfession prof = VillagerRegistry.getById(3);
//		// 防具職人
//		VillagerCareer c0 = prof.getCareer(0);
//		c0.addTrade(1, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(4, 7)),
//				new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
//		});
//		c0.addTrade(2, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_HELMET, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_CHESTPLATE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 14)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_LEGGINGS,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(10, 14)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_BOOTS,ItemCore.item_ironscrap,  new EntityVillager.PriceInfo(8, 12))
//		});
//		c0.addTrade(3, new ITradeList[]{
//			new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_BOOTS, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
//			new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_LEGGINGS, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 16)),
//			new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_HELMET, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 16)),
//			new VillagerTrade.ListItemForAnotherItem(Items.CHAINMAIL_CHESTPLATE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(13, 17))
//		});
//
//		// 武器職人
//		VillagerCareer c1 = prof.getCareer(1);
//		c1.addTrade(1, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.COAL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(16, 24)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_AXE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(9, 11)),
//				new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
//		});
//		c1.addTrade(2, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(4, 7)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_SWORD, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(12, 13))
//		});
//
//
//		// ツール職人
//		VillagerCareer c2 = prof.getCareer(2);
//		c2.addTrade(1, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.COAL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(16, 24)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_SHOVEL, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(8, 12)),
//				new VillagerTrade.ListItemForAnotherItem(ItemCore.item_steel,new ItemStack(ItemCore.item_tamahagane,1), new EntityVillager.PriceInfo(6, 12))
//		});
//		c2.addTrade(2, new ITradeList[]{
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_INGOT, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(10, 15)),
//				new VillagerTrade.ListItemForAnotherItem(Items.IRON_PICKAXE, ItemCore.item_ironscrap, new EntityVillager.PriceInfo(14, 17))
//		});
//	}
}
