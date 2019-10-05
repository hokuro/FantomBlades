package mod.fbd.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.tileentity.TileEntity;

public class ModelAirPomp extends Model {
	private static final float[] rads = new float[]{
			-0.523598776F,
			-0.522550748F,
			-0.521751893F,
			-0.520700176F,
			-0.519329336F,
			-0.517560319F,
			-0.515300185F,
			-0.512441322F,
			-0.508861103F,
			-0.504422126F,
			-0.49897317F,
			-0.492350995F,
			-0.484383071F,
			-0.474891311F,
			-0.463696796F,
			-0.45062543F,
			-0.435514415F,
			-0.418219302F,
			-0.398621371F,
			-0.376634974F,
			-0.352214448F,
			-0.325360202F,
			-0.296123525F,
			-0.264609774F,
			-0.230979591F,
			-0.195447936F,
			-0.158280836F,
			-0.119789854F,
			-0.080324468F,
			-0.040262639F,
			0F,
			0.040262639F,
			0.080324468F,
			0.119789854F,
			0.158280836F,
			0.195447936F,
			0.230979591F,
			0.264609774F,
			0.296123525F,
			0.325360202F,
			0.352214448F,
			0.376634974F,
			0.398621371F,
			0.418219302F,
			0.435514415F,
			0.45062543F,
			0.463696796F,
			0.474891311F,
			0.484383071F,
			0.492350995F,
			0.49897317F,
			0.504422126F,
			0.508861103F,
			0.512441322F,
			0.515300185F,
			0.517560319F,
			0.519329336F,
			0.520700176F,
			0.521751893F,
			0.522550748F,
			0.523598776F,
	};
  //fields
    RendererModel bottom;
    RendererModel front;
    RendererModel back;
    RendererModel side1;
    RendererModel side2;
    RendererModel top;
    RendererModel pomp1;
    RendererModel pomp2;
    RendererModel pomp3;

  public ModelAirPomp()
  {
    textureWidth = 128;
    textureHeight = 64;

      bottom = new RendererModel(this, 49, 0);
      bottom.addBox(-3F, 0F, -7F, 10, 1, 14);
      bottom.setRotationPoint(0F, 0F, 0F);
      bottom.setTextureSize(128, 64);
      bottom.mirror = true;
      setRotation(bottom, 0F, 0F, 0F);
      front = new RendererModel(this, 0, 42);
      front.addBox(-3F, -8F, -8F, 10, 9, 1);
      front.setRotationPoint(0F, 0F, 0F);
      front.setTextureSize(128, 64);
      front.mirror = true;
      setRotation(front, 0F, 0F, 0F);
      back = new RendererModel(this, 23, 42);
      back.addBox(-3F, -8F, 7F, 10, 9, 1);
      back.setRotationPoint(0F, 0F, 0F);
      back.setTextureSize(128, 64);
      back.mirror = true;
      setRotation(back, 0F, 0F, 0F);
      side1 = new RendererModel(this, 0, 16);
      side1.addBox(7F, -8F, -8F, 1, 9, 16);
      side1.setRotationPoint(0F, 0F, 0F);
      side1.setTextureSize(128, 64);
      side1.mirror = true;
      setRotation(side1, 0F, 0F, 0F);
      side2 = new RendererModel(this, 35, 16);
      side2.addBox(-4F, -8F, -8F, 1, 9, 16);
      side2.setRotationPoint(0F, 0F, 0F);
      side2.setTextureSize(128, 64);
      side2.mirror = true;
      setRotation(side2, 0F, 0F, 0F);
      top = new RendererModel(this, 0, 0);
      top.addBox(-3F, 0F, -7F, 10, 1, 14);
      top.setRotationPoint(0F, -7F, 0F);
      top.setTextureSize(128, 64);
      top.mirror = true;
      setRotation(top, 0F, 0F, 0F);
      pomp1 = new RendererModel(this, 46, 42);
      pomp1.addBox(-6F, -1F, -1F, 2, 2, 3);
      pomp1.setRotationPoint(0F, 0F, 0F);
      pomp1.setTextureSize(128, 64);
      pomp1.mirror = true;
      setRotation(pomp1, 0F, 0F, 0F);
      pomp2 = new RendererModel(this, 46, 48);
      pomp2.addBox(-7F, 0F, 0F, 1, 1, 1);
      pomp2.setRotationPoint(0F, 0F, 0F);
      pomp2.setTextureSize(128, 64);
      pomp2.mirror = true;
      setRotation(pomp2, 0F, 0F, 0F);
      pomp3 = new RendererModel(this, 46, 48);
      pomp3.addBox(-8F, 0F, 0F, 1, 1, 1);
      pomp3.setRotationPoint(0F, 0F, 0F);
      pomp3.setTextureSize(128, 64);
      pomp3.mirror = true;
      setRotation(pomp2, 0F, 0F, 0F);
  }

  public void render(TileEntity entity, float scale, int cnt){
	bottom.render(scale);
	front.render(scale);
	back.render(scale);
	side1.render(scale);
	side2.render(scale);
	top.render(scale);
	pomp1.render(scale);
	pomp2.render(scale);
	pomp3.render(scale);
	setRotation(top,rads[cnt],0,0);
  }

  private void setRotation(RendererModel model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
  }
}
