package mod.fbd.item;

import java.util.ArrayList;
import java.util.Map;

import mod.fbd.core.ModCommon;
import mod.fbd.equipmenteffect.EnchantmentCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemKatanaByako extends ItemKatana {
	public ItemKatanaByako(Item.Properties property){
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
			EntityLivingBase living = (EntityLivingBase)entity;
			// 効果変更白虎防具を追加してセットボーナスにする
//			// 装備中 鈍足、攻撃力低下、採掘低下のポーション効果を受けない
			if (living.getHeldItemMainhand().getItem() == this){
//				List<PotionEffect> effects = new ArrayList<PotionEffect>();
//				effects.addAll(living.getActivePotionEffects());
//				for (PotionEffect effect : effects){
//					if (effect.getPotion() == MobEffects.SLOWNESS ||
//						effect.getPotion() == MobEffects.MINING_FATIGUE ||
//						effect.getPotion() == MobEffects.WEAKNESS){
//						living.removeActivePotionEffect(effect.getPotion());
//					}
//				}
				for (PotionEffect effect : updatePotionList(stack)){
					living.addPotionEffect(new PotionEffect(effect.getPotion(),20,effect.getAmplifier()));
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

				// 目の前のエンティティを奈落へ吹き飛ばす
				if (movingbjectposition.hitInfo != null ){
					if (movingbjectposition.hitInfo instanceof EntityLiving){
						((EntityLiving)movingbjectposition.hitInfo).setPosition(((EntityLiving)movingbjectposition.hitInfo).posX, -10, ((EntityLiving)movingbjectposition.hitInfo).posZ);
					}
				}
			return new ActionResult(EnumActionResult.SUCCESS,stack);
		 }
		 return new ActionResult(EnumActionResult.FAIL, stack);
	 }

	 @Override
	 public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		 if (this.isInGroup(group)) {
			 items.add(getDefaultStack());
		 }
	 }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana_byako);
		 ItemKatana.setMaxLevel(ret,255);
		 ItemKatana.setExp(ret,0);
		 ItemKatana.setRustValue(ret, 0);
		 ItemKatana.setAttackDamage(ret,10.0F);
		 ItemKatana.setAttackSpeed(ret,-0.1D);
		 ItemKatana.setEndurance(ret, 1000);
		 ret.addEnchantment(Enchantments.KNOCKBACK, 1);
		 ret.addEnchantment(EnchantmentCore.enc_livingkiller,1);
//       効果変更
//		 ret.addEnchantment(Enchantments.FEATHER_FALLING, 1);
		 ItemKatana.setUpdatePotionList(ret, new ArrayList<PotionEffect>(){
			 {add(new PotionEffect(MobEffects.INVISIBILITY));}
//				{add(new PotionEffect(MobEffects.SPEED,20,0));}
//				{add(new PotionEffect(MobEffects.JUMP_BOOST,20,0));}
		 });
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_byako.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }

	 public void setLevel(ItemStack stack, int value){
	    	if (value > 255){value = 255;}
	    	NBTTagCompound tag = getItemTagCompound(stack);
	    	tag.setFloat("BladeLevel", value);

			boolean update=false;
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
			int lv = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
			if (lv < MathHelper.ceil(value/5F)){
				map.replace(Enchantments.KNOCKBACK, MathHelper.ceil(value/5F));
				update = true;
			}
			lv = EnchantmentHelper.getEnchantmentLevel(EnchantmentCore.enc_livingkiller, stack);
			if (lv < MathHelper.ceil(value/5F) && lv < 10){
				map.replace(EnchantmentCore.enc_livingkiller, MathHelper.ceil(value/5F));
				update = true;
			}
			if (update){
				EnchantmentHelper.setEnchantments(map, stack);
			}
	 }
	 public Enchantment[] ignoreEnchantments(){
		 return	new Enchantment[]{Enchantments.KNOCKBACK,EnchantmentCore.enc_livingkiller};
	 }
}
