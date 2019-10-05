package mod.fbd.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

public class BlockDummyAnvil extends AnvilBlock {

	protected BlockDummyAnvil(Block.Properties property){
		super(property);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return true;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		return new ArrayList<ItemStack>(){{add(new ItemStack(Blocks.ANVIL,1));}};
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	}
}
