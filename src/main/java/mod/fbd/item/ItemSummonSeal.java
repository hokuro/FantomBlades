package mod.fbd.item;

import mod.fbd.core.log.ModLog;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntityElmBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSummonSeal extends Item {

	private EnumSummonType summonEntity;

	ItemSummonSeal(EnumSummonType summon){
		summonEntity = summon;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		ItemStack itemstack = player.getHeldItem(hand);
			float f = 1.0F;
			float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
			float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
			double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
			double d1 = (player.prevPosY + (player.posY-player.prevPosY) * (double)f+1.6200000000000001D) - (double) player.getYOffset();
			double d2 = player.prevPosZ + (player.posZ -player.prevPosZ) * (double)f;
			Vec3d vec3 = new Vec3d(d0,d1,d2);
			float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
			float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
			float f5 = -MathHelper.cos(-f1 * 0.01745329F);
			float f6 = MathHelper.sin(-f1 * 0.01745329F);
			float f7 = f4 * f5;
			float f8 = f6;
			float f9 = f3 * f5;
			double range = 5D;
			Vec3d vec3d1 =  vec3.addVector((double)f7*range, (double)f8*range, (double)f9*range);
			RayTraceResult movingbjectposition = world.rayTraceBlocks(vec3, vec3d1,true);

			if (movingbjectposition == null){
				return new ActionResult(EnumActionResult.FAIL, itemstack);
			}

			if (movingbjectposition.typeOfHit == RayTraceResult.Type.BLOCK){
				BlockPos blockpos = movingbjectposition.getBlockPos();
				if (world.isAirBlock(blockpos.add(0,1,0)) && world.isAirBlock(blockpos.add(0,2,0)) && blockpos.getY() <= player.posY+1){
					if (!world.isRemote){
						try{
							EntityElmBase summon = this.summonEntity.summonEntity(world,blockpos);
							if (summon != null){
								summon.setLocationAndAngles(blockpos.getX()+0.5F, blockpos.getY()+1, blockpos.getZ()+0.5F,
										(MathHelper.floor((double)((player.rotationYaw*4F)/360F)+2.5D)&3)*90,0.0F);
								summon.rotationYawHead = summon.rotationYaw;
								summon.renderYawOffset = summon.rotationYaw;
								summon.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(summon)), (IEntityLivingData)null);
								world.spawnEntity(summon);
								summon.Dw_SUMMONPOS(blockpos.add(0,1,0));
								summon.playLivingSound();
							}else{
								player.sendStatusMessage(new TextComponentString("can't summon"),false);
							}
						}catch(Exception e){
							ModLog.log().error(e.getMessage());
						}
					}
					if(!player.capabilities.isCreativeMode){
						itemstack.shrink(1);
					}
				}
			}
		return new ActionResult(EnumActionResult.SUCCESS,itemstack);
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
				if (EntityBladeSmith.canStartBladeSmith(world, pos, false, null)){
					EntityBladeSmith smith = new EntityBladeSmith(world);
					summon = smith;
				}
			}else if (this == ARMORSMITH){
				if (EntityArmorSmith.canStartArmorSmith(world, pos, false, null)){
					EntityArmorSmith smith = new EntityArmorSmith(world);
					summon = smith;
				}
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
