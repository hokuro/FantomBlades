package mod.fbd.item.guns;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.config.ConfigValue;
import mod.fbd.core.SoundManager;
import mod.fbd.entity.EntityBurret;
import mod.fbd.inventory.ContainerRevolver;
import mod.fbd.inventory.InventoryRevolver;
import mod.fbd.item.ItemCore;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemRevolver extends Item {
	public static final int BURRET_MAX = 6;
	public static final int RELOAD_TIME = 10;

	protected InventoryRevolver inventory;

	public ItemRevolver(Item.Properties property){
		super(property);
	}

    protected void gunFire(ItemStack stack, World worldIn, PlayerEntity entityLiving)
    {
    	if (entityLiving instanceof PlayerEntity)
        {
            PlayerEntity entityplayer = (PlayerEntity)entityLiving;
    		ItemStack burretStack = ItemRevolver.getNextBurret(stack);
            if (!worldIn.isRemote)
            {
            	EntityBurret entityburret = new EntityBurret(worldIn, entityLiving, burretStack);
            	entityburret.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, ItemBurret.getGunPowder(burretStack)+1 * 5.0F, 0.1F);
                stack.damageItem(1, entityplayer, (living)->{
                	living.sendBreakAnimation(Hand.MAIN_HAND);
                });
                worldIn.addEntity(entityburret);
            }
            // 残弾を減らす
            shootBurret(stack);
            worldIn.playSound((PlayerEntity)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundManager.sound_gun_gunshot, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F) + ItemBurret.getGunPowder(burretStack)+1 * 0.5F);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
    	if (handIn == Hand.MAIN_HAND){
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		ItemStack offHand = playerIn.getHeldItemOffhand();
    		ItemStack burretStack = ItemRevolver.getNextBurret(gunStack);
    		if (getReloadTime(gunStack) <= 0){
        		if (burretStack.isEmpty()){
        			// i残弾無し
            		if (ConfigValue.GENERAL.GunGuiAutoOpen() && offHand.isEmpty()){
                		if (!worldIn.isRemote) {
	            			// i片手が空いてるなら弾込めする
	            			NetworkHooks.openGui((ServerPlayerEntity)playerIn,
	                    			new SimpleNamedProvider(Hand.MAIN_HAND),
	                    			(buf)->{
	            						buf.writeInt(Hand.MAIN_HAND.ordinal());
	            					});
                		}
            		}else if (offHand.getItem() instanceof ItemBurret){
            			// i銃弾を持ってるなら弾込め
        				ItemStack newOffHand = reload(gunStack,offHand.copy());
            			if (!worldIn.isRemote){
                			playerIn.setHeldItem(Hand.OFF_HAND,newOffHand);
            			}
            			// iリロード時間設定(6発分)
            			setReloadTime(gunStack,RELOAD_TIME*6);
            		}else{
            			worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_noburret, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F));
            		}
        		}else{
        			// i残弾有りなら弾を打つ
        			gunFire(gunStack, worldIn, playerIn);

        			// iリロード時間設定
        			setReloadTime(gunStack,RELOAD_TIME);
        		}
    		}
    		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, gunStack);
    	}else{
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (!worldIn.isRemote) {
        		// iオフハンドで持っている場合GUIを開く
        		if (gunStack.getItem() instanceof ItemRevolver && playerIn.getHeldItemMainhand().isEmpty()){
        			NetworkHooks.openGui((ServerPlayerEntity)playerIn,
                			new SimpleNamedProvider(Hand.OFF_HAND),
                			(buf)->{
        						buf.writeInt(Hand.OFF_HAND.ordinal());
        					});
        		}
    		}
    		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, gunStack);
    	}
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (stack.getItem() instanceof ItemRevolver){
    		int time = getReloadTime(stack);
    		if (time > 0){
    			time--;
    			setReloadTime(stack,time);
    			if (time%RELOAD_TIME == 9){
    				worldIn.playSound((PlayerEntity)null, entityIn.posX, entityIn.posY, entityIn.posZ, SoundManager.sound_gun_reload, SoundCategory.PLAYERS, 1.0F, 1.0F / (ModUtil.randomF() * 0.4F + 1.2F));
    			}
    		}
    	}
    }

    public IInventory getInventory(ItemStack stack){
    	this.inventory = new InventoryRevolver(stack);
    	return inventory;
    }

    public static int getReloadTime(ItemStack stack){
    	CompoundNBT tag = getItemTagCompound(stack);
    	if (tag.contains("reloadtime")){
    		return tag.getInt("reloadtime");
    	}
    	return 0;
    }

    public static void setReloadTime(ItemStack stack, int value){
    	CompoundNBT tag = getItemTagCompound(stack);
    	tag.putInt("reloadtime", value);
    }

    public static ItemStack getNextBurret(ItemStack stack){
    	List<ItemStack> burrets = getBurrets(stack);
    	if (burrets.size() != 0){
    		return burrets.get(0);
    	}
    	return ItemStack.EMPTY;
    }

    public static void shootBurret(ItemStack stack){
    	List<ItemStack> burrets = getBurrets(stack);
    	if (burrets.size() != 0){
    		burrets.remove(0);
    	}
    	setBurrets(stack,burrets);
    }

    public static List<ItemStack> setBurrets(ItemStack stack, List<ItemStack> burrets){
    	List<ItemStack> retList = new ArrayList<ItemStack>();
    	CompoundNBT tag = getItemTagCompound(stack);
        ListNBT nbttaglist = new ListNBT();
        for (int i = 0; i < burrets.size(); ++i)
        {
            ItemStack itemstack = burrets.get(i);
            if (i < BURRET_MAX){
                if (!itemstack.isEmpty())
                {
                    nbttaglist.add(itemstack.write(new CompoundNBT()));
                }
                itemstack = ItemStack.EMPTY;
            }
            retList.add(itemstack);
        }
        tag.put("burrets", nbttaglist);
        return retList;
    }

    public static List<ItemStack> getBurrets(ItemStack stack){
    	List<ItemStack> retList = new ArrayList<ItemStack>();
    	CompoundNBT tag = getItemTagCompound(stack);
    	ListNBT nbttaglist = tag.getList("burrets", 10);
        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            ItemStack itemstack = ItemStack.read(nbttaglist.getCompound(i));
            if (!itemstack.isEmpty())
            {
            	retList.add(itemstack);
            }
        }
        return retList;
    }

    public static ItemStack reload(ItemStack stack, ItemStack burret){
    	List<ItemStack> setStack = new ArrayList<ItemStack>();
    	int loopMax = Math.min(burret.getCount(), BURRET_MAX);
    	ItemStack setBurret  = burret.copy();
    	setBurret.setCount(1);
    	for (int i = 0; i < loopMax; i++){
    		setStack.add(setBurret.copy());
    		burret.shrink(1);
    	}
    	ItemRevolver.setBurrets(stack, setStack);
    	if (burret.getCount() == 0){
    		burret = ItemStack.EMPTY;
    	}
    	return burret;
    }


    public static CompoundNBT getItemTagCompound(ItemStack stack){
		CompoundNBT tag;
		if(stack.hasTag()){
			tag = stack.getTag();
		}else{
			tag = new CompoundNBT();
			stack.setTag(tag);
		}
		return tag;
	}

    class SimpleNamedProvider implements INamedContainerProvider{
    	private Hand hand;

    	public SimpleNamedProvider(Hand handIn) {
    		hand = handIn;
    	}

		@Override
		public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity player) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == ItemCore.item_revolver) {
				return new ContainerRevolver(id, playerInv, ((ItemRevolver)stack.getItem()).getInventory(stack));
			}
			return null;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TranslationTextComponent("container.revolver");
		}

    }
}
