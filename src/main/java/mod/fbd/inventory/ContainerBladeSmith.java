package mod.fbd.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBladeSmith extends Container {
	/** Instance of Merchant. */
    private final IInventory smithInventory;
    /** Instance of World. */
    private final World world;

    public ContainerBladeSmith(InventoryPlayer playerInventory, IInventory bladesmith, World worldIn)
    {
        this.smithInventory = bladesmith;
        this.world = worldIn;
        // 材料スロット
        for ( int r = 0; r < 2; r++){
        	for (int c = 0; c < 6; c++){
        		  this.addSlotToContainer(new Slot(this.smithInventory, c + (6*r), 8 + (18*c), 17 + (18*r))
      			{
      				public boolean isItemValid(ItemStack stack)
      				{
      					return  InventorySmith.allowItem(0, stack);
      				}
      			});
        	}
        }

        // 刀スロット
		this.addSlotToContainer(new Slot(this.smithInventory, 12, 138, 26)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  InventorySmith.allowItem(12, stack);
				}
			});

        // ツールスロット
		this.addSlotToContainer(new Slot(this.smithInventory, 13, 8, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(13, stack);
			}
		});


		this.addSlotToContainer(new Slot(this.smithInventory, 14, 26, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(14, stack);
			}
		});


		this.addSlotToContainer(new Slot(this.smithInventory, 15, 44, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(15, stack);
			}
		});


		this.addSlotToContainer(new Slot(this.smithInventory, 16, 62, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(16, stack);
			}
		});


        // プレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 169));
        }
    }

    public IInventory getSmithInventory()
    {
        return this.smithInventory;
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return smithInventory.isUsableByPlayer(playerIn);
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

            if (index > 16)
            {
                if (!this.mergeItemStack(itemstack1, 0, 17, false))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {
            	if (!this.mergeItemStack(itemstack1, 16, this.inventorySlots.size(), false))
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

        if (!this.world.isRemote)
        {
        	ItemStack itemstack;
        	for (int i = 0; i < 12; i++){
        		itemstack = this.smithInventory.getStackInSlot(i);
        		if (!itemstack.isEmpty()){
        			playerIn.dropItem(itemstack.copy(), false);
        			this.smithInventory.setInventorySlotContents(i,ItemStack.EMPTY);
        		}
        	}
        }
    }
}
