package mod.fbd.entity.ai;

import com.google.common.base.Predicates;

import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;

public class EntityAIWatchClosestSmith extends EntityAIWatchClosest {

	public EntityAIWatchClosestSmith(EntityLiving entityIn, Class<? extends Entity> watchTargetClass, float maxDistance) {
		super(entityIn, watchTargetClass, maxDistance);
	}

	   /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if (entity instanceof EntityBladeSmith){
    		if (((EntityBladeSmith)entity).Dw_ISWORK()){
    			return false;
    		}
    	}
        if (this.entity.getAttackTarget() != null)
        {
            this.closestEntity = this.entity.getAttackTarget();
        }

        if (this.watchedClass == EntityPlayer.class)
        {
            this.closestEntity = this.entity.world.getClosestPlayer(this.entity.posX, this.entity.posY, this.entity.posZ, (double)this.maxDistanceForPlayer, Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.notRiding(this.entity)));
        }
        else
        {
            this.closestEntity = this.entity.world.findNearestEntityWithinAABB(this.watchedClass, this.entity.getEntityBoundingBox().grow((double)this.maxDistanceForPlayer, 3.0D, (double)this.maxDistanceForPlayer), this.entity);
        }

        return this.closestEntity != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if (!this.closestEntity.isEntityAlive())
        {
            return false;
        }
        else if (this.entity.getDistanceSq(this.closestEntity) > (double)(this.maxDistanceForPlayer * this.maxDistanceForPlayer))
        {
            return false;
        }
        else
        {
            return true;
        }
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
        this.closestEntity = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.entity.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, (float)this.entity.getHorizontalFaceSpeed(), (float)this.entity.getVerticalFaceSpeed());
    }

}
