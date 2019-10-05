package mod.fbd.item.katana;

import java.util.Random;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;

public class ItemKatanaKirin extends AbstractItemKatana {
	/*
	 * 土属性　
	 * 水に対して相克し、木に相克され、火に相生され、金を相生する
	 * 水属性に強い：亀に強い
	 * 木性に弱い：周囲の木ブロックの分攻撃力低下
	 * 火属製から力を得る：周囲の火属性ブロックの分攻撃力上昇
	 * 金属性に力を与える：動物を飼い府kさせる
	*/
	public static final String NAME = "bladekirin";
	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_kirin.png");

	public ItemKatanaKirin(Item.Properties property){
		super(property.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {

		ItemStack main = playerIn.getHeldItemMainhand();
		if (!worldIn.isRemote){
			int i = (300 + (new Random()).nextInt(600)) * 20;
			if (hand == Hand.MAIN_HAND){
				// iメインハンドに持って右クリックで天候を晴に
				if (main.getItem() == this){
					//CommandWeather
					WorldInfo worldinfo = worldIn.getWorldInfo();
					worldinfo.setClearWeatherTime(i);
					worldinfo.setRainTime(0);
					worldinfo.setThunderTime(0);
					worldinfo.setRaining(false);
					worldinfo.setThundering(false);
				}
			}else if (hand == Hand.OFF_HAND){
				ItemStack off = playerIn.getHeldItemOffhand();
				if (off.getItem() == this){
					// iオフハンドに持って右クリックで天候を嵐に
					main = playerIn.getHeldItemOffhand();
					if (main.getItem() == this){
						WorldInfo worldinfo = worldIn.getWorldInfo();
						worldinfo.setClearWeatherTime(0);
						worldinfo.setRainTime(i);
						worldinfo.setThunderTime(i);
						worldinfo.setRaining(true);
						worldinfo.setThundering(true);
					}
				}
			}else{
				return new ActionResult(ActionResultType.FAIL, main);
			}
		}
		return new ActionResult(ActionResultType.SUCCESS, main);
	}

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
    	super.hitEntity(stack, target, attacker);
    	if (target instanceof MobEntity && target.isAlive()){
    		// 生きているなら落雷を落とす
    		if (!target.world.isRemote){
    			((ServerWorld)target.world).addLightningBolt(new LightningBoltEntity(target.world, target.posX, target.posY, target.posZ, true));
    		}
    	}
		return true;
    }

	@Override
	protected float calcurateAttackDamage(ItemStack stack, World world, Entity entity) {
		int level = this.getLevel(stack);
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// i錆補正

		// i攻撃力設定
		float damage = (this.getAttackDamage(stack) 														// i固有攻撃力
				+ MathHelper.floor(this.getAttackDamage(stack)*(1-Math.exp((-1*level/100)/(1-Math.exp(-1)))))   // iレベル補正
				+ Mod_FantomBlade.HAGANE.getAttackDamage());                                                                           // 素材補正

		float power = 1.0F;
		// i溶岩の中、炎上中で攻撃力増加
		if (entity.isInLava() || entity.isBurning()) {
			power += 0.5F;
		}
		// i-5～5の範囲にある木ブロック、葉ブロックの数だけ攻撃力低下
		// i-5～5の範囲にある土ブロック、石ブロック(ネザー産のものは除く)の数だけ攻撃力低下
		for (Vec3i vec : ModCommon.SearchBlock11to11) {
			BlockState state = world.getBlockState(entity.getPosition().add(vec));
			if (state.getMaterial() == Material.FIRE || state.getMaterial() == Material.LAVA) {
				power += 0.01;
			}else if((state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.WOOD)) {
				power -= 0.01;
			}
		}
		power = MathHelper.clamp(power, 0.1F, 3.0F);
		damage *= power;

		// i 錆補正
		if (rust_h!= 0){
			damage = (float) (damage * MathHelper.clamp(rust_h,0.4,1.0)); // i錆補正 最低0.4倍
		}
		return damage;
	}

	@Override
	protected double calcureteAttackSpeed(ItemStack stack, World world, Entity entity) {
		int rust = this.getRustValue(stack);
		double rust_h = (Math.exp(-1.0D*(rust/1024.0D))*(1.0D-Math.exp(-0.2D))) * (rust==0?0:1);			// i錆補正
		// i攻撃速度設定
		double Speed = this.getAttackSpeed(stack) + 1 - rust_h;
		// i-5～5のハイン位にある金属ブロック、oreブロック、黒曜石の数だけスピード上昇
		float power = 1.0F;
		for (Vec3i vec : ModCommon.SearchBlock11to11) {
			BlockState state = world.getBlockState(entity.getPosition().add(vec));
			if (state.getMaterial() == Material.FIRE || state.getMaterial() == Material.LAVA) {
				power += 0.01;
			}else if((state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.WOOD)) {
				power -= 0.01;
			}
		}

		return Speed * power;
	}

	public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	}

	public Enchantment[] ignoreEnchantments(){
		return	new Enchantment[]{
				Enchantments.IMPALING,
				Enchantments.SMITE,
				Enchantments.BANE_OF_ARTHROPODS};
	}

	@Override
	protected String getKatanaName() {
		// TODO 自動生成されたメソッド・スタブ
		return NAME;
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_kirin);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,14.0F);
		setAttackSpeed(ret,-0.1D);
		setEndurance(ret, 1000);
		if (ModCommon.isDevelop){
			setExp(ret, 10000);
		}
		return ret;
	}
}
