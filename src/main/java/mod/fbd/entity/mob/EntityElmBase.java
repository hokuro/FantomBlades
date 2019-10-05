package mod.fbd.entity.mob;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityElmBase extends CreatureEntity {

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

    public EntityElmBase(EntityType<? extends CreatureEntity> etype, World worldIn) {
        super(etype, worldIn);
    }

    @Override
    protected void registerData(){
        super.registerData();
        this.dataManager.register(DW_SUMMONPOS, new BlockPos(0,-1,0));
		this.dataManager.register(DW_EXP,0);
    }


    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        ListNBT ListNBT = new ListNBT();
		// HomePosition
        compound.putInt("HOMEX", getPosition().getX());
        compound.putInt("HOMEY", getPosition().getY());
        compound.putInt("HOMEZ", getPosition().getZ());
        compound.putInt("HOMEWORLD", homeWorld);
		writeDWData(compound);
    }

    private void writeDWData(CompoundNBT tagCompound){

    	CompoundNBT dwtag = new CompoundNBT();
        BlockPos summonPos = this.Dw_SUMMONPOS();
        dwtag.putInt("summonPosX", summonPos.getX());
        dwtag.putInt("summonPosY", summonPos.getY());
        dwtag.putInt("summonPosZ", summonPos.getZ());
        dwtag.putInt("level",getLevel());
        dwtag.putInt("exp", Dw_Exp());
        tagCompound.put("dwData", dwtag);
    }

    @Override
    public void readAdditional(CompoundNBT compound){
        super.readAdditional(compound);
		// HomePosition
		int lhx = compound.getInt("HOMEX");
		int lhy = compound.getInt("HOMEY");
		int lhz = compound.getInt("HOMEZ");
		setHomePosAndDistance(new BlockPos(lhx, lhy, lhz),(int)getMaximumHomeDistance());
		homeWorld = compound.getInt("HOEMWORLD");
		readDWData(compound);
    }

    private void readDWData(CompoundNBT compound){
    	CompoundNBT dwtag = (CompoundNBT)compound.get("dwData");

        this.Dw_SUMMONPOS(new BlockPos(
        		dwtag.getInt("summonPosX"),
        		dwtag.getInt("summonPosY"),
        		dwtag.getInt("summonPosZ")));

        Dw_Exp(dwtag.getInt("exp"));
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return new BlockPos(this);
    }

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
