package mod.fbd.item.katana;

import mod.fbd.core.ModCommon;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class ItemKatanaSuzaku extends AbstractItemKatana{
	/*
	 * 火属性　
	 * 金に対して相克し、水に相克され、木に相生され、土を相生する
	 * 金属性に強い：動物系に追加ダメージ
	 * 水属性に弱い：水中及び雨天で攻撃力減少
	 * 木属性から力を得る：周囲の木、草ブロック分攻撃力上昇
	 * 土属性に力を与える：アンデットを回復
	*/

	public static String NAME = "bladesuzak";
	public static final ResourceLocation DEFAULT_KATANATEXTURE = new ResourceLocation(ModCommon.MOD_ID,"textures/entity/katana_suzaku.png");

	public ItemKatanaSuzaku(Item.Properties property){
		super(property.defaultMaxDamage(200));
		LEVELUP_EXP = 1000;
		POTION_UP = 200;
		ENCHANT_UP = 200;
	}


	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (context.getHand() == Hand.MAIN_HAND) {
			BlockState state = context.getWorld().getBlockState(context.getPos());
			PlayerEntity player = context.getPlayer();
			ItemStack katana = context.getItem();
			ItemStack stack = new ItemStack(Items.FLINT_AND_STEEL);
			player.setHeldItem(Hand.MAIN_HAND, stack);
			BlockRayTraceResult trace = new BlockRayTraceResult(context.getHitVec(),context.getFace(), context.getPos(), context.func_221533_k());
			ItemUseContext nextContext = new ItemUseContext(player, context.getHand(), trace);
			stack.onItemUse(nextContext);
			player.setHeldItem(Hand.MAIN_HAND, katana);
			if(!context.getWorld().isRemote) {
				stack.damageItem(1, player, (living) -> {
					living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		         });
			}
			return  ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;

	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.getItem() == this){
			RayTraceResult movingbjectposition = rayTrace(worldIn, playerIn,RayTraceContext.FluidMode.SOURCE_ONLY);
			if (movingbjectposition.getType() == RayTraceResult.Type.ENTITY) {
				// iモブを炎上させる
				if (movingbjectposition.hitInfo instanceof LivingEntity) {
					int level = getLevel(stack);
					LivingEntity target = (LivingEntity)movingbjectposition.hitInfo;
					if (!target.isImmuneToFire()) {
						worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
						if (!worldIn.isRemote) {
							target.setFire(level * 5);
							stack.damageItem(1, playerIn, (living) -> {
								living.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					         });
						}
						return new ActionResult(ActionResultType.SUCCESS,stack);
					}
				}
			}
		}
		return new ActionResult(ActionResultType.PASS, stack);
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
		// i水中で攻撃力低下
		if (entity.isInWater() || world.getWorldInfo().isRaining()) {
			power -= 0.5F;
		}
		// i雷雨の中ではパワーアップしない
		if (world.getWorldInfo().isThundering()) {
			// i-5～5の範囲にある木ブロック葉ブロックの数だけ攻撃力上昇
			// i-5～5の範囲にある水ブロック、の数だけ攻撃力低下
			for (Vec3i vec : ModCommon.SearchBlock11to11) {
				BlockState state = world.getBlockState(entity.getPosition().add(vec));
				if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.WOOD) {
					power += 0.1;
				}
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

		float power = 1.0F;
		// i水中で攻撃力低下
		if (entity.isInWater() || world.getWorldInfo().isRaining()) {
			power -= 0.5F;
		}
		// i雷雨の中ではパワーアップしない
		if (world.getWorldInfo().isThundering()) {
			// i-5～5の範囲にある木ブロック葉ブロックの数だけ攻撃力上昇
			// i-5～5の範囲にある水ブロック、の数だけ攻撃力低下
			for (Vec3i vec : ModCommon.SearchBlock11to11) {
				BlockState state = world.getBlockState(entity.getPosition().add(vec));
				if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.WOOD) {
					power += 0.1;
				}
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
				Enchantments.BANE_OF_ARTHROPODS,
				Enchantments.FLAME};
	}

	@Override
	protected String getKatanaName() {
		// TODO 自動生成されたメソッド・スタブ
		return NAME;
	}

	@Override
	protected ItemStack getDefault() {
		ItemStack ret = new ItemStack(ItemCore.item_katana_suzaku);
		setMaxLevel(ret,255);
		setExp(ret,0);
		setRustValue(ret, 0);
		setAttackDamage(ret,10.0F);
		setAttackSpeed(ret,-0.2D);
		setEndurance(ret, 1000);
		ret.addEnchantment(Enchantments.FLAME, 1);
		return ret;
	}
}
