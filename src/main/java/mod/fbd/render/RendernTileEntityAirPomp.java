package mod.fbd.render;

import mod.fbd.model.ModelAirPomp;
import mod.fbd.tileentity.TileEntityAirPomp;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RendernTileEntityAirPomp  extends TileEntitySpecialRenderer<TileEntityAirPomp> {
	private static final ResourceLocation tex = new ResourceLocation("fbd:textures/entity/airpomp.png");

	private ModelAirPomp mainModel = new ModelAirPomp();

	@Override
	public void render(TileEntityAirPomp te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		dorender((TileEntityAirPomp)te,x,y,z,partialTicks,destroyStage);
	}

	public void dorender(TileEntityAirPomp te, double x, double y, double z, float partialTicks, int destroyStage) {
		double sx,sy,sz = 0.0D;
		double tx,ty,tz = 0.0D;
		sx = sy = sz = 0.03125D;
		tx = ty = tz = 0.5D;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(5.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }else{
    		this.bindTexture(tex);
        }

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F , (float) y +  0.06F, (float) z + 0.5F);
		GlStateManager.scale(0.0625D,0.0625D,0.0625D);
		GlStateManager.rotate(180,0F,0F,1F);
		int idx = te.getFace();
		GlStateManager.rotate((90F * idx) +90,0F,1F,0F);
		this.mainModel.render(te,1.0F);
		GlStateManager.popMatrix();


        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
	}
}
