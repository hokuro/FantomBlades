package mod.fbd.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;

public class ModelSmithBase<EntitySmithBase extends Entity> extends EntityModel<EntitySmithBase> {
	//fields
    public RendererModel head;
    public RendererModel body;
    public RendererModel rightarm;
    public RendererModel leftarm;
    public RendererModel rightleg;
    public RendererModel leftleg;


    public ModelSmithBase(){
		textureWidth = 64;
		textureHeight = 32;
		head = new RendererModel(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 32);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		body = new RendererModel(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 12, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(64, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightarm = new RendererModel(this, 40, 16);
		rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
		rightarm.setRotationPoint(-5F, 2F, 0F);
		rightarm.setTextureSize(64, 32);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new RendererModel(this, 40, 16);
		leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
		leftarm.setRotationPoint(5F, 2F, 0F);
		leftarm.setTextureSize(64, 32);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		rightleg = new RendererModel(this, 0, 16);
		rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		rightleg.setRotationPoint(-2F, 12F, 0F);
		rightleg.setTextureSize(64, 32);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		leftleg = new RendererModel(this, 0, 16);
		leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		leftleg.setRotationPoint(2F, 12F, 0F);
		leftleg.setTextureSize(64, 32);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
    }

    @Override
    public void render(EntitySmithBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
	    head.render(scale);
	    body.render(scale);
	    rightarm.render(scale);
	    leftarm.render(scale);
	    rightleg.render(scale);
	    leftleg.render(scale);
    }

    @Override
    public void setRotationAngles(EntitySmithBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

    }

    public void render(float f5){
	    head.render(f5);
	    body.render(f5);
	    rightarm.render(f5);
	    leftarm.render(f5);
	    rightleg.render(f5);
	    leftleg.render(f5);
    }

    private void setRotation(RendererModel model, float x, float y, float z){
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
    }

    double f = 2.5D;
    public void swing(int dw_Swing) {
    	double rotdeg = (-1.0D *((100.0D*Math.abs(Math.sin((double)dw_Swing/100.0D*f*Math.PI)))+30.D));
    	float rotx = (float)(rotdeg*Math.PI/180);
    	setRotation(this.rightarm, rotx, 0, 0);
    }

    public void resetSwing(){
    	if (this.rightarm.rotateAngleX != 0){
    		setRotation(this.rightarm, 0,0,0);
    	}
    }
}
