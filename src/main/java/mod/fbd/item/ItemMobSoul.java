package mod.fbd.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ItemMobSoul extends Item {

	public ItemMobSoul(Item.Properties property) {
		super(property);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			ItemStack itemstack = context.getItem();

			String key = this.readKey(itemstack);
			CompoundNBT nbt = this.readData(itemstack);
			CompoundNBT additional = this.readAdditional(itemstack);
			if (StringUtils.isNullOrEmpty(key) || nbt == null) {
				return ActionResultType.FAIL;
			}

			BlockPos blockpos = context.getPos();
			Direction direction = context.getFace();
			BlockState blockstate = world.getBlockState(blockpos);

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.offset(direction);
			}

			Entity ent = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(key)).create(world);
			if (ent != null && ent instanceof LivingEntity){
				LivingEntity summon = (LivingEntity)ent;
				summon.read(nbt);
				summon.readAdditional(additional);
				summon.setLocationAndAngles(blockpos.getX()+0.5F, blockpos.getY()+1, blockpos.getZ()+0.5F,
						(MathHelper.floor((double)((context.getPlayer().rotationYaw*4F)/360F)+2.5D)&3)*90,0.0F);
				summon.rotationYawHead = summon.rotationYaw;
				summon.renderYawOffset = summon.rotationYaw;
				world.addEntity(summon);
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
			String key = this.readKey(itemstack);
			CompoundNBT nbt = this.readData(itemstack);
			CompoundNBT additional = this.readAdditional(itemstack);
			if (StringUtils.isNullOrEmpty(key) || nbt == null) {
				return new ActionResult<>(ActionResultType.FAIL, itemstack);
			}

			RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
			if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
				return new ActionResult<>(ActionResultType.PASS, itemstack);
			} else {
				BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
				BlockPos blockpos = blockraytraceresult.getPos();
				if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
					return new ActionResult<>(ActionResultType.PASS, itemstack);
				} else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {

					Entity ent = Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(key)).create(worldIn);
					if (ent != null && ent instanceof LivingEntity){
						LivingEntity summon = (LivingEntity)ent;
						summon.read(nbt);
						summon.readAdditional(additional);
						summon.setLocationAndAngles(blockpos.getX()+0.5F, blockpos.getY()+1, blockpos.getZ()+0.5F,
								(MathHelper.floor((double)((playerIn.rotationYaw*4F)/360F)+2.5D)&3)*90,0.0F);
						summon.rotationYawHead = summon.rotationYaw;
						summon.renderYawOffset = summon.rotationYaw;
						worldIn.addEntity(summon);
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



	@Override
	public String getTranslationKey(ItemStack stack) {
		String entityName = this.readKey(stack);
		if (StringUtils.isNullOrEmpty(entityName)) {
			entityName = "Empty";
		}else {
			entityName = I18n.format(Registry.ENTITY_TYPE.getOrDefault(new ResourceLocation(entityName)).getTranslationKey());
		}
		return I18n.format(this.getTranslationKey()) + " " + entityName;
	}

	public String readKey(ItemStack stack) {
		String ret = "";
		CompoundNBT nbt = stack.getOrCreateTag();
		if (nbt.contains("entitynamekey")){
			ret = nbt.getString("entitynamekey");
		}
		return ret;
	}

	public CompoundNBT readData(ItemStack stack) {
		CompoundNBT ret = null;
		CompoundNBT nbt = stack.getOrCreateTag();
		if (nbt.contains("entityreaddata")){
			ret = (CompoundNBT)nbt.get("entityreaddata");
		}
		return ret;
	}

	public CompoundNBT readAdditional(ItemStack stack) {
		CompoundNBT ret = null;
		CompoundNBT nbt = stack.getOrCreateTag();
		if (nbt.contains("entityadditionaldata")){
			ret = (CompoundNBT)nbt.get("entityadditionaldata");
		}
		return ret;
	}


	public static void addReadData(ItemStack stack, String key, CompoundNBT data, CompoundNBT additional) {
		CompoundNBT nbt = stack.getOrCreateTag();
		nbt.putString("entitynamekey", key);
		nbt.put("entityreaddata", data);
		nbt.put("entityadditionaldata", additional);
		stack.setTag(nbt);
	}
}
