package mod.fbd.item;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemMobSoule extends Item {
    public ItemMobSoule(Item.Properties property)
    {
    	super(property);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
           return EnumActionResult.SUCCESS;
        } else {
           ItemStack itemstack = context.getItem();
           BlockPos blockpos = context.getPos();
           EnumFacing enumfacing = context.getFace();
           IBlockState iblockstate = world.getBlockState(blockpos);
           Block block = iblockstate.getBlock();
           if (block == Blocks.SPAWNER) {
              TileEntity tileentity = world.getTileEntity(blockpos);
              if (tileentity instanceof TileEntityMobSpawner) {
                 MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
                 EntityType<?> entitytype1 = this.getType(itemstack.getTag());
                 if (entitytype1 != null) {
                    mobspawnerbaselogic.setEntityType(entitytype1);
                    tileentity.markDirty();
                    world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
                 }

                 itemstack.shrink(1);
                 return EnumActionResult.SUCCESS;
              }
           }

           BlockPos blockpos1;
           if (iblockstate.getCollisionShape(world, blockpos).isEmpty()) {
              blockpos1 = blockpos;
           } else {
              blockpos1 = blockpos.offset(enumfacing);
           }

           EntityType<?> entitytype = this.getType(itemstack.getTag());
           if (entitytype == null || entitytype.spawnEntity(world, itemstack, context.getPlayer(), blockpos1, true, !Objects.equals(blockpos, blockpos1) && enumfacing == EnumFacing.UP) != null) {
              itemstack.shrink(1);
           }

           return EnumActionResult.SUCCESS;
        }
     }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
           return new ActionResult<>(EnumActionResult.PASS, itemstack);
        } else {
           RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
           if (raytraceresult != null && raytraceresult.type == RayTraceResult.Type.BLOCK) {
              BlockPos blockpos = raytraceresult.getBlockPos();
              if (!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockFlowingFluid)) {
                 return new ActionResult<>(EnumActionResult.PASS, itemstack);
              } else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
                 EntityType<?> entitytype = this.getType(itemstack.getTag());
                 if (entitytype != null && entitytype.spawnEntity(worldIn, itemstack, playerIn, blockpos, false, false) != null) {
                    if (!playerIn.abilities.isCreativeMode) {
                       itemstack.shrink(1);
                    }

                    playerIn.addStat(StatList.ITEM_USED.get(this));
                    return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                 } else {
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);
                 }
              } else {
                 return new ActionResult<>(EnumActionResult.FAIL, itemstack);
              }
           } else {
              return new ActionResult<>(EnumActionResult.PASS, itemstack);
           }
        }
     }

    @Nullable
    public EntityType<?> getType(@Nullable NBTTagCompound p_208076_1_) {
       if (p_208076_1_ != null && p_208076_1_.contains("EntityTag", 10)) {
          NBTTagCompound nbttagcompound = p_208076_1_.getCompound("EntityTag");
          if (nbttagcompound.contains("id", 8)) {
             return EntityType.getById(nbttagcompound.getString("id"));
          }
       }
       return null;
    }

    public void setType(ItemStack stack, EntityType<?> etype) {
    	NBTTagCompound tag = stack.getOrCreateTag();
    	NBTTagCompound tag2 = new NBTTagCompound();
    	tag2.setString("id", etype.getRegistryName().toString());
    	tag.setTag("EntityTag",tag2);
    }

    public boolean hasType(@Nullable NBTTagCompound p_208077_1_, EntityType<?> p_208077_2_) {
        return Objects.equals(this.getType(p_208077_1_), p_208077_2_);
     }
}