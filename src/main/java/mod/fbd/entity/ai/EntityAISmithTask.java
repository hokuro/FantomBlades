package mod.fbd.entity.ai;

import mod.fbd.block.BlockCore;
import mod.fbd.entity.mob.EntitySmithBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class EntityAISmithTask extends Goal {

    protected EntitySmithBase entity;
    /** The closest entity which is being watched by this one. */
    protected Entity closestEntity;
    /** This is the Maximum distance that the AI will look for the Entity */
    protected float maxDistanceForPlayer;
    private int lookTime;
    protected Class <? extends Entity > watchedClass;


	public EntityAISmithTask(EntitySmithBase entityIn) {
		entity = entityIn;
	}

	/**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
    public boolean shouldExecute() {
    	return ((EntitySmithBase)entity).Dw_ISWORK();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
	@Override
    public boolean shouldContinueExecuting() {
    	return ((EntitySmithBase)entity).Dw_ISWORK();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
	@Override
    public void startExecuting(){

    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
	@Override
    public void resetTask() {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
	@Override
    public void tick() {
    	if (entity.checkWork(entity.world, entity.getPos())){
        	BlockPos pos = entity.getPosition();
        	if (entity.world.getBlockState(pos.offset(Direction.NORTH)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(Direction.NORTH);
        	}else if (entity.world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(Direction.SOUTH);
        	}else if (entity.world.getBlockState(pos.offset(Direction.EAST)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(Direction.EAST);
        	}else if (entity.world.getBlockState(pos.offset(Direction.WEST)).getBlock() == BlockCore.block_anvildummy){
        		pos = pos.offset(Direction.WEST);
        	}
            this.entity.getLookController().setLookPosition(pos.getX(), pos.getY(), pos.getZ(), (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
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
