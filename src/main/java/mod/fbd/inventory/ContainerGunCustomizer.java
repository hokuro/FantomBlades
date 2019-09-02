package mod.fbd.inventory;

import mod.fbd.item.ItemBurret;
import mod.fbd.item.ItemBurret.EnumBurret;
import mod.fbd.item.ItemBurretPotion;
import mod.fbd.item.ItemCore;
import mod.fbd.util.ModUtil;
import mod.fbd.util.ModUtil.CompaierLevel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerGunCustomizer extends Container {
    /** Here comes out item you merged and/or renamed. */
    private final IInventory outputSlot;
	private final IInventory inputSlot;

	private final EntityPlayer player;
	private final World world;


	public ContainerGunCustomizer(EntityPlayer playerIn, World worldIn){
		world = worldIn;
		player = playerIn;//
	    this.outputSlot = new InventoryGunCustomize();
	    this.inputSlot = new InventoryBasic(new TextComponentTranslation("Repair"), 2)
	    {
	        /**
	         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't
	         * think it hasn't changed and skip it.
	         */
	        public void markDirty()
	        {
	            super.markDirty();
	            ContainerGunCustomizer.this.onCraftMatrixChanged(this);
	        }
	    };


		this.addSlot(new Slot(inputSlot, 0, 44, 17){
			@Override
	          public boolean isItemValid(ItemStack stack)
	          {
	              return ContainerGunCustomizer.checkValidate(0,stack);
	          }
		});

		this.addSlot(new Slot(inputSlot, 1, 44, 52){
			@Override
	          public boolean isItemValid(ItemStack stack)
	          {
	              return (stack.getItem() instanceof ItemBurret);
	          }
		});
		this.addSlot(new Slot(outputSlot, 2, 98, 35)
		{
			/**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }
            /**
             * Return whether this slot's stack can be taken from this slot.
             */
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return this.getHasStack();
            }

            public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
            {

            	for (int i = 0; i < 2; i++){
            		ItemStack stack0 = ContainerGunCustomizer.this.inputSlot.getStackInSlot(i);
                	stack0.shrink(stack.getCount());
                	if (stack0.getCount() != 0){
                		ContainerGunCustomizer.this.inputSlot.setInventorySlotContents(i, stack0);
                	}else{
                		ContainerGunCustomizer.this.inputSlot.setInventorySlotContents(i, ItemStack.EMPTY);
                	}
            	}
                return stack;
            }
		});

		// プレイヤーインベントリ
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlot(new Slot(player.inventory, k, 8 + k * 18, 142));
        }
	}

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.inputSlot)
        {
            this.updateRepairOutput();
        }
    }

    /**
     * called when the Anvil Input Slot changes, calculates the new result and puts it in the output slot
     */
    public void updateRepairOutput()
    {
    	ItemStack stack = makeResult(inputSlot.getStackInSlot(0), inputSlot.getStackInSlot(1));
    	//this.inputSlot.setInventorySlotContents(2, stack.copy());
        this.outputSlot.setInventorySlotContents(0, stack.copy());
        this.detectAndSendChanges();
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void updateProgressBar(int id, int data)
    {
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.inputSlot);
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

            if (index < 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 0 && index != 1)
            {
                if (index >= 3 && index < this.inventorySlots.size() && !this.mergeItemStack(itemstack1, 0, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), false))
            {
                return ItemStack.EMPTY;
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

	public static ItemStack makeResult(ItemStack stack0, ItemStack stack1){
		ItemStack ret = ItemStack.EMPTY;
		if (!stack0.isEmpty() && !stack1.isEmpty()){
			if (stack0.getItem() == ItemCore.item_gunpowder_draw){
				if (stack1.getItem() == ItemCore.item_burret){
					// 吸引弾に更新
					boolean fullmetal = ItemBurret.isFullMetal(stack1);
					boolean inwater = ItemBurret.canWater(stack1);
					int powder = ItemBurret.getGunPowder(stack1);

					ret = new ItemStack(ItemCore.item_burret_draw);
					if (EnumBurret.DRAW.canFullmetal()){
						ItemBurret.setFullMetal(ret, fullmetal);
					}
					if (EnumBurret.DRAW.canWater()){
						ItemBurret.setCanWater(ret, inwater);
					}
					ItemBurret.addGunPowder(ret, powder);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_expload){
				if (stack1.getItem() == ItemCore.item_burret){
					// 炸裂弾に更新
					boolean fullmetal = ItemBurret.isFullMetal(stack1);
					boolean inwater = ItemBurret.canWater(stack1);
					int powder = ItemBurret.getGunPowder(stack1);

					ret = new ItemStack(ItemCore.item_burret_explode);
					if (EnumBurret.EXPLOSION.canFullmetal()){
						ItemBurret.setFullMetal(ret, fullmetal);
					}
					if (EnumBurret.EXPLOSION.canWater()){
						ItemBurret.setCanWater(ret, inwater);
					}
					ItemBurret.addGunPowder(ret, powder);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_teleport){
				if (stack1.getItem() == ItemCore.item_burret){
					// 転移弾に更新
					boolean fullmetal = ItemBurret.isFullMetal(stack1);
					boolean inwater = ItemBurret.canWater(stack1);
					int powder = ItemBurret.getGunPowder(stack1);

					ret = new ItemStack(ItemCore.item_burret_teleport);
					if (EnumBurret.TELEPORT.canFullmetal()){
						ItemBurret.setFullMetal(ret, fullmetal);
					}
					if (EnumBurret.TELEPORT.canWater()){
						ItemBurret.setCanWater(ret, inwater);
					}
					ItemBurret.addGunPowder(ret, powder);
				}

			}else if (stack0.getItem() == ItemCore.item_gunpowder_water){
				if (((ItemBurret)stack1.getItem()).getBurret().canWater() && !ItemBurret.canWater(stack1)){
					// 水中弾に更新できるなら水中弾に更新
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.setCanWater(ret, true);
				}
			}else if (stack0.getItem() == ItemCore.item_steel){
				if (((ItemBurret)stack1.getItem()).getBurret().canFullmetal() && !ItemBurret.isFullMetal(stack1)){
					// 貫通弾に更新できるなら貫通弾に更新
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.setFullMetal(ret, true);
				}
			}else if (stack0.getItem() == Items.GUNPOWDER){
				if (ItemBurret.canAddGunPowder(stack1, 1)){
					// 炸薬を増量する
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.addGunPowder(ret, 1);
				}
			}else if (stack0.getItem() == Items.POTION || stack0.getItem() == Items.SPLASH_POTION || stack0.getItem() == Items.LINGERING_POTION){
				if (stack1.getItem() == ItemCore.item_burret){
					// 通常弾にポーション効果をつける
					boolean fullmetal = ItemBurret.isFullMetal(stack1);
					boolean inwater = ItemBurret.canWater(stack1);
					int powder = ItemBurret.getGunPowder(stack1);
					PotionEffect effect = PotionUtils.getEffectsFromStack(stack0).get(0);

					ret = new ItemStack(ItemCore.item_burret_potion,Math.min(8, stack1.getCount()));
					ItemBurret.setFullMetal(ret, fullmetal);
					ItemBurret.setCanWater(ret, inwater);
					ItemBurret.addGunPowder(ret, powder);

					if (stack0.getItem() == Items.POTION){
						ItemBurretPotion.setPotionType(ret, 1);
					}else if(stack0.getItem() == Items.SPLASH_POTION){
						ItemBurretPotion.setPotionType(ret, 2);
					}else{
						ItemBurretPotion.setPotionType(ret, 3);
					}
					ItemBurretPotion.setPotionEffect(ret, effect);
				}
			}
		}

		return ret;
	}

	public static final ItemStack[] slot0Validate = {
			new ItemStack(Items.GUNPOWDER),
			new ItemStack(ItemCore.item_gunpowder_draw),
			new ItemStack(ItemCore.item_gunpowder_expload),
			new ItemStack(ItemCore.item_gunpowder_teleport),
			new ItemStack(ItemCore.item_gunpowder_water),
			new ItemStack(ItemCore.item_steel),
			new ItemStack(Items.POTION),
			new ItemStack(Items.SPLASH_POTION),
			new ItemStack(Items.LINGERING_POTION)};

	public static boolean checkValidate(int index, ItemStack stack){
		if (index == 0){
			Item item = stack.getItem();
			if (item == Items.POISONOUS_POTATO || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION){
				if (PotionUtils.getEffectsFromStack(stack).size() < 1){
					return false;
				}
			}
			for (ItemStack valid : slot0Validate){
				if (ModUtil.compareItemStacks(valid, stack, CompaierLevel.LEVEL_EQUAL_ITEM)){
					return true;
				}
			}
		}else if (index == 1){
			if (stack.getItem() instanceof ItemBurret && !(stack.getItem() instanceof ItemBurretPotion)){
				return true;
			}
		}
		return false;
	}
}
