package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ItemKatanaByako extends AbstractItemKatana {
	/*
	 * 金属性　
	 * 木に対して相克し、火にに相克され、土に相生され、水を相生する
	 * 木属性に強い：水生生物に強い
	 * 火性に弱い：ネザー、溶岩の中、炎上中はパワーアップの効力を失う
	 * 土属製から力を得る：周囲の土属性ブロックの分攻撃力上昇
	 * 水属性に力を与える：亀を回復させる、亀の卵をすぐに孵化させる
	*/

	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_byako.png");
	public static final String NAME = "bladebyako";

	public ItemKatanaByako(Item.Properties property){
		super(property.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);

		if (state.getBlock() == Blocks.TURTLE_EGG) {
			world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
			world.removeBlock(pos, false);
			if (!world.isRemote) {
				for(int j = 0; j < state.get(TurtleEggBlock.EGGS); ++j) {
					world.playEvent(2001, pos, Block.getStateId(state));
					TurtleEntity turtleentity = EntityType.TURTLE.create(world);
					turtleentity.setGrowingAge(-24000);
					turtleentity.setHome(pos);
					turtleentity.setLocationAndAngles((double)pos.getX() + 0.3D + (double)j * 0.2D, (double)pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
					world.addEntity(turtleentity);
				}
			}
		}

		return ActionResultType.PASS;
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

		// iネザー以外のディメンジョンで溶岩の中ではなく、炎上中でもない場合
		if ((entity.dimension != DimensionType.THE_NETHER && !entity.isInLava() && !entity.isBurning())) {
			float power = 1.0F;
			// i-5～5の範囲にある土ブロック、石ブロック、金属ブロックの数だけ攻撃力上昇
			for (Vec3i vec : ModCommon.SearchBlock11to11) {
				BlockState state = world.getBlockState(entity.getPosition().add(vec));
				if (state.getMaterial() == Material.EARTH ||
						state.getMaterial() == Material.ROCK ||
						state.getMaterial() == Material.IRON) {
					power += 0.01;
				}
			}
			power = MathHelper.clamp(power, 0.1F, 3.0F);
			damage *= power;
		}else {
			damage *= 0.5F;
		}

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
		// iネザー以外のディメンジョンで溶岩の中ではなく、炎上中でもない場合
		if ((entity.dimension != DimensionType.THE_NETHER && !entity.isInLava() && !entity.isBurning())) {
			float power = 1.0F;
			// i-5～5の範囲にある土ブロック、石ブロック、金属ブロックの数だけ攻撃力上昇
			for (Vec3i vec : ModCommon.SearchBlock11to11) {
				BlockState state = world.getBlockState(entity.getPosition().add(vec));
				if (state.getMaterial() == Material.EARTH ||
						state.getMaterial() == Material.ROCK ||
						state.getMaterial() == Material.IRON) {
					power += 0.001;
				}
			}
			Speed *=power;
		}

		return Speed;
	}

	@Override
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
		return NAME;
	}


	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_byako);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,10.0F);
		setAttackSpeed(ret,-0.1D);
		setEndurance(ret, 1000);
		return ret;
	}


}