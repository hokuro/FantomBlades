package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class ItemKatanaSeiryu extends AbstractItemKatana{
	/*
	 * 木属性　
	 * 土に対して相克し、金に相克され、水に相生され、火を相生する
	 * 土属性に強い：アンデット特攻
	 * 金属性に弱い：周囲の金属系ブロックの分攻撃力低下
	 * 水属性から力を得る：周囲の水ブロックの分攻撃力上昇、雨、嵐のときに攻撃力上昇
	 * 火属性に力を与える：炎上しないモブの体力を回復
	*/

	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_seiryu.png");
	public static final String NAME = "bladeseiryu";

	public ItemKatanaSeiryu(Item.Properties property){
		super(property.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return new ActionResult<>(ActionResultType.PASS, stack);
		}else if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
			BlockState state = worldIn.getBlockState(new BlockPos(raytraceresult.getHitVec()));
			if (state.getBlock() == Blocks.LAVA && state.get(FlowingFluidBlock.LEVEL) != 0) {
				// i溶岩流を溶岩源に
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!worldIn.isRemote) {
					worldIn.setBlockState(new BlockPos(raytraceresult.getHitVec()), Blocks.LAVA.getDefaultState().with(FlowingFluidBlock.LEVEL, 0));
					stack.damageItem(1, playerIn, (living) -> {
						living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			         });
				}
				return new ActionResult<>(ActionResultType.SUCCESS, stack);
			}else if (state.getMaterial() == Material.FIRE) {
				// i炎の寿命を延ばす
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!worldIn.isRemote) {
					worldIn.setBlockState(new BlockPos(raytraceresult.getHitVec()), Blocks.FIRE.getDefaultState().with(FireBlock.AGE, 15));
					stack.damageItem(1, playerIn, (living) -> {
						living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			         });
				}
				return new ActionResult<>(ActionResultType.SUCCESS, stack);
			}
		}
		return new ActionResult<>(ActionResultType.PASS, stack);
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
		// i水中又は雨天で攻撃力上昇
		if (entity.isInWater() || world.getWorldInfo().isRaining()) {
			power += 0.5F;
		}
		// i雷雨であればさらに上昇
		if (world.getWorldInfo().isThundering()) {
			power += 0.5F;
		}

		// i-5～5の範囲にある金属ブロック、oreブロック、黒曜石の数だけ攻撃力低下
		// i-5～5の範囲にある水ブロックの数だけ攻撃力上昇
		for (Vec3i vec : ModCommon.SearchBlock11to11) {
			BlockState state = world.getBlockState(entity.getPosition().add(vec));
			if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("ore") || state.getBlock() == Blocks.OBSIDIAN) {
				power -= 0.5;
			}else if(state.getMaterial() == Material.WATER) {
				power += 0.2;
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
		// i-5～5の範囲にある金属ブロック、oreブロック、黒曜石の数だけスピード上昇
		float power = 1.0F;
		for (Vec3i vec : ModCommon.SearchBlock11to11) {
			BlockState state = world.getBlockState(entity.getPosition().add(vec));
			if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("ore") || state.getBlock() == Blocks.OBSIDIAN) {
				power -= 0.01;
			}else if(state.getMaterial() == Material.WATER) {
				power += 0.01;
			}
		}
		return Speed * power;
	}

	public ResourceLocation getBladeTexture() {
			return DEFAULT_KATANATEXTURE;
	}

	@Override
	protected String getKatanaName() {
		return NAME;
	}

	@Override
	public Enchantment[] ignoreEnchantments(){
		return	new Enchantment[]{
				Enchantments.IMPALING,
				Enchantments.SMITE,
				Enchantments.BANE_OF_ARTHROPODS};
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_seiryu);
		setMaxLevel(ret,255);
			setExp(ret,0);
			setRustValue(ret, 0);
			setAttackDamage(ret,10.0F);
			setAttackSpeed(ret,-0.2D);
			setEndurance(ret, 1000);
		return ret;
	}


}
