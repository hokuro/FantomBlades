package mod.fbd.inventory;

import mod.fbd.entity.mob.EntitySmithBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSmithBase extends Container {
	protected InventorySmithBase smithInventory;
    protected PlayerInventory playerInv;

	public ContainerSmithBase(ContainerType<? extends ContainerSmithBase> containerType, int id,  PlayerInventory playerIn,  InventorySmithBase smith){
    	super(containerType, id);
        this.smithInventory = smith;
        this.playerInv = playerIn;
        // 材料スロット
        for (int i = 0; i < InventoryBladeSmith.INV_05_RESULT; i++)
        this.addSlot(new Slot(this.smithInventory, i, 8 + (i*18), 18 ) {
			public boolean isItemValid(ItemStack stack) {
				return smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
        });

        // 成果物スロット
		this.addSlot(new Slot(this.smithInventory, InventoryBladeSmith.INV_05_RESULT, 138, 26){
			public boolean isItemValid(ItemStack stack) {
				return  smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
		});

	    // ツールスロット
		// iビッグハンマー
		this.addSlot(new Slot(this.smithInventory, InventoryArmorSmith.INV_06_TOOL_BIGHAMMER, 8, 71) {
			public boolean isItemValid(ItemStack stack) {
				return  smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
		});

		// iスモールハンマー
		this.addSlot(new Slot(this.smithInventory, InventoryArmorSmith.INV_07_TOOL_SMALLHAMMER, 26, 71) {
			public boolean isItemValid(ItemStack stack) {
				return  smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
		});

		// iハサミ
		this.addSlot(new Slot(this.smithInventory, InventoryArmorSmith.INV_08_TOOL_SHEARS, 44, 71) {
			public boolean isItemValid(ItemStack stack) {
				return  smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
		});

		// 石炭/木炭
		this.addSlot(new Slot(this.smithInventory, InventoryArmorSmith.INV_09_TOOL_COAL, 62, 71) {
			public boolean isItemValid(ItemStack stack) {
				return  smithInventory.isItemValidForSlot(this.slotNumber, stack);
			}
		});


	    // プレイヤースロット
	    for (int i = 0; i < 3; ++i) {
	        for (int j = 0; j < 9; ++j) {
	            this.addSlot(new Slot(playerIn, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
	        }
	    }

	    for (int k = 0; k < 9; ++k) {
	        this.addSlot(new Slot(playerIn, k, 8 + k * 18, 169));
	    }
	}

    public IInventory getSmithInventory() {
        return this.smithInventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return smithInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // プレイヤー -> 職人
            if (index > this.smithInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, 0, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else {
            	// 職人 -> プレイヤー
                if (!this.mergeItemStack(itemstack1,this.smithInventory.getSizeInventory(), inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } if (itemstack1.isEmpty()) {
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

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);

        if (!this.playerInv.player.world.isRemote)  {
        	ItemStack itemstack;
        	int lpmax = this.getSmith().Dw_ISWORK()?InventorySmithBase.INV_05_RESULT:InventorySmithBase.INV_06_TOOL_BIGHAMMER;
            for (int i = 0; i < lpmax; i++){
        		itemstack = this.smithInventory.getStackInSlot(i);
        		if (!itemstack.isEmpty()){
        			playerIn.dropItem(itemstack.copy(), false);
        			this.smithInventory.setInventorySlotContents(i,ItemStack.EMPTY);
        		}
        	}
        }
    }

    public EntitySmithBase getSmith() {return this.smithInventory.getSmith();}
    public int getSmithId() {return this.smithInventory.getSmith().getEntityId();}
}
