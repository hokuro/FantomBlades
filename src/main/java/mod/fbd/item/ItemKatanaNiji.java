package mod.fbd.item;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.world.World;

public class ItemKatanaNiji extends ItemKatana {

	public ItemKatanaNiji(Item.Properties property){
		super(Mod_FantomBlade.NIJI, property.maxStackSize(200));
		LEVELUP_EXP = 2000;
		POTION_UP = 500;
		ENCHANT_UP = 500;
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
				+ Mod_FantomBlade.NIJI.getAttackDamage()                                                                            // 素材補正
				+ getKillCount(stack) / 100;                                                                    //　殺害補正

		// 攻撃速度設定
		attackSpeed = this.getAttackSpeed(stack);

		// 耐久力設定
		//this.setMaxDamage(this.getGetEndurance(stack));
		ModUtil.setPrivateValue(Item.class, this, this.getGetEndurance(stack), "maxDamage");
		if (entity instanceof EntityPlayer){
			updateAttackAmplifier(stack,(EntityPlayer)entity);
		}
	}

	 @Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
	     ItemStack katana = playerIn.getHeldItemMainhand();
		 if (!worldIn.isRemote){
			 if (katana.getItem() == this){
				 // 右クリックでポーション効果を付与
				 for(PotionEffect effect : ItemKatana.getPotionEffects(katana)){
					playerIn.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
				 }
			 }
		 }
		 return new ActionResult(EnumActionResult.SUCCESS, katana);
	 }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
    	super.hitEntity(stack, target, attacker);
    	// 切った相手にポーション効果を付与
    	if (target.isAlive()){
    		for (PotionEffect effect : ItemKatana.getPotionEffects(stack)){
    			target.addPotionEffect(new PotionEffect(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
    		}
    	}else{
    		// 殺したらキル数を増やす
    		ItemKatanaNiji.addKillCount(stack,1);
    	}
		return true;
    }

    public void setDamage(ItemStack stack, int damage)
    {
    	// 虹は決して壊れない
    	super.setDamage(stack, 0);
    }



	public static void setKillCount(ItemStack stack, int value) {
    	NBTTagCompound tag = getItemTagCompound(stack);
    	if (value > 1000){value = 1000;}
    	tag.setInt("killcount", value);
	}

	public static int getKillCount(ItemStack stack){
		NBTTagCompound tag = getItemTagCompound(stack);
    	return tag.getInt("killcount");
	}

	public static void addKillCount(ItemStack stack, int add){
		if (getKillCount(stack) < 1000){
			setKillCount(stack,getKillCount(stack)+add);
		}
	}

	 @Override
	 public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		 if (this.isInGroup(group)) {
			 items.add(getDefaultStack());
		 }
	 }

	 public static ItemStack getDefaultStack(){
	    	ItemStack ret = new ItemStack(ItemCore.item_katana_niji);
			ItemKatana.setMaxLevel(ret,255);
			ItemKatana.setExp(ret,0);
			ItemKatana.setRustValue(ret, 0);
			ItemKatana.setAttackDamage(ret,1.0F);
			ItemKatana.setAttackSpeed(ret,-0.0000000001D);
			ItemKatana.setEndurance(ret, 1000);
			ItemKatanaNiji.setKillCount(ret,0);
			if (ModCommon.isDevelop){
				ItemKatana.setExp(ret, 10000);
				ItemKatanaNiji.setKillCount(ret,999);
			}
		 return ret;
	 }

	 public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_niji.png");
	 public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	 }
}
