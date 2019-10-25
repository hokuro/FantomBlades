package mod.fbd.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVoid extends EntityRenderer<Entity> {

	public RenderVoid(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
    }
}
