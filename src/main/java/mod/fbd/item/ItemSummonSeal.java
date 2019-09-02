package mod.fbd.item;

import mod.fbd.core.log.ModLog;
import mod.fbd.entity.mob.EntityArmorSmith;
import mod.fbd.entity.mob.EntityBladeSmith;
import mod.fbd.entity.mob.EntityElmBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemSummonSeal extends Item {

	private EnumSummonType summonEntity;

	ItemSummonSeal(EnumSummonType summon, Item.Properties property){
		super(property);
		summonEntity = summon;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		ItemStack itemstack = player.getHeldItem(hand);
			RayTraceResult movingbjectposition = this.rayTrace(world, player,true);

			if (movingbjectposition == null){
				return new ActionResult(EnumActionResult.FAIL, itemstack);
			}

			if (movingbjectposition.type == RayTraceResult.Type.BLOCK){
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
								summon.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(summon)), (IEntityLivingData)null, new NBTTagCompound());
								world.spawnEntity(summon);
								summon.Dw_SUMMONPOS(blockpos.add(0,1,0));
							}else{
								player.sendStatusMessage(new TextComponentString("can't summon"),false);
							}
						}catch(Exception e){
							ModLog.log().error(e.getMessage());
						}
					}
					if(!player.isCreative()){
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
