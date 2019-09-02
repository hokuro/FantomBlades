package mod.fbd.inventory;

import mod.fbd.item.ItemBladePiece;
import mod.fbd.item.ItemTamahagane;
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
//        // 材料スロット
//        for ( int r = 0; r < 2; r++){
//        	/////for (int c = 0; c < 6; c++){
//        	for(int c = 0; c < 1; c++){
//        		  this.addSlot(new Slot(this.smithInventory, c + (6*r), 8 + (18*c), 17 + (18*r))
//      			{
//      				public boolean isItemValid(ItemStack stack)
//      				{
//      					return  InventorySmith.allowItem(0, stack);
//      				}
//      			});
//        	}
//        }
        // 玉鋼スロット
        this.addSlot(new Slot(this.smithInventory, 0, 8, 17)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return (stack.getItem() instanceof ItemTamahagane);
				}
			});
        // 欠片スロット
        this.addSlot(new Slot(this.smithInventory, 1, 8, 35)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return (stack.getItem() instanceof ItemBladePiece);
				}
			});

        // 刀スロット
		this.addSlot(new Slot(this.smithInventory, 2, 138, 26)
			{
				public boolean isItemValid(ItemStack stack)
				{
					return  InventorySmith.allowItem(2, stack);
				}
			});

        // ツールスロット
		this.addSlot(new Slot(this.smithInventory, 3, 8, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(3, stack);
			}
		});


		this.addSlot(new Slot(this.smithInventory, 4, 26, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(4, stack);
			}
		});


		this.addSlot(new Slot(this.smithInventory, 5, 44, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(5, stack);
			}
		});


		this.addSlot(new Slot(this.smithInventory, 6, 62, 71)
		{
			public boolean isItemValid(ItemStack stack)
			{
				return  InventorySmith.allowItem(6, stack);
			}
		});


        // プレイヤースロット
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 169));
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

            //////if (index > 16)
            if (index > 6)
            {
                /////if (!this.mergeItemStack(itemstack1, 0, 17, false))
                if (!this.mergeItemStack(itemstack1, 0, 7, false))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else
            {
            	/////if (!this.mergeItemStack(itemstack1, 16, this.inventorySlots.size(), false))
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

        if (!this.world.isRemote)
        {
        	ItemStack itemstack;
        	/////for (int i = 0; i < 12; i++){
            for (int i = 0; i < 2; i++){
        		itemstack = this.smithInventory.getStackInSlot(i);
        		if (!itemstack.isEmpty()){
        			playerIn.dropItem(itemstack.copy(), false);
        			this.smithInventory.setInventorySlotContents(i,ItemStack.EMPTY);
        		}
        	}
        }
    }
}
