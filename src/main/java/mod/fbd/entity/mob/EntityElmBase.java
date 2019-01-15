package mod.fbd.entity.mob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityElmBase extends EntityCreature{

	/**************************************************************/
	/** DataWatcher                                              **/
	/**************************************************************/
	// 召喚された位置
    protected static final DataParameter<BlockPos> DW_SUMMONPOS = EntityDataManager.<BlockPos>createKey(EntityElmBase.class, DataSerializers.BLOCK_POS);
	public void Dw_SUMMONPOS(BlockPos value){
		this.dataManager.set(DW_SUMMONPOS, value);
	}

	public BlockPos Dw_SUMMONPOS(){
		return this.dataManager.get(DW_SUMMONPOS);
	}

	// レベル
	public abstract int getLevel();
    // 経験値
    protected static final DataParameter<Integer> DW_EXP = EntityDataManager.<Integer>createKey(EntityElmBase.class,DataSerializers.VARINT);
    public void Dw_Exp(int value){
		this.dataManager.set(DW_EXP, value);
	}
	public int Dw_Exp(){
		return this.dataManager.get(DW_EXP);
	}


    private int homeWorld;

    public EntityElmBase(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(DW_SUMMONPOS, new BlockPos(0,-1,0));
		this.dataManager.register(DW_EXP,0);
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
		// HomePosition
        compound.setInteger("HOMEX", getPosition().getX());
        compound.setInteger("HOMEY", getPosition().getY());
        compound.setInteger("HOMEZ", getPosition().getZ());
        compound.setInteger("HOMEWORLD", homeWorld);
		writeDWData(compound);
    }

    private void writeDWData(NBTTagCompound tagCompound){

    	NBTTagCompound dwtag = new NBTTagCompound();
        BlockPos summonPos = this.Dw_SUMMONPOS();
        dwtag.setInteger("summonPosX", summonPos.getX());
        dwtag.setInteger("summonPosY", summonPos.getY());
        dwtag.setInteger("summonPosZ", summonPos.getZ());
        dwtag.setInteger("level",getLevel());
        dwtag.setInteger("exp", Dw_Exp());
        tagCompound.setTag("dwData", dwtag);
    }



    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
		// HomePosition
		int lhx = compound.getInteger("HOMEX");
		int lhy = compound.getInteger("HOMEY");
		int lhz = compound.getInteger("HOMEZ");
		setHomePosAndDistance(new BlockPos(lhx, lhy, lhz),(int)getMaximumHomeDistance());
		homeWorld = compound.getInteger("HOEMWORLD");
		readDWData(compound);

    }


    private void readDWData(NBTTagCompound compound)
    {
    	NBTTagCompound dwtag = (NBTTagCompound)compound.getTag("dwData");

        this.Dw_SUMMONPOS(new BlockPos(
        		dwtag.getInteger("summonPosX"),
        		dwtag.getInteger("summonPosY"),
        		dwtag.getInteger("summonPosZ")));

        Dw_Exp(dwtag.getInteger("exp"));
    }

    public World getWorld()
    {
        return this.world;
    }

    public BlockPos getPos()
    {
        return new BlockPos(this);
    }
}