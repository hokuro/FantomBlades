package mod.fbd.inventory;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.guns.ItemCartridge;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAutomatic extends Container{
	/** Instance of Merchant. */
    private final IInventory automatic;
    private final PlayerInventory player;

    public ContainerAutomatic(int id, PlayerInventory playerIn,  IInventory gun) {
    	super(Mod_FantomBlade.CONTAINER_AUTOMATIC, id);
    	automatic = gun;
    	player = playerIn;
    	this.addSlot(new Slot(automatic, 0, 86, 65)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemCartridge);
				}
			});
        // iプレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerIn, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(playerIn, k, 8 + k * 18, 169));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 1)
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {
            	if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        automatic.closeInventory(playerIn);
    }

    public IInventory gunInventory() {
    	return automatic;
    }
}
