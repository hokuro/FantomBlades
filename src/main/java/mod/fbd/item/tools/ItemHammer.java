package mod.fbd.item.tools;

import java.util.Set;

import com.google.common.collect.Sets;

import mod.fbd.core.ModCommon;
import mod.fbd.item.ItemCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;

public class ItemHammer extends ToolItem {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
			Blocks.OAK_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.BOOKSHELF, Blocks.OAK_WOOD, Blocks.SPRUCE_WOOD, Blocks.BIRCH_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD, Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG, Blocks.CHEST, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.OAK_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.ACACIA_PRESSURE_PLATE,
			Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX,
			Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);

	protected EnumHammer hammersize;

	public ItemHammer(IItemTier tier, float attackDamageIn, float attackSpeedIn, EnumHammer size, Item.Properties property) {
		super(attackDamageIn,attackDamageIn,tier,EFFECTIVE_ON,property);
		hammersize = size;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// iビルダーハンマーならシルクタッチのエンチャント追加
		if (stack.getItem() == ItemCore.item_bilder_bighammer || stack.getItem() == ItemCore.item_bilder_smallhammer) {
			int lv = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
			if (lv == 0) {
				stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
			}
		}
	}

	@Override
	public boolean canHarvestBlock(BlockState blockIn) {
		Block block = blockIn.getBlock();
		int i = this.getTier().getHarvestLevel();
		return i >= blockIn.getHarvestLevel();
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		boolean ret = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		if (!worldIn.isRemote) {
			Vec3i[] loop;
			if (hammersize == EnumHammer.BIG) {
				loop = ModCommon.SearchBlock5to5;
			}else {
				loop = ModCommon.SearchBlock3to3;
			}
			for(Vec3i vec : loop) {
				BlockPos nextPos = pos.add(vec);
				// iプレイヤーの足元より上のブロックのみを壊す
				if (!(vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) &&
						nextPos.getY() >= entityLiving.getPosition().getY()) {
					BlockState nextState = worldIn.getBlockState(nextPos);
					// iハーベストレベルを比較
					if (nextState.getHarvestLevel() <= this.getTier().getHarvestLevel()){
						TileEntity te = null;
						// iライルエンティティを取り出す
						if (nextState.hasTileEntity()) {
							te = worldIn.getTileEntity(nextPos);
							// iインベントリ持ちなら中身をぶちまける
							if (te instanceof IInventory) {
								InventoryHelper.dropInventoryItems(worldIn, nextPos, (IInventory)te);
							}
							// iタイルエンティティを削除
							worldIn.removeTileEntity(nextPos);
						}
						// iブロックを削除
						worldIn.removeBlock(nextPos, false);
	                    // iブロックをアイテム化
						LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)worldIn))
								.withRandom(worldIn.rand)
								.withParameter(LootParameters.POSITION, pos)
								.withParameter(LootParameters.TOOL, stack)
								.withLuck(((PlayerEntity)entityLiving).getLuck())
								.withNullableParameter(LootParameters.BLOCK_ENTITY, te);

						nextState.getDrops(lootcontext$builder).forEach((nextStack)->{
	                    	worldIn.addEntity(new ItemEntity(worldIn, nextPos.getX(), nextPos.getY(),nextPos.getZ(), nextStack));
	                    });
						if (nextState.getBlockHardness(worldIn, nextPos) != 0.0F) {
					         stack.damageItem(1, entityLiving, (p_220038_0_) -> {
					            p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					         });
						}
						if (stack.isEmpty()) {
							break;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return this.efficiency;
	}

	@Override
	public int getItemEnchantability() {
		return 1;

	}

	public enum EnumHammer{
		BIG,
		SMALL
	}

}
