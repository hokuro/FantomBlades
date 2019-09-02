package mod.fbd.item;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemKatanaMugen extends ItemKatana {
	public ItemKatanaMugen(Item.Properties property){
		super(Mod_FantomBlade.NIJI, property.defaultMaxDamage(200));
		LEVELUP_EXP = 2000;
		POTION_UP = 1000;
		ENCHANT_UP = 1000;
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
		// 攻撃力設定
		attackDamage = this.getAttackDamage(stack) 																// 固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // レベル補正
				+ getAttackDamage()                                                                             // 素材補正
				+ getKillCount(stack) / 100;                                                                    //　殺害補正

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack);

		// 耐久力設定
		//this.setMaxDamage(this.getGetEndurance(stack));
		ModUtil.setPrivateValue(Item.class, this, this.getGetEndurance(stack), "maxDamage");
		if (entity instanceof EntityPlayer){
			updateAttackAmplifier(stack,(EntityPlayer)entity);
		}

		// 所持している対象にバフ効果を発生させる
		if (entity instanceof EntityLivingBase){
			EntityLivingBase living = ((EntityLivingBase)entity);
			if (living.getHeldItemMainhand() == stack){
				for (PotionEffect effect: ItemKatana.getPotionEffects(stack)){
					if (!effect.getPotion().isBadEffect() && !effect.getPotion().isInstant()){
						living.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
					}
				}
			}
		}
	}

	 @Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		 ItemStack katana = playerIn.getHeldItemMainhand();
		 if (!worldIn.isRemote){
			 if (katana.getItem() == this){
				 // 右クリックでバフ効果を付与
				 for(PotionEffect effect : ItemKatana.getPotionEffects(katana)){
					 if (!effect.getPotion().isBadEffect()){
						 playerIn.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
					 }
				 }
			 }
		 }
		 return new ActionResult(EnumActionResult.SUCCESS, katana);
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

			// 殺人カウントアップ
	    	ItemKatanaNiji.addKillCount(stack,1);
		}else if (target.isAlive()){
			// 生きてるならデバフ効果をお見舞い
			for (PotionEffect effect : this.getPotionEffects(stack)){
				if (effect.getPotion().isBadEffect() ||
						(effect.getPotion() == MobEffects.INSTANT_HEALTH  && (target.getCreatureAttribute() == CreatureAttribute.UNDEAD)) ||
						(effect.getPotion() == MobEffects.INSTANT_DAMAGE && (target.getCreatureAttribute() != CreatureAttribute.UNDEAD))){
					target.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
				}
			}
		}
		stack.damageItem(1, attacker);

		return true;
    }

    public void setDamage(ItemStack stack, int damage)
    {
    	// 夢幻は決して壊れない
    	super.setDamage(stack, 0);
    }



	public static void setKillCount(ItemStack stack, int value) {
    	NBTTagCompound tag = getItemTagCompound(stack);
    	if (value > 10000){value = 10000;}
    	tag.setInt("killcount", value);
	}

	public static int getKillCount(ItemStack stack){
		NBTTagCompound tag = getItemTagCompound(stack);
    	return tag.getInt("killcount");
	}

	public static void addKillCount(ItemStack stack, int add){
		if (getKillCount(stack) < 10000){
			setKillCount(stack,getKillCount(stack)+add);
		}
	}

	private boolean isBuff(Potion potion, boolean isunded){
		if (potion == MobEffects.SPEED ||
			potion == MobEffects.HASTE ||
			potion == MobEffects.STRENGTH ||
			potion == MobEffects.JUMP_BOOST ||
			potion == MobEffects.RESISTANCE ||
			potion == MobEffects.FIRE_RESISTANCE ||
			potion == MobEffects.WATER_BREATHING ||
			potion == MobEffects.INVISIBILITY ||
			potion == MobEffects.NIGHT_VISION ||
			potion == MobEffects.HEALTH_BOOST ||
			potion == MobEffects.ABSORPTION ||
			potion == MobEffects.SATURATION ||
			potion == MobEffects.LUCK ||
			potion == MobEffects.REGENERATION ||
			(potion == MobEffects.INSTANT_HEALTH && !isunded) ||
			(potion == MobEffects.INSTANT_DAMAGE && isunded)){
			return true;
		}else{
			return false;
		}
	}

	 @Override
	 public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		 if (this.isInGroup(group)) {
			 items.add(getDefaultStack());
		 }
	 }

	 public static ItemStack getDefaultStack(){
		 ItemStack ret = new ItemStack(ItemCore.item_katana_mugen);
			ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,1.0F);
			ItemKatana.setAttackSpeed(ret,-0.0000000001D);
			ItemKatana.setEndurance(ret, 1000);
			ItemKatanaNiji.setKillCount(ret,0);
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_mugen.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }
}
