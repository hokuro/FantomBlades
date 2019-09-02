package mod.fbd.core;

import net.minecraft.entity.Entity;

public class CommonProxy{
	public CommonProxy(){
	}

	protected Entity target = null;
	public void setGuiTarget(Entity ent){
		target = ent;
	}

	public Entity getGuiTarget(){
		return target;
	}
}