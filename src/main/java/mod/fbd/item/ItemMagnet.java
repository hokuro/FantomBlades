package mod.fbd.item;

import mod.fbd.core.ModCommon;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMagnet extends Item {

	protected BlockPos beforePos;
	private static final IItemPropertyGetter DAMAGE_MAGNET = (stack, world, entity) -> {
		if (stack.getItem() instanceof ItemMagnet) {
			int damage = ((ItemMagnet)stack.getItem()).getSand(stack);
			if (damage == 0){
				return 0;
			}else if (damage <= 64){
				return 1;
			}else if (damage <= 128){
				return 2;
			}else if (128 < damage){
				return 3;
			}
		}
		return 0;
	};


	public ItemMagnet(Item.Properties property){
		super(property);
		beforePos = new BlockPos(0,0,0);
		this.addPropertyOverride(new ResourceLocation(ModCommon.MOD_ID, "ironsand"), DAMAGE_MAGNET);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext contex) {
		ItemStack stack = contex.getItem();
		BlockPos pos = contex.getPos();
		if (stack.getItem() == ItemCore.item_magnet && hasSand(stack)){
			if (contex.getWorld().isRemote){
				contex.getWorld().playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F + contex.getWorld().rand.nextFloat(), contex.getWorld().rand.nextFloat() * 0.7F + 0.3F, false);
			}else{
				int stackDamage = this.getSand(stack);
				while(stackDamage > 0) {
					if (stackDamage >= 64){
						InventoryHelper.spawnItemStack(contex.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemCore.item_satetu,64));
					}else{
						InventoryHelper.spawnItemStack(contex.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemCore.item_satetu,stackDamage));
					}
					stackDamage -=64;
				}
				this.setSand(stack, 0);
			}
		}
        return ActionResultType.PASS;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int ix, boolean flag) {
		if (world.isRemote) {
			return;
		}
		if (entity instanceof PlayerEntity && this.getSand(itemstack) < 256){
			BlockPos now = entity.getPosition();
			if (!beforePos.equals(now)){
				if (world.getBlockState(now.offset(Direction.DOWN)).getMaterial() == Material.SAND){
					this.growSand(itemstack, world.rand.nextInt(3)+1);
					this.beforePos = now;
				}
			}
		}
	}

	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
    {
		if (stack.getItem() == ItemCore.item_magnet && state.getMaterial() == Material.SAND){
			this.growSand(stack,worldIn.rand.nextInt(29)+4);
		}
        return false;
    }

	@Override
    public boolean isEnchantable(ItemStack stack)
    {
		return false;
    }

	public boolean hasSand(ItemStack stack){
        CompoundNBT CompoundNBT = stack.getTag();
        if (CompoundNBT != null) {
        	return (CompoundNBT.getInt("satetu") > 0);
        }
        return false;
	}

    public int getSand(ItemStack stack)
    {
    	int ret = 0;
    	CompoundNBT CompoundNBT = stack.getTag();

        if (CompoundNBT != null)
        {
        	ret = CompoundNBT.getInt("satetu");
        }
        return ret;
    }

	public void setSand(ItemStack stack, int cnt)
	{
        CompoundNBT CompoundNBT = stack.getTag();
        if (CompoundNBT == null)
        {
            CompoundNBT = new CompoundNBT();
            stack.setTag(CompoundNBT);
        }
        if ( cnt > 256){
        	cnt = 256;
        }
        CompoundNBT.putInt("satetu", cnt);
	}

	public void growSand(ItemStack stack, int amount){
		setSand(stack, getSand(stack)+amount);
	}
}
