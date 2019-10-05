package mod.fbd.item;

import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntityElmBase;
import mod.fbd.entity.mob.EntitySmithBase;
import mod.fbd.resource.TextureUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemSummonSeal extends Item {
	private EnumSummonType summonEntity;

	ItemSummonSeal(EnumSummonType summon, Item.Properties property){
		super(property);
		summonEntity = summon;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			ItemStack itemstack = context.getItem();
			BlockPos blockpos = context.getPos();
			Direction direction = context.getFace();
			BlockState blockstate = world.getBlockState(blockpos);

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.offset(direction);
			}
			EntityElmBase summon = summonEntity.summonEntity(world, blockpos1);
			if (summon != null){
				summon.setLocationAndAngles(blockpos.getX()+0.5F, blockpos.getY()+1, blockpos.getZ()+0.5F,
						(MathHelper.floor((double)((context.getPlayer().rotationYaw*4F)/360F)+2.5D)&3)*90,0.0F);
				summon.rotationYawHead = summon.rotationYaw;
				summon.renderYawOffset = summon.rotationYaw;
				summon.onInitialSpawn(world, world.getDifficultyForLocation(blockpos1), SpawnReason.SPAWN_EGG, (ILivingEntityData)null, (CompoundNBT)null);
				if (summon instanceof EntitySmithBase) {
					((EntitySmithBase)summon).setCustomResourceLocation(TextureUtil.TextureManager().getRandomTexture(((EntitySmithBase)summon).getSmithName()));
				}
				world.addEntity(summon);
				summon.Dw_SUMMONPOS(blockpos.add(0,1,0));
				if (!context.getPlayer().abilities.isCreativeMode) {
					itemstack.shrink(1);
				}
			}else{
				context.getPlayer().sendStatusMessage(new StringTextComponent("can't summon"),false);
			}
			return ActionResultType.SUCCESS;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		} else {
			RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
			if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
				return new ActionResult<>(ActionResultType.PASS, itemstack);
			} else {
				BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
				BlockPos blockpos = blockraytraceresult.getPos();
				if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
					return new ActionResult<>(ActionResultType.PASS, itemstack);
				} else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {

					EntityElmBase summon = summonEntity.summonEntity(worldIn, blockpos);
					if (summon != null){
						summon.setLocationAndAngles(blockpos.getX()+0.5F, blockpos.getY()+1, blockpos.getZ()+0.5F,
								(MathHelper.floor((double)((playerIn.rotationYaw*4F)/360F)+2.5D)&3)*90,0.0F);
						summon.rotationYawHead = summon.rotationYaw;
						summon.renderYawOffset = summon.rotationYaw;
						summon.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(blockpos), SpawnReason.SPAWN_EGG, (ILivingEntityData)null, (CompoundNBT)null);
						if (summon instanceof EntitySmithBase) {
							((EntitySmithBase)summon).setCustomResourceLocation(TextureUtil.TextureManager().getRandomTexture(((EntitySmithBase)summon).getSmithName()));
						}
						worldIn.addEntity(summon);
						summon.Dw_SUMMONPOS(blockpos.add(0,1,0));
						if (!playerIn.abilities.isCreativeMode) {
							itemstack.shrink(1);
						}
						return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
					}else{
						playerIn.sendStatusMessage(new StringTextComponent("can't summon"),false);
						return new ActionResult<>(ActionResultType.PASS, itemstack);
					}
				} else {
					return new ActionResult<>(ActionResultType.FAIL, itemstack);
				}
			}
		}
	}

	public static enum EnumSummonType{
		BLADESMITH(0,"bladesmith"),
		ARMORSMITH(1,"armorsmith"),
		IMOUTO(1,"imout");

		private final int index;
		private final String displayName;
		private static final EnumSummonType[] values ={
				BLADESMITH,
				ARMORSMITH,
				IMOUTO
		};

		private EnumSummonType(int idx, String name){
			index = idx;
			displayName = name;
		}

		public int getIndex(){return index;}
		public String getName(){return displayName;}
		public EntityElmBase summonEntity(World world, BlockPos pos){
			EntityElmBase summon = null;
			if (this == BLADESMITH){
				summon = new EntityBladeSmith(world);
			}else if (this == ARMORSMITH){
				summon = new EntityArmorSmith(world);
			}else if (this == IMOUTO){

			}

			return summon;
		}


		public static EnumSummonType getEntityFromIndex(int idx){
			if (idx <0 || idx >= values.length){
				return null;
			}
			return values[idx];
		}
	}
}
