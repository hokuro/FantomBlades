package mod.fbd.entity.ai;

import mod.fbd.entity.mob.EntityBladeSmith;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAIWatchClosestSmith extends LookAtGoal {

	public EntityAIWatchClosestSmith(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
		super(entityIn, watchTargetClass, maxDistance);
	}

	@Override
    public boolean shouldExecute() {
    	if (entity instanceof EntityBladeSmith){
    		if (((EntityBladeSmith)entity).Dw_ISWORK()){
    			return false;
    		}
    	}

        if (this.entity.getAttackTarget() != null) {
            this.closestEntity = this.entity.getAttackTarget();
        }

        if (this.watchedClass == PlayerEntity.class) {
        	this.closestEntity = this.entity.world.getClosestPlayer(this.field_220716_e, this.entity, this.entity.posX, this.entity.posY + (double)this.entity.getEyeHeight(), this.entity.posZ);
        } else {
        	this.closestEntity = this.entity.world.func_225318_b(this.watchedClass, this.field_220716_e, this.entity, this.entity.posX, this.entity.posY + (double)this.entity.getEyeHeight(), this.entity.posZ, this.entity.getBoundingBox().grow((double)this.maxDistance, 3.0D, (double)this.maxDistance));
        }

        return this.closestEntity != null;
	}

	@Override
    public boolean shouldContinueExecuting() {
        if (!this.closestEntity.isAlive()) {
            return false;
        } else if (this.entity.getDistanceSq(this.closestEntity) > (double)(this.maxDistance * this.maxDistance)) {
            return false;
        } else {
            return true;
        }
    }

	@Override
    public void startExecuting()  {
    	super.startExecuting();
    }

    public void resetTask() {
        super.resetTask();
    }

    @Override
    public void tick()  {
    	this.entity.getLookController().func_220679_a(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ);
    }
}
