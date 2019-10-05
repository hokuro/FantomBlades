package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class ItemKatanaGenbu extends AbstractItemKatana {
	/*
	 * 水属性　
	 * 火に対して相克し、土に相克され、金に相生され、木を相生する
	 * 火属性に強い：ネザーの生物に高ダメージ
	 * 土属性に弱い：周囲の土系ブロックの分攻撃力低下
	 * 金属性から力を得る：周囲の金属ブロックの分攻撃力上昇
	 * 木属性に力を与える：魚、烏賊、海豚、ドラウンドを回復
	*/

	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_genbu.png");
	public static final String NAME = "bladegenbu";
	public ItemKatanaGenbu(Item.Properties proerty){
		super(proerty.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (context.getHand() == Hand.MAIN_HAND) {
			ItemStack stack = context.getItem();
			PlayerEntity player = context.getPlayer();
			World worldIn = context.getWorld();
			BlockPos pos = context.getPos();
			BlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() == Blocks.MAGMA_BLOCK) {
				// i使用したもの場マグマブロックなら黒曜石に変換
				worldIn.playSound(player, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if(!worldIn.isRemote) {
					worldIn.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
					stack.damageItem(1, player, (living) -> {
						living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			         });
				}
				return ActionResultType.SUCCESS;
			}else {
				// 骨粉と同じ効果
				ItemStack meal = new ItemStack(Items.BONE_MEAL,1);
				player.setHeldItem(context.getHand(), meal);
				BlockRayTraceResult trace = new BlockRayTraceResult(context.getHitVec(),context.getFace(), context.getPos(), context.func_221533_k());
				ItemUseContext mealUse = new ItemUseContext(player, context.getHand(), trace);
				meal.onItemUse(mealUse);
				player.setHeldItem(context.getHand(), stack);

				if(!worldIn.isRemote) {
					stack.damageItem(1, player, (living) -> {
						living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			         });
				}
				return ActionResultType.PASS;
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.getItem() == this){
				RayTraceResult movingbjectposition = Item.rayTrace(worldIn, playerIn,FluidMode.ANY);

				if (movingbjectposition.getType() == RayTraceResult.Type.MISS){
					return new ActionResult(ActionResultType.FAIL, stack);
				}

				// i目の前に溶岩がある状態で右クリックをすると、黒曜石を生成する
				if (movingbjectposition.getType() == RayTraceResult.Type.BLOCK){
					BlockPos blockpos = new BlockPos(movingbjectposition.getHitVec());
					BlockState state = worldIn.getBlockState(blockpos);
					if (state.getMaterial() == Material.LAVA) {
						worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
						if(!worldIn.isRemote) {
							worldIn.setBlockState(blockpos, Blocks.MAGMA_BLOCK.getDefaultState());
							stack.damageItem(1, playerIn, (living) -> {
								living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					         });
						}
					}
				}
			return new ActionResult(ActionResultType.SUCCESS,stack);
		}
		return new ActionResult(ActionResultType.FAIL, stack);
	}

	public ResourceLocation getBladeTexture() {
		return DEFAULT_KATANATEXTURE;
	}

	public Enchantment[] ignoreEnchantments(){
		return	new Enchantment[]{
				Enchantments.IMPALING,
				Enchantments.SMITE,
				Enchantments.BANE_OF_ARTHROPODS,
				Enchantments.FLAME};
	}

	@Override
	protected String getKatanaName() {
		return NAME;
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
		// i水中で攻撃力上昇
		if (entity.isInWater()) {
			power += 0.5F;
		}
		// i-5～5の範囲にある金属ブロック、oreブロック、黒曜石の数だけ攻撃力上昇
		// i-5～5の範囲にある土ブロック、石ブロック(ネザー産のものは除く)の数だけ攻撃力低下
		for (Vec3i vec : ModCommon.SearchBlock11to11) {
			BlockState state = world.getBlockState(entity.getPosition().add(vec));
			if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("ore") || state.getBlock() == Blocks.OBSIDIAN) {
				power += 0.1;
			}else if((state.getMaterial() == Material.ROCK || state.getMaterial() == Material.EARTH) && (state.getBlock().getRegistryName().getPath().contains("nether"))) {
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
			if (state.getMaterial() == Material.IRON || state.getBlock().getRegistryName().getPath().contains("ore") || state.getBlock() == Blocks.OBSIDIAN) {
				power += 0.01;
			}else if((state.getMaterial() == Material.ROCK || state.getMaterial() == Material.EARTH) && (state.getBlock().getRegistryName().getPath().contains("nether"))) {
				power -= 0.01;
			}
		}

		return Speed * power;
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_genbu);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,12.0F);
		setAttackSpeed(ret,-0.5D);
		setEndurance(ret, 1000);
		return ret;
	}
}
