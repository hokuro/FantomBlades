package mod.fbd.inventory;

import java.util.List;

import com.google.common.collect.Lists;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.katana.AbstractItemKatana;
import mod.fbd.tileentity.TileEntityBladeAlter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerBladeAlter extends Container {

	private TileEntityBladeAlter entity;
    private int state;
    private int tank_iron;
    private int tank_coal;
    private PlayerInventory player;
	private final List<IContainerListener> mylisteners = Lists.newArrayList();

    public ContainerBladeAlter(int id, PlayerInventory playerIn,  TileEntityBladeAlter tile) {
    	super(Mod_FantomBlade.CONTAINER_BLADEALTER, id);
		entity = tile;
		player = playerIn;
		// 材料スロット
		this.addSlot(new Slot(this.entity, 0, 8, 17)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  (stack.getItem() instanceof AbstractItemKatana);
			}
		});

        // プレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 126 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(player, k, 8 + k * 18, 184));
        }
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.entity.isUsableByPlayer(playerIn);
	}

	@Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.player.player.world.isRemote){
            this.clearContainer(playerIn, this.player.player.world, this.entity);
        }
    }

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 1, inventorySlots.size(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (!this.mylisteners.contains(listener)) {
			this.mylisteners.add(listener);
		}
		for(int i = 0; i < entity.getFieldCount(); ++i) {
			listener.sendWindowProperty(this, i, entity.getField(i));
		}
	}

	@Override
	public void removeListener(IContainerListener listener) {
		super.removeListener(listener);
		this.mylisteners.remove(listener);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.entity.setField(id, data);
	}

	public TileEntityBladeAlter getTileEntity() {
		return this.entity;
	}
}
