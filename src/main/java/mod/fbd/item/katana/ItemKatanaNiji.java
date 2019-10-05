package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemKatanaNiji extends AbstractItemKatana {

	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_niji.png");
	public static final String NAME = "bladeniji";

	public ItemKatanaNiji(Item.Properties property){
		super(Mod_FantomBlade.NIJI, property.maxStackSize(200));
		LEVELUP_EXP = 2000;
		POTION_UP = 500;
		ENCHANT_UP = 500;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
	ItemStack katana = playerIn.getHeldItemMainhand();
		if (!worldIn.isRemote){
			if (katana.getItem() == this){
				// i右クリックでポーション効果を付与
				for(EffectInstance effect : getPotionEffects(katana)){
					playerIn.addPotionEffect(new EffectInstance(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
				}
			}
		}
		return new ActionResult(ActionResultType.SUCCESS, katana);
	}

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
    	super.hitEntity(stack, target, attacker);
    	// i切った相手にポーション効果を付与
    	if (target.isAlive()){
    		for (EffectInstance effect : getPotionEffects(stack)){
    			target.addPotionEffect(new EffectInstance(effect.getPotion(),effect.getDuration(),effect.getAmplifier()));
    		}
    	}else{
    		// i殺したらキル数を増やす
    		ItemKatanaNiji.addKillCount(stack,1);
    	}
		return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
    	// i虹は決して壊れない
    	super.setDamage(stack, 0);
    }

	@Override
	protected float calcurateAttackDamage(ItemStack stack, World world, Entity entity) {
		int level = this.getLevel(stack);
		// i攻撃力設定
		float damage = this.getAttackDamage(stack) 																// i固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // iレベル補正
				+ Mod_FantomBlade.NIJI.getAttackDamage()                                                        // 素材補正
				+ getKillCount(stack) / 100;
		return damage;
	}

	@Override
	protected double calcureteAttackSpeed(ItemStack stack, World world, Entity entity) {
		// i攻撃速度設定
		double Speed = this.getAttackSpeed(stack);
		return Speed;
	}


	public static void setKillCount(ItemStack stack, int value) {
    	CompoundNBT tag = getItemTagCompound(stack);
    	if (value > 1000){value = 1000;}
    	tag.putInt("killcount", value);
	}

	public static int getKillCount(ItemStack stack){
		CompoundNBT tag = getItemTagCompound(stack);
    	return tag.getInt("killcount");
	}

	public static void addKillCount(ItemStack stack, int add){
		if (getKillCount(stack) < 1000){
			setKillCount(stack,getKillCount(stack)+add);
		}
	}

	@Override
	protected String getKatanaName() {
		// TODO 自動生成されたメソッド・スタブ
		return NAME;
	}

	@Override
	public ResourceLocation getBladeTexture() {
		return DEFAULT_KATANATEXTURE;
	}

	@Override
	public Enchantment[] ignoreEnchantments() {
		return	new Enchantment[]{};
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_niji);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,1.0F);
		setAttackSpeed(ret,-0.0000000001D);
		setEndurance(ret, 1000);
		ItemKatanaNiji.setKillCount(ret,0);
		if (ModCommon.isDevelop){
			setExp(ret, 10000);
			setKillCount(ret,999);
		}
		return ret;
	}
}
