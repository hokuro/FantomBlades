package mod.fbd.model;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.fbd.render.RenderTileEntityBladeStand;
import mod.fbd.tileentity.TileEntityBladeStand;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelBladeStand1 extends ModelBlaseStandBase {
	 //fields
    RendererModel L1;
    RendererModel L2;
    RendererModel L3;
    RendererModel L4;
    RendererModel Shape4;
    RendererModel Shape6;
    RendererModel L5;
    RendererModel L6;
    RendererModel L7;
    RendererModel L8;
    RendererModel L9;
    RendererModel L10;
    RendererModel L11;
    RendererModel L12;
    RendererModel R1;
    RendererModel R2;
    RendererModel R3;
    RendererModel R4;
    RendererModel R5;
    RendererModel R6;
    RendererModel R7;
    RendererModel R8;
    RendererModel R9;
    RendererModel R10;
    RendererModel R11;
    RendererModel R12;

  public ModelBladeStand1()
  {
    textureWidth = 128;
    textureHeight = 64;

      L1 = new RendererModel(this, 0, 0);
      L1.addBox(4F, -1F, -7F, 4, 2, 14);
      L1.setRotationPoint(0F, 0F, 0F);
      L1.setTextureSize(128, 64);
      L1.mirror = true;
      setRotation(L1, 0F, 0F, 0F);
      L2 = new RendererModel(this, 36, 0);
      L2.addBox(4F, -2F, -6F, 4, 1, 12);
      L2.setRotationPoint(0F, 0F, 0F);
      L2.setTextureSize(128, 64);
      L2.mirror = true;
      setRotation(L2, 0F, 0F, 0F);
      L3 = new RendererModel(this, 68, 0);
      L3.addBox(4F, -3F, -5F, 4, 1, 10);
      L3.setRotationPoint(0F, 0F, 0F);
      L3.setTextureSize(128, 64);
      L3.mirror = true;
      setRotation(L3, 0F, 0F, 0F);
      L4 = new RendererModel(this, 0, 16);
      L4.addBox(4F, -4F, -2F, 1, 1, 4);
      L4.setRotationPoint(0F, 0F, 0F);
      L4.setTextureSize(128, 64);
      L4.mirror = true;
      setRotation(L4, 0F, 0F, 0F);
      Shape4 = new RendererModel(this, 49, 22);
      Shape4.addBox(-4F, -2F, -2F, 8, 1, 3);
      Shape4.setRotationPoint(0F, 0F, 0F);
      Shape4.setTextureSize(128, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape6 = new RendererModel(this, 50, 14);
      Shape6.addBox(-4F, -7F, -1F, 8, 5, 1);
      Shape6.setRotationPoint(0F, 0F, 0F);
      Shape6.setTextureSize(128, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      L5 = new RendererModel(this, 10, 16);
      L5.addBox(4F, -9F, -1F, 1, 5, 2);
      L5.setRotationPoint(0F, 0F, 0F);
      L5.setTextureSize(128, 64);
      L5.mirror = true;
      setRotation(L5, 0F, 0F, 0F);
      L6 = new RendererModel(this, 16, 16);
      L6.addBox(4F, -8F, -2F, 1, 3, 1);
      L6.setRotationPoint(0F, 0F, 0F);
      L6.setTextureSize(128, 64);
      L6.mirror = true;
      setRotation(L6, 0F, 0F, 0F);
      L7 = new RendererModel(this, 20, 16);
      L7.addBox(4F, -9F, -3F, 1, 2, 1);
      L7.setRotationPoint(0F, 0F, 0F);
      L7.setTextureSize(128, 64);
      L7.mirror = true;
      setRotation(L7, 0F, 0F, 0F);
      L8 = new RendererModel(this, 24, 16);
      L8.addBox(4F, -9F, 1F, 1, 2, 1);
      L8.setRotationPoint(0F, 0F, 0F);
      L8.setTextureSize(128, 64);
      L8.mirror = true;
      setRotation(L8, 0F, 0F, 0F);
      L9 = new RendererModel(this, 28, 16);
      L9.addBox(4F, -12F, 0F, 1, 3, 2);
      L9.setRotationPoint(0F, 0F, 0F);
      L9.setTextureSize(128, 64);
      L9.mirror = true;
      setRotation(L9, 0F, 0F, 0F);
      L10 = new RendererModel(this, 34, 16);
      L10.addBox(4F, -11F, 2F, 1, 3, 1);
      L10.setRotationPoint(0F, 0F, 0F);
      L10.setTextureSize(128, 64);
      L10.mirror = true;
      setRotation(L10, 0F, 0F, 0F);
      L11 = new RendererModel(this, 38, 16);
      L11.addBox(4F, -13F, -1F, 1, 2, 1);
      L11.setRotationPoint(0F, 0F, 0F);
      L11.setTextureSize(128, 64);
      L11.mirror = true;
      setRotation(L11, 0F, 0F, 0F);
      L12 = new RendererModel(this, 42, 16);
      L12.addBox(4F, -14F, 1F, 1, 2, 1);
      L12.setRotationPoint(0F, 0F, 0F);
      L12.setTextureSize(128, 64);
      L12.mirror = true;
      setRotation(L12, 0F, 0F, 0F);
      R1 = new RendererModel(this, 0, 48);
      R1.addBox(-8F, -1F, -7F, 4, 2, 14);
      R1.setRotationPoint(0F, 0F, 0F);
      R1.setTextureSize(128, 64);
      R1.mirror = true;
      setRotation(R1, 0F, 0F, 0F);
      R2 = new RendererModel(this, 36, 51);
      R2.addBox(-8F, -2F, -6F, 4, 1, 12);
      R2.setRotationPoint(0F, 0F, 0F);
      R2.setTextureSize(128, 64);
      R2.mirror = true;
      setRotation(R2, 0F, 0F, 0F);
      R3 = new RendererModel(this, 68, 53);
      R3.addBox(-8F, -3F, -5F, 4, 1, 10);
      R3.setRotationPoint(0F, 0F, 0F);
      R3.setTextureSize(128, 64);
      R3.mirror = true;
      setRotation(R3, 0F, 0F, 0F);
      R4 = new RendererModel(this, 0, 26);
      R4.addBox(-5F, -4F, -2F, 1, 1, 4);
      R4.setRotationPoint(0F, 0F, 0F);
      R4.setTextureSize(128, 64);
      R4.mirror = true;
      setRotation(R4, 0F, 0F, 0F);
      R5 = new RendererModel(this, 10, 26);
      R5.addBox(-5F, -9F, -1F, 1, 5, 2);
      R5.setRotationPoint(0F, 0F, 0F);
      R5.setTextureSize(128, 64);
      R5.mirror = true;
      setRotation(R5, 0F, 0F, 0F);
      R6 = new RendererModel(this, 16, 26);
      R6.addBox(-5F, -8F, -2F, 1, 3, 1);
      R6.setRotationPoint(0F, 0F, 0F);
      R6.setTextureSize(128, 64);
      R6.mirror = true;
      setRotation(R6, 0F, 0F, 0F);
      R7 = new RendererModel(this, 20, 26);
      R7.addBox(-5F, -9F, -3F, 1, 2, 1);
      R7.setRotationPoint(0F, 0F, 0F);
      R7.setTextureSize(128, 64);
      R7.mirror = true;
      setRotation(R7, 0F, 0F, 0F);
      R8 = new RendererModel(this, 24, 26);
      R8.addBox(-5F, -9F, 1F, 1, 2, 1);
      R8.setRotationPoint(0F, 0F, 0F);
      R8.setTextureSize(128, 64);
      R8.mirror = true;
      setRotation(R8, 0F, 0F, 0F);
      R9 = new RendererModel(this, 28, 26);
      R9.addBox(-5F, -12F, 0F, 1, 3, 2);
      R9.setRotationPoint(0F, 0F, 0F);
      R9.setTextureSize(128, 64);
      R9.mirror = true;
      setRotation(R9, 0F, 0F, 0F);
      R10 = new RendererModel(this, 34, 26);
      R10.addBox(-5F, -11F, 2F, 1, 3, 1);
      R10.setRotationPoint(0F, 0F, 0F);
      R10.setTextureSize(128, 64);
      R10.mirror = true;
      setRotation(R10, 0F, 0F, 0F);
      R11 = new RendererModel(this, 38, 26);
      R11.addBox(-5F, -13F, -1F, 1, 2, 1);
      R11.setRotationPoint(0F, 0F, 0F);
      R11.setTextureSize(128, 64);
      R11.mirror = true;
      setRotation(R11, 0F, 0F, 0F);
      R12 = new RendererModel(this, 42, 26);
      R12.addBox(-5F, -14F, 1F, 1, 2, 1);
      R12.setRotationPoint(0F, 0F, 0F);
      R12.setTextureSize(128, 64);
      R12.mirror = true;
      setRotation(R12, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {

    L1.render(f5);
    L2.render(f5);
    L3.render(f5);
    L4.render(f5);
    Shape4.render(f5);
    Shape6.render(f5);
    L5.render(f5);
    L6.render(f5);
    L7.render(f5);
    L8.render(f5);
    L9.render(f5);
    L10.render(f5);
    L11.render(f5);
    L12.render(f5);
    R1.render(f5);
    R2.render(f5);
    R3.render(f5);
    R4.render(f5);
    R5.render(f5);
    R6.render(f5);
    R7.render(f5);
    R8.render(f5);
    R9.render(f5);
    R10.render(f5);
    R11.render(f5);
    R12.render(f5);
  }

  @Override
  public void render(TileEntityBladeStand entity, float scale) {
  	L1.render(scale);
  	L2.render(scale);
  	L3.render(scale);
  	L4.render(scale);
  	Shape4.render(scale);
  	Shape6.render(scale);
  	L5.render(scale);
  	L6.render(scale);
  	L7.render(scale);
  	L8.render(scale);
  	L9.render(scale);
  	L10.render(scale);
  	L11.render(scale);
  	L12.render(scale);
  	R1.render(scale);
  	R2.render(scale);
  	R3.render(scale);
  	R4.render(scale);
  	R5.render(scale);
  	R6.render(scale);
  	R7.render(scale);
  	R8.render(scale);
  	R9.render(scale);
  	R10.render(scale);
  	R11.render(scale);
  	R12.render(scale);
  }

  @Override
  public void setRotation(RendererModel model, float x, float y, float z)
  {
	model.rotateAngleX = x;
	model.rotateAngleY = y;
	model.rotateAngleZ = z;
  }

  private ModelKatana katana1 = new ModelKatana();
  private ModelKatana katana2 = new ModelKatana();

  @Override
  public void bladeRender(TileEntityBladeStand stand, double x, double y, double z,RenderTileEntityBladeStand render){
	boolean has1 = stand.hasKatana(0);
	int idx = stand.getFace().getHorizontalIndex();
	katana1.setVisible(has1);
	if (has1){
			GlStateManager.pushMatrix();
			if (idx == 1){
				GlStateManager.translatef((float) x -0.1875F + 0.5F , (float) y + 0.5F + 0.06F, (float) z + 0.5F);
			}else if (idx == 3){
				GlStateManager.translatef((float) x +0.1875F + 0.5F , (float) y + 0.5F + 0.06F, (float) z + 0.5F);
			}else{
				GlStateManager.translatef((float) x + 0.5F , (float) y + 0.5F + 0.06F, (float) z + 0.5F);
			}
			GlStateManager.scaled(0.0625D,0.0625D,0.0625D);
			GlStateManager.rotatef(90F * (idx+2),0,1,0);
			render.bindTexturePublic(stand.getKatanaTexture(0));
			this.katana1.render(null, 0, 0, 0, 0, 0, 1.0F);
			GlStateManager.popMatrix();
	}

	boolean has2 = stand.hasKatana(1);
	katana2.setVisible(has2);
	if (has2){
			GlStateManager.pushMatrix();
			if (idx == 3){
				GlStateManager.translatef((float) x + 0.0625F + 0.5F , (float) y +  0.75f + 0.06F, (float) z + 0.5F);
			}else if (idx == 1){
				GlStateManager.translatef((float) x + -0.0625F + 0.5F , (float) y +  0.75f + 0.06F, (float) z + 0.5F);
			}else if (idx == 0){
				GlStateManager.translatef((float) x + 0.5F , (float) y +  0.75f + 0.06F, (float) z + -0.125F +0.5F);
			}else if (idx==2){
				GlStateManager.translatef((float) x + 0.5F , (float) y +  0.75f + 0.06F, (float) z + 0.125F +0.5F);
			}
			GlStateManager.scaled(0.0625D,0.0625D,0.0625D);
			GlStateManager.rotatef(90F * (idx+2),0,1,0);
			render.bindTexturePublic(stand.getKatanaTexture(1));
			this.katana2.render(null, 0, 0, 0, 0, 0, 1.0F);
			GlStateManager.popMatrix();
	}
  }
}