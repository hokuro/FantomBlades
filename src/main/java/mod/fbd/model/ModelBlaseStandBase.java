package mod.fbd.model;

import mod.fbd.render.RenderTileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public abstract class ModelBlaseStandBase extends Model {
	public abstract void render(TileEntityBladeStand entity, float scale);
	public abstract void setRotation(RendererModel model, float x, float y, float z);
	public abstract void bladeRender(TileEntityBladeStand entity, double x, double y, double z, RenderTileEntityBladeStand renderTileEntityBladeStand);
}
