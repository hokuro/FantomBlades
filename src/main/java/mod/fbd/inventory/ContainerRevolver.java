package mod.fbd.inventory;

import mod.fbd.item.ItemBurret;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRevolver extends Container{
	/** Instance of Merchant. */
    private final IInventory revolver;
    private final EntityPlayer player;

    public ContainerRevolver(EntityPlayer playerIn, IInventory gun)
    {
    	revolver = gun;
    	player = playerIn;
    	this.addSlot(new Slot(revolver, 0, 80, 17)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});
    	this.addSlot(new Slot(revolver, 1, 45, 37)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});
    	this.addSlot(new Slot(revolver, 2, 46, 66)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});
    	this.addSlot(new Slot(revolver, 3, 80, 86)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});
    	this.addSlot(new Slot(revolver, 4, 115, 66)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});
    	this.addSlot(new Slot(revolver, 5, 115, 37)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  (stack.getItem() instanceof ItemBurret);
				}
			});


        // プレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(player.inventory, k, 8 + k * 18, 169));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index > 5)
            {
                if (!this.mergeItemStack(itemstack1, 0, 6, false))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {
            	if (!this.mergeItemStack(itemstack1, 6, this.inventorySlots.size(), false))
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

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        revolver.closeInventory(playerIn);
    }
}