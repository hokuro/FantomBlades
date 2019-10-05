package mod.fbd.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;

public class ModelKatana extends Model{
	  //fields
	    RendererModel Shape16;
	    RendererModel Shape17;
	    RendererModel Shape18;
	    RendererModel Shape19;
	    RendererModel Shape20;

	  public ModelKatana()
	  {
	    textureWidth = 64;
	    textureHeight = 32;

	      Shape16 = new RendererModel(this, 8, 0);
	      Shape16.addBox(-6F, 0F, -2F, 16, 1, 1);
	      Shape16.setRotationPoint(0F, 0F, 0F);
	      Shape16.setTextureSize(64, 32);
	      Shape16.mirror = true;
	      setRotation(Shape16, 0F, 0F, 0F);
	      Shape17 = new RendererModel(this, 0, 3);
	      Shape17.addBox(-7F, -1F, -2F, 1, 3, 1);
	      Shape17.setRotationPoint(0F, 0F, 0F);
	      Shape17.setTextureSize(64, 32);
	      Shape17.mirror = true;
	      setRotation(Shape17, 0F, 0F, 0F);
	      Shape18 = new RendererModel(this, 5, 5);
	      Shape18.addBox(-7F, 0F, -1F, 1, 1, 1);
	      Shape18.setRotationPoint(0F, 0F, 0F);
	      Shape18.setTextureSize(64, 32);
	      Shape18.mirror = true;
	      setRotation(Shape18, 0F, 0F, 0F);
	      Shape19 = new RendererModel(this, 5, 3);
	      Shape19.addBox(-7F, 0F, -3F, 1, 1, 1);
	      Shape19.setRotationPoint(0F, 0F, 0F);
	      Shape19.setTextureSize(64, 32);
	      Shape19.mirror = true;
	      setRotation(Shape19, 0F, 0F, 0F);
	      Shape20 = new RendererModel(this, 0, 0);
	      Shape20.addBox(-10F, 0F, -2F, 3, 1, 1);
	      Shape20.setRotationPoint(0F, 0F, 0F);
	      Shape20.setTextureSize(64, 32);
	      Shape20.mirror = true;
	      setRotation(Shape20, 0F, 0F, 0F);
	  }

	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
	    Shape16.render(f5);
	    Shape17.render(f5);
	    Shape18.render(f5);
	    Shape19.render(f5);
	    Shape20.render(f5);
	  }

	  private void setRotation(RendererModel model, float x, float y, float z)
	  {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	  }

	  public void setVisible(boolean value){
		Shape16.showModel = value;
		Shape17.showModel = value;
		Shape18.showModel = value;
		Shape19.showModel = value;
		Shape20.showModel = value;
	  }

	}
