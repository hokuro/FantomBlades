package mod.fbd.inventory;

import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.item.ItemCore;
import mod.fbd.item.guns.ItemBurret;
import mod.fbd.item.guns.ItemBurret.EnumBurret;
import mod.fbd.item.guns.ItemBurretPotion;
import mod.fbd.util.ModUtil;
import mod.fbd.util.ModUtil.CompaierLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerGunCustomizer extends Container {
	   /** Here comes out item you merged and/or renamed. */
    private final IInventory outputSlot;
	private final IInventory inputSlot;

	private final PlayerInventory player;
	private final World world;


    public ContainerGunCustomizer(int id, PlayerInventory playerIn){
    	super(Mod_FantomBlade.CONTAINER_GUNCUSTOMIZER, id);
		world = playerIn.player.world;
		player = playerIn;//
		this.outputSlot = new InventoryGunCustomize();
		this.inputSlot = new Inventory(2) {
			@Override
			public void markDirty() {
				super.markDirty();
				ContainerGunCustomizer.this.onCraftMatrixChanged(this);
			}
		};


		this.addSlot(new Slot(inputSlot, 0, 44, 17){
			@Override
			public boolean isItemValid(ItemStack stack){
				return ContainerGunCustomizer.checkValidate(0,stack);
			}
		});

		this.addSlot(new Slot(inputSlot, 1, 44, 52){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return (stack.getItem() instanceof ItemBurret);
			}
		});
		this.addSlot(new Slot(outputSlot, 2, 98, 35) {
			@Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

			@Override
            public boolean canTakeStack(PlayerEntity playerIn)  {
                return this.getHasStack();
            }

			@Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
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

		// iプレイヤーインベントリ
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(player, k, 8 + k * 18, 142));
        }
	}

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.inputSlot) {
            this.updateRepairOutput();
        }
    }

    public void updateRepairOutput() {
    	ItemStack stack = makeResult(inputSlot.getStackInSlot(0), inputSlot.getStackInSlot(1));
        this.outputSlot.setInventorySlotContents(0, stack.copy());
        this.detectAndSendChanges();
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public void updateProgressBar(int id, int data) {
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote) {
            this.clearContainer(playerIn, this.world, this.inputSlot);
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())  {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 2) {
                if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), true))  {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0 && index != 1) {
                if (index >= 3 && index < this.inventorySlots.size() && !this.mergeItemStack(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, this.inventorySlots.size(), false)) {
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

	public static ItemStack makeResult(ItemStack stack0, ItemStack stack1){
		ItemStack ret = ItemStack.EMPTY;
		if (!stack0.isEmpty() && !stack1.isEmpty()){
			if (stack0.getItem() == ItemCore.item_gunpowder_draw){
				if (stack1.getItem() == ItemCore.item_burret){
					// i吸引弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_draw);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_expload){
				if (stack1.getItem() == ItemCore.item_burret){
					// i炸裂弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_explode);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_teleport){
				if (stack1.getItem() == ItemCore.item_burret){
					// i転移弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_teleport);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpwoder_flame){
				if (stack1.getItem() == ItemCore.item_burret){
					// i火炎弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_flame);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_heal) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i癒しの弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_heal);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_byako) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i白虎弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_byako);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_genbu) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i玄武弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_genbu);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_seiryu) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i青龍弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_seiryu);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_suzaku) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i朱雀弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_suzaku);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_kirin) {
				if (stack1.getItem() == ItemCore.item_burret){
					// i麒麟弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_kirin);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_wither) {
				if (stack1.getItem() == ItemCore.item_burret) {
					// iウィザー弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_wither);
				}
			}else if (stack0.getItem() == ItemCore.item_gunpowder_revitate) {
				if (stack1.getItem() == ItemCore.item_burret) {
					// iレビテート弾に更新
					ret = makeBurret(stack1, (ItemBurret)ItemCore.item_burret_revitate);
				}
			} else if (stack0.getItem() == ItemCore.item_gunpowder_water){
				if (((ItemBurret)stack1.getItem()).getBurret().canWater() && !ItemBurret.canWater(stack1)){
					// i水中弾に更新できるなら水中弾に更新
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.setCanWater(ret, true);
				}
			} else if (stack0.getItem() == ItemCore.item_steel){
				if (((ItemBurret)stack1.getItem()).getBurret().canFullmetal() && !ItemBurret.isFullMetal(stack1)){
					// i貫通弾に更新できるなら貫通弾に更新
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.setFullMetal(ret, true);
				}
			}else if (stack0.getItem() == Items.GUNPOWDER){
				if (ItemBurret.canAddGunPowder(stack1, 1)){
					// i炸薬を増量する
					ret = stack1.copy();
					ret.setCount(1);
					ItemBurret.addGunPowder(ret, 1);
				}
			}else if (stack0.getItem() == Items.POTION || stack0.getItem() == Items.SPLASH_POTION || stack0.getItem() == Items.LINGERING_POTION){
				if (stack1.getItem() == ItemCore.item_burret){
					// i通常弾にポーション効果をつける
					boolean fullmetal = ItemBurret.isFullMetal(stack1);
					boolean inwater = ItemBurret.canWater(stack1);
					int powder = ItemBurret.getGunPowder(stack1);
					EffectInstance effect = PotionUtils.getEffectsFromStack(stack0).get(0);

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
			new ItemStack(ItemCore.item_gunpowder_teleport),
			new ItemStack(ItemCore.item_gunpowder_draw),
			new ItemStack(ItemCore.item_gunpowder_expload),
			new ItemStack(ItemCore.item_gunpwoder_flame),
			new ItemStack(ItemCore.item_gunpowder_water),
			new ItemStack(ItemCore.item_gunpowder_heal),
			new ItemStack(ItemCore.item_gunpowder_suzaku),
			new ItemStack(ItemCore.item_gunpowder_kirin),
			new ItemStack(ItemCore.item_gunpowder_byako),
			new ItemStack(ItemCore.item_gunpowder_genbu),
			new ItemStack(ItemCore.item_gunpowder_seiryu),
			new ItemStack(ItemCore.item_gunpowder_wither),
			new ItemStack(ItemCore.item_gunpowder_revitate),
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


	protected static ItemStack makeBurret(ItemStack burret, ItemBurret nextBurret) {
		ItemStack ret = new ItemStack(nextBurret);
		boolean fullmetal = ItemBurret.isFullMetal(burret);
		boolean inwater = ItemBurret.canWater(burret);

		int powder = ItemBurret.getGunPowder(burret);
		EnumBurret burretType = nextBurret.getBurret();
		if (burretType.canFullmetal()){
			ItemBurret.setFullMetal(ret, fullmetal);
		}
		if (burretType.canWater()){
			ItemBurret.setCanWater(ret, inwater);
		}
		ItemBurret.addGunPowder(ret, powder);
		return ret;
	}
}
