package mod.fbd.block;

import javax.annotation.Nullable;

import mod.fbd.inventory.ContainerGunCustomizer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockGunCustomizer extends Block {

	public BlockGunCustomizer(Block.Properties property) {
		super(property);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote){
			// GUI表示
    		NetworkHooks.openGui((ServerPlayerEntity)playerIn,
        			new INamedContainerProvider() {
    					@Override
    					@Nullable
    					public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
    						return new ContainerGunCustomizer(id, playerInv);
    					}

    					@Override
    					public ITextComponent getDisplayName() {
    						return new TranslationTextComponent("container.bladeforge");
    					}
    				},
        			(buf)->{
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
					});
		}
        return false;
    }


}
