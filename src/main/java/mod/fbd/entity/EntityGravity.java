package mod.fbd.entity;

import java.util.List;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityGravity extends Entity implements IEntityAdditionalSpawnData {
	public static final String NAME = "gravity";
	public static final int TICK_TIME = 20;
	public static final int DEFAULT_LIFE = 600;
	public static final int ADDITIONAL_LIFE = 300;
	public int tick_cnt = 0;
	public int life = 0;

	public EntityGravity(FMLPlayMessages.SpawnEntity packet, World world) {
		this(Mod_FantomBlade.RegistryEvents.GRAVITY, world);
	}

    public EntityGravity(EntityType<?> etype, World worldIn){
    	this(worldIn, 0, 0, 0);
    }

    public EntityGravity(World worldIn, double x, double y, double z){
    	super(Mod_FantomBlade.RegistryEvents.GRAVITY, worldIn);
    	this.setPosition(x, y, z);
    	life = DEFAULT_LIFE + ModUtil.random(ADDITIONAL_LIFE);
    }

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		life --;
		if (this.world.isRemote) {

		}else {
			tick_cnt++;
			if (tick_cnt >= TICK_TIME) {
				// i上空にあるブロックを1段下げる
				for (int y = (int) this.posY + 1; y < 257; y++) {
					BlockState stateU = world.getBlockState(new BlockPos(this.posX,y,this.posZ));
					BlockState stateB = world.getBlockState(new BlockPos(this.posX,y,this.posZ).offset(Direction.DOWN));
					if (!stateU.hasTileEntity() && stateU.getBlock() != Blocks.WATER && stateU.getBlock() != Blocks.LAVA && stateU.getBlock() != Blocks.BEDROCK) {
						if (stateB.isAir() && !stateU.isAir()) {
							world.setBlockState(new BlockPos(this.posX,y,this.posZ).offset(Direction.DOWN), stateU);
							world.setBlockState(new BlockPos(this.posX,y,this.posZ), Blocks.AIR.getDefaultState());
						}
					}
				}
				tick_cnt = 0;
			};
            List<LivingEntity> livings = ((ServerWorld)world).getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().expand(0,300,0));
            for (LivingEntity living : livings) {
            	living.onGround = false;
            	living.fallDistance += 20;
            }
    		if (life <= 0) {
    			this.remove();
    		}
		}
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void registerData() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		tick_cnt = compound.getInt("tick_cnt");
		life = compound.getInt("life");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.putInt("tick_cnt", tick_cnt);
		compound.putInt("life", life);

	}
}
