package mod.fbd.entity.mob;

import mod.fbd.resource.TextureInfo;
import net.minecraft.entity.INpc;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntitySmithBase extends EntityElmBase implements INpc{
	/**************************************************************/
	/** DataWatcher                                              **/
	/**************************************************************/
	// テクスチャー
	 protected String ddtexture;
	 protected static final DataParameter<String> DW_TEXTURE = EntityDataManager.<String>createKey(EntityElmBase.class, DataSerializers.STRING);
		public void Dw_Texture(String value){
			this.dataManager.set(DW_TEXTURE, value);
		}

		public String Dw_Texture(){
			return this.dataManager.get(DW_TEXTURE);
		}
	// 作業状況
	protected static final DataParameter<Integer> DW_WORKTIMER = EntityDataManager.<Integer>createKey(EntityElmBase.class, DataSerializers.VARINT);
	public void Dw_WORKTIMER(int value){
		this.dataManager.set(DW_WORKTIMER, value);
	}

	public int Dw_WORKTIMER(){
		return this.dataManager.get(DW_WORKTIMER);
	}
	public void addWorkTimeValue(int value){
		Dw_WORKTIMER(Dw_WORKTIMER()+value);
	}

	// 作業状態
	protected static final DataParameter<Boolean> DW_ISWORK = EntityDataManager.<Boolean>createKey(EntityElmBase.class, DataSerializers.BOOLEAN);
	public void Dw_ISWORK(boolean value){
		this.dataManager.set(DW_ISWORK, value);
	}

	public boolean Dw_ISWORK(){
		return this.dataManager.get(DW_ISWORK);
	}

	// 腕ふり
	protected static final DataParameter<Integer> DW_SWING = EntityDataManager.<Integer>createKey(EntityElmBase.class, DataSerializers.VARINT);
	public void Dw_Swing(int value){
		this.dataManager.set(DW_SWING, value);
	}

	public int Dw_Swing() {
		return this.dataManager.get(DW_SWING);
	}
	public void addSwingValue(int value){
		Dw_Swing(Dw_Swing()+value);
	}


	public EntitySmithBase(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	public abstract boolean checkWork(World world, BlockPos pos);

    public abstract void finishWork(World world,BlockPos pos, boolean success);

	public abstract boolean updateTexture();

	public abstract void updateTexture(boolean flag);

	public abstract TextureInfo getTexture();



}
