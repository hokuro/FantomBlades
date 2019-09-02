package mod.fbd.item;

import java.util.ArrayList;
import java.util.Map;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.equipmenteffect.EnchantmentCore;
import mod.fbd.util.ModUtil;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
import net.minecraft.world.World;

public class ItemKatanaGenbu extends ItemKatana {
	public ItemKatanaGenbu(Item.Properties proerty){
		super(proerty.defaultMaxDamage(200));
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
		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			    // 錆補正

		// 攻撃力設定
		attackDamage = (this.getAttackDamage(stack) 														    // 固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // レベル補正
				+ Mod_FantomBlade.HAGANE.getAttackDamage());                                                                           // 素材補正
		if (world.getBlockState(entity.getPosition()).getMaterial() == Material.LAVA){
			// 溶岩の中では攻撃力半減
			attackDamage /=2;
		}else if (world.getBlockState(entity.getPosition()).getMaterial() == Material.WATER){
			// 水中では攻撃力3倍
			attackDamage *= 3;;
		}
		if (rust_h!= 0){
			attackDamage = (float) (attackDamage * MathHelper.clamp(rust_h,0.4,1.0)); // 錆補正 最低0.4倍
		}

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack) - rust_h;

		// 耐久力設定
		//this.setMaxDamage(this.getGetEndurance(stack));
		ModUtil.setPrivateValue(Item.class, this, this.getGetEndurance(stack), "maxDamage");
		if (entity instanceof EntityPlayer){
			updateAttackAmplifier(stack,(EntityPlayer)entity);
		}

		if (entity instanceof EntityLivingBase){
			if (((EntityLivingBase) entity).getHeldItemMainhand().getItem() == this){
				// 装備中水中呼吸の効果
				for (PotionEffect effect : updatePotionList(stack)){
//					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(effect.getPotion(),20,effect.getAmplifier()));
				}
			}
		}
	}

	 @Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		 ItemStack stack = playerIn.getHeldItemMainhand();
		 if (stack.getItem() == this){
				RayTraceResult movingbjectposition = this.rayTrace(worldIn, playerIn,true);

				if (movingbjectposition == null){
					return new ActionResult(EnumActionResult.FAIL, stack);
				}

				// 目の前に溶岩がある状態で右クリックをすると、黒曜石を生成する
				if (movingbjectposition.type == RayTraceResult.Type.BLOCK){
					BlockPos blockpos = movingbjectposition.getBlockPos();
					if (worldIn.getBlockState(blockpos).getMaterial() == Material.LAVA){
						worldIn.setBlockState(blockpos, Blocks.MAGMA_BLOCK.getDefaultState());
					}else if (worldIn.getBlockState(blockpos).getBlock() == Blocks.MAGMA_BLOCK){
						worldIn.setBlockState(blockpos, Blocks.OBSIDIAN.getDefaultState());
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
	 public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		 if (this.isInGroup(group)) {
			 items.add(getDefaultStack());
		 }
	 }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana_genbu);
			ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,12.0F);
			ItemKatana.setAttackSpeed(ret,-0.5D);
			ItemKatana.setEndurance(ret, 1000);
//			ret.addEnchantment(Enchantments.DEPTH_STRIDER, 1);
//			ret.addEnchantment(Enchantments.RESPIRATION, 1);
			// エンダーマンに追加ダメージ
			ret.addEnchantment(EnchantmentCore.enc_enderkillre, 1);
			ret.addEnchantment(EnchantmentCore.enc_waterkiller, 1);

			ItemKatana.setUpdatePotionList(ret, new ArrayList<PotionEffect>(){
				{add(new PotionEffect(MobEffects.WATER_BREATHING,20,1));}
			});
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_genbu.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }

	 public void setLevel(ItemStack stack, int value){
	    	if (value > 255){value = 255;}
	    	NBTTagCompound tag = getItemTagCompound(stack);
	    	tag.setFloat("BladeLevel", value);

			boolean update=false;
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			int lv = EnchantmentHelper.getEnchantmentLevel(EnchantmentCore.enc_enderkillre, stack);
			if (lv < MathHelper.ceil(value/5F) && lv < 11){
				map.replace(EnchantmentCore.enc_enderkillre, MathHelper.ceil(value/5F));
				update = true;
			}
		    lv = EnchantmentHelper.getEnchantmentLevel(EnchantmentCore.enc_waterkiller, stack);
			if (lv < MathHelper.ceil(value/5F) && lv < 11){
				map.replace(EnchantmentCore.enc_waterkiller, MathHelper.ceil(value/5F));
				update = true;
			}
			if (update){
				EnchantmentHelper.setEnchantments(map, stack);
			}
	 }
	 public Enchantment[] ignoreEnchantments(){
		 return	new Enchantment[]{EnchantmentCore.enc_enderkillre, EnchantmentCore.enc_waterkiller};
	 }
}
