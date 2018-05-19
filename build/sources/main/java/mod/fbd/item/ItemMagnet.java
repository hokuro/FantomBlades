package mod.fbd.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMagnet extends Item {

	protected BlockPos beforePos;

	public ItemMagnet(){
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		beforePos = new BlockPos(0,0,0);
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() == ItemCore.item_magnet && stack.getItemDamage() > 0){
			if (worldIn.isRemote){
				worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F + worldIn.rand.nextFloat(), worldIn.rand.nextFloat() * 0.7F + 0.3F, false);
			}else{
				int stackDamage = this.getSand(stack);
				for (int i = 0; i < 4 && stackDamage > 0; i++){
					if (stackDamage >= 64){
						InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemCore.item_satetu,64));
					}else{
						InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemCore.item_satetu,stackDamage));
					}
					stackDamage -=64;
				}
				this.setSand(stack, 0);
			}
		}
        return EnumActionResult.PASS;
    }

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int ix, boolean flag) {
		if (world.isRemote) {
			return;
		}
		if (entity instanceof EntityPlayer && this.getSand(itemstack) < 256){
			BlockPos now = entity.getPosition();
			if (!beforePos.equals(now)){
				if (world.getBlockState(now.offset(EnumFacing.DOWN)).getMaterial() == Material.SAND){
					this.growSand(itemstack, world.rand.nextInt(3)+1);
					this.beforePos = now;
				}
			}
		}
	}

	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		if (stack.getItem() == ItemCore.item_magnet && state.getMaterial() == Material.SAND){
			this.growSand(stack,worldIn.rand.nextInt(29)+4);
		}
        return false;
    }


	@Override
    public int getMetadata(ItemStack stack)
    {
		int damage = this.getSand(stack);
		if (damage == 0){
			return 0;
		}else if (damage <= 64){
			return 1;
		}else if (damage <= 128){
			return 2;
		}else if (128 < damage){
			return 3;
		}
		return 0;
    }

	@Override
    public boolean isEnchantable(ItemStack stack)
    {
		return false;
    }

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
        }
    }

	public boolean hasSand(ItemStack stack){
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey("satetu", 1);
	}

    public int getSand(ItemStack stack)
    {
    	int ret = 0;
    	NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
        	ret = nbttagcompound.getInteger("satetu");
        }
        return ret;
    }

	public void setSand(ItemStack stack, int cnt)
	{
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }
        if ( cnt > 256){
        	cnt = 256;
        }
        nbttagcompound.setInteger("satetu", cnt);
	}

	public void growSand(ItemStack stack, int amount){
		setSand(stack, getSand(stack)+amount);
	}
}
