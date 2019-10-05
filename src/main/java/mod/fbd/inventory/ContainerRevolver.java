package mod.fbd.inventory;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.guns.ItemBurret;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRevolver extends Container {
	/** Instance of Merchant. */
    private final IInventory revolver;
    private final PlayerInventory playerInv;

    public ContainerRevolver(int id, PlayerInventory playerIn, IInventory gun) {
    	super(Mod_FantomBlade.CONTAINER_REVOLVER, id);
    	revolver = gun;
    	playerInv = playerIn;
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


        // iプレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 169));
        }
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn){
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index){
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

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        revolver.closeInventory(playerIn);
    }

    public IInventory gunInventory() {
    	return revolver;
    }
}