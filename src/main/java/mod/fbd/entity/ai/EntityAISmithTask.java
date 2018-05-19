package mod.fbd.entity.ai;

import mod.fbd.block.BlockCore;
import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EntityAISmithTask extends EntityAIBase {

    protected EntityBladeSmith entity;
    /** The closest entity which is being watched by this one. */
    protected Entity closestEntity;
    /** This is the Maximum distance that the AI will look for the Entity */
    protected float maxDistanceForPlayer;
    private int lookTime;
    protected Class <? extends Entity > watchedClass;


	public EntityAISmithTask(EntityBladeSmith entityIn) {
		entity = entityIn;
	}

	   /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	return ((EntityBladeSmith)entity).Dw_ISWORK();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
    	return ((EntityBladeSmith)entity).Dw_ISWORK();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {

    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
    	if (entity.checkWork(entity.world, entity.getPos())){
        	BlockPos pos = entity.getPosition();
        	if (entity.world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(EnumFacing.NORTH);
        	}else if (entity.world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(EnumFacing.SOUTH);
        	}else if (entity.world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(EnumFacing.EAST);
        	}else if (entity.world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(EnumFacing.WEST);
        	}
            this.entity.getLookHelper().setLookPosition(pos.getX(), pos.getY(), pos.getZ(), (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
            this.entity.addWorkTimeValue(-1);
            this.entity.addSwingValue(1);

            if (this.entity.Dw_WORKTIMER() <= 0){
            	entity.finishWork(entity.world, entity.getPos(),true);
            }
    	}else{
    		entity.finishWork(entity.world, entity.getPos(),false);
    	}
    }

}
