package mod.fbd.item;

import java.util.ArrayList;

import mod.fbd.core.ModCommon;
import mod.fbd.potion.PotionCore;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemKatanaSeiryu extends ItemKatana {
	public ItemKatanaSeiryu(Item.Properties property){
		super(property.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}


	@Override
	public int getLevelUpExp(){
		return LEVELUP_EXP;
	}
	@Override
	public int getPotionEffectUpExp(){
		return POTION_UP;
	}
	@Override
	public int getEnchantLevelUpExp(){
		return ENCHANT_UP;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {
		super.inventoryTick(stack, world, entity, indexOfMainSlot, isCurrent);
		if (entity instanceof EntityLivingBase){
		}
	}

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		int level = this.getLevel(stack);
		// 相手を殺したら経験値獲得
		if(!target.isAlive() && target.deathTime == 0 && attacker instanceof EntityPlayer){
			// 相手の最大HPが経験値
			this.addExp(stack, (int)target.getMaxHealth());
			if (level != this.getLevel(stack)){
				((EntityPlayer)attacker).sendStatusMessage(new TextComponentString("Katana Level Up "+level+"->"+this.getLevel(stack)),false);
			}
		}else if (target.isAlive()){
			// 生きてるならポーション効果をお見舞い
			for (PotionEffect effect : this.updatePotionList(stack)){
				target.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
			}
		}
		stack.damageItem(1, attacker);

		return true;
    }

	 @Override
	 public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		 if (this.isInGroup(group)) {
			 items.add(getDefaultStack());
		 }
	 }

	 @Override
	 public EnumActionResult onItemUse(ItemUseContext context) {
	        ItemStack itemstack = context.getItem();
            if (applyBonemeal(itemstack, context.getWorld(), context.getPos(), context.getPlayer()))
            {
                if (!context.getWorld().isRemote)
                {
                	context.getWorld().playEvent(2005, context.getPos(), 0);
                }

            }
            return EnumActionResult.SUCCESS;

	 }

	    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target)
	    {
	        if (worldIn instanceof net.minecraft.world.WorldServer)
	            return applyBonemeal(stack, worldIn, target, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer)worldIn));
	        return false;
	    }

	    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player)
	    {
	        IBlockState iblockstate = worldIn.getBlockState(target);

	        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack);
	        if (hook != 0) return hook > 0;

	        if (iblockstate.getBlock() instanceof IGrowable)
	        {
	            IGrowable igrowable = (IGrowable)iblockstate.getBlock();

	            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
	            {
	                if (!worldIn.isRemote)
	                {
	                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate))
	                    {
	                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
	                    }

	                }

	                return true;
	            }
	        }

	        return false;
	    }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana_seiryu);
	    	ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,10.0F);
			ItemKatana.setAttackSpeed(ret,-0.2D);
			ItemKatana.setEndurance(ret, 1000);
			ItemKatana.setUpdatePotionList(ret, new ArrayList<PotionEffect>(){
				{add(new PotionEffect(PotionCore.seiryupotion,600,1));}
			});
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_seiryu.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }
}
