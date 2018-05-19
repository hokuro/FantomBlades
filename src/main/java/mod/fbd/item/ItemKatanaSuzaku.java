package mod.fbd.item;

import java.util.ArrayList;
import java.util.Map;

import mod.fbd.core.ModCommon;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemKatanaSuzaku extends ItemKatana {
	public ItemKatanaSuzaku(){
		super();
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
	public void onUpdate(ItemStack stack, World world, Entity entity, int indexOfMainSlot, boolean isCurrent) {
		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// 錆補正

		// 攻撃力設定
		attackDamage = (this.getAttackDamage(stack) 																// 固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // レベル補正
				+ ToolMaterial.IRON.getAttackDamage());                                                          // 素材補正
		if (entity.dimension == -1){
			// ネザーでは攻撃力3倍
			attackDamage *= 3;
		}
		if (world.getBlockState(entity.getPosition()).getMaterial() == Material.LAVA){
			// 溶岩の中では攻撃力3倍
			attackDamage *= 3;
		}else if (world.getBlockState(entity.getPosition()).getMaterial() == Material.WATER){
			// 水中では攻撃力半減
			attackDamage /=2;
		}
		if (rust_h!= 0){
			attackDamage = (float) (attackDamage * MathHelper.clamp(rust_h,0.4,1.0)); // 錆補正 最低0.4倍
		}

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack) + 1 - rust_h;

		// 耐久力設定
		this.setMaxDamage(this.getGetEndurance(stack));
		if (entity instanceof EntityPlayer){
			updateAttackAmplifier(stack,(EntityPlayer)entity);
		}

		if (entity instanceof EntityLivingBase){
			if (((EntityLivingBase) entity).getHeldItemMainhand().getItem() == this){
				// 装備中耐火の効果
				for (PotionEffect effect : updatePotionList(stack)){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(effect.getPotion(),20,effect.getAmplifier()));
				}
			}
		}
	}

	 @Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		 ItemStack stack = playerIn.getHeldItemMainhand();
		 if (stack.getItem() == this){
				float f = 1.0F;
				float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
				float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
				double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * (double) f;
				double d1 = (playerIn.prevPosY + (playerIn.posY-playerIn.prevPosY) * (double)f+1.6200000000000001D) - (double) playerIn.getYOffset();
				double d2 = playerIn.prevPosZ + (playerIn.posZ -playerIn.prevPosZ) * (double)f;
				Vec3d vec3 = new Vec3d(d0,d1,d2);
				float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
				float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
				float f5 = -MathHelper.cos(-f1 * 0.01745329F);
				float f6 = MathHelper.sin(-f1 * 0.01745329F);
				float f7 = f4 * f5;
				float f8 = f6;
				float f9 = f3 * f5;
				double range = 5D;
				Vec3d vec3d1 =  vec3.addVector((double)f7*range, (double)f8*range, (double)f9*range);
				RayTraceResult movingbjectposition = worldIn.rayTraceBlocks(vec3, vec3d1,true);

				if (movingbjectposition == null){
					return new ActionResult(EnumActionResult.FAIL, stack);
				}

				// 目の前に水がある状態で右クリックをすると、水を蒸発させる
				if (movingbjectposition.typeOfHit == RayTraceResult.Type.BLOCK){
					BlockPos blockpos = movingbjectposition.getBlockPos();
					if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER){
						worldIn.isAirBlock(blockpos);
					}
				}
			return new ActionResult(EnumActionResult.SUCCESS,stack);
		 }
		 return new ActionResult(EnumActionResult.FAIL, stack);
	 }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		super.hitEntity(stack, target, attacker);
		return true;
    }


	 @Override
	 public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	 {
		    if (this.isInCreativeTab(tab))
		    {
		        items.add(getDefaultStack());
		    }
	 }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana_suzaku);
	    	ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,10.0F);
			ItemKatana.setAttackSpeed(ret,-0.2D);
			ItemKatana.setEndurance(ret, 1000);
			ret.addEnchantment(Enchantments.FLAME, 1);
			ItemKatana.setUpdatePotionList(ret, new ArrayList<PotionEffect>(){
				{add(new PotionEffect(MobEffects.FIRE_RESISTANCE,20,1));}
			});
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_suzaku.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }

	 public void setLevel(ItemStack stack, int value){
	    	if (value > 255){value = 255;}
	    	NBTTagCompound tag = getItemTagCompound(stack);
	    	tag.setFloat("BladeLevel", value);

			boolean update=false;
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			int lv = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
			if (lv < MathHelper.ceil(value/5F) && lv < 5){
				map.replace(Enchantments.FLAME, MathHelper.ceil(value/5F));
				update = true;
			}
			if (update){
				EnchantmentHelper.setEnchantments(map, stack);
			}
	 }
	 public Enchantment[] ignoreEnchantments(){
		 return	new Enchantment[]{Enchantments.FLAME};
	 }
}
