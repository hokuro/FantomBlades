package mod.fbd.item.guns;

import java.util.ArrayList;
import java.util.List;

import mod.fbd.config.ConfigValue;
import mod.fbd.core.Mod_FantomBlade;
import mod.fbd.core.SoundManager;
import mod.fbd.entity.EntityBurret;
import mod.fbd.inventory.ContainerAutomatic;
import mod.fbd.inventory.InventoryAutomatic;
import mod.fbd.item.ItemCore;
import mod.fbd.util.ModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemAutomatic extends Item {
	public static final int BURRET_MAX = 6;
	public static final int RELOAD_TIME = 5;

	protected InventoryAutomatic inventory;

	public ItemAutomatic(Item.Properties property){
		super(property);
	}

    protected void gunFire(ItemStack stack, World worldIn, PlayerEntity entityLiving)
    {
    	if (entityLiving instanceof PlayerEntity)
        {
            PlayerEntity entityplayer = (PlayerEntity)entityLiving;
    		ItemStack burretStack = getNextBurret(stack);
            if (!worldIn.isRemote)
            {
            	EntityBurret entityburret = new EntityBurret(worldIn, entityLiving, burretStack);
            	entityburret.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, ItemBurret.getGunPowder(burretStack)+1 * 5.0F, 0.1F);
                stack.damageItem(1, entityplayer, (entity)->{entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
                worldIn.addEntity(entityburret);
            }
            // 残弾を減らす
            shootBurret(stack);
            worldIn.playSound((PlayerEntity)null,
            		entityplayer.posX,
            		entityplayer.posY,
            		entityplayer.posZ,
            		SoundManager.sound_gun_gunshot,
            		SoundCategory.PLAYERS, 1.0F,
            		(float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F) + ItemBurret.getGunPowder(burretStack)+1 * 0.5F));
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.NONE;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
    	if (handIn == Hand.MAIN_HAND){
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		ItemStack offHand = playerIn.getHeldItemOffhand();
    		ItemStack burretStack = getNextBurret(gunStack);
    		if (getReloadTime(gunStack) <= 0){
        		if (burretStack.isEmpty()){
        			// i残弾無し
            		if (ConfigValue.GENERAL.GunGuiAutoOpen() && offHand.isEmpty()){
            			if (!worldIn.isRemote) {
	            			// i片手が空いてるなら弾込めする
	            			// GUI表示
	                		NetworkHooks.openGui((ServerPlayerEntity)playerIn,
	                    			new SimpleNamedProvider(Hand.MAIN_HAND),
	                    			(buf)->{
	            						buf.writeInt(Hand.MAIN_HAND.ordinal());
	            					});
            			}
            		}else if (offHand.getItem() instanceof ItemCartridge && ItemCartridge.getBurrets(offHand).size() > 0){
            			// i弾入りのカートリッジを持っている場合交換
        				ItemStack retcartridge = reload(gunStack,offHand.copy());
            			if (!worldIn.isRemote){
                			playerIn.setHeldItem(Hand.OFF_HAND,ItemStack.EMPTY);
                			if (!retcartridge.isEmpty()){
                				// i元のカートリッジを足元へ
                				ModUtil.spawnItemStack(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, retcartridge, Mod_FantomBlade.rnd);
                			}
            			}
            			// iリロード時間設定(6発分)
            			setReloadTime(gunStack,RELOAD_TIME*2);
            			worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_reload, SoundCategory.PLAYERS, 1.0F, (float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F)));
            		}else{
            			worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundManager.sound_gun_noburret, SoundCategory.PLAYERS, 1.0F,  (float)(1.0F / (ModUtil.randomF() * 0.4F + 1.2F)));
        			}
        		}else{
        			if (ModUtil.random(100000) > 8){
        				// i残弾ありなら弾を打つ
        				gunFire(gunStack, worldIn, playerIn);
            			// iリロード時間設定
            			setReloadTime(gunStack,RELOAD_TIME);
        			}else{
        				// 0.0008 %の確率でジャム
        				// i詰まった弾を捨てる
        				shootBurret(gunStack);
            			// iリロード時間設定(2.5秒)
            			setReloadTime(gunStack,RELOAD_TIME*10);
        			}
        		}
    		}
    		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, gunStack);
    	}else{
    		// iオフハンドで持っている場合GUIを開く
    		ItemStack gunStack = playerIn.getHeldItem(handIn);
    		if (gunStack.getItem() instanceof ItemAutomatic && playerIn.getHeldItemMainhand().isEmpty()){
    			if (!worldIn.isRemote) {
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
    	if (stack.getItem() instanceof ItemAutomatic){
    		int time = getReloadTime(stack);
    		if (time > 0){
    			time--;
    			setReloadTime(stack,time);
    		}
    	}
    }

    public IInventory getInventory(ItemStack stack){
    	this.inventory = new InventoryAutomatic(stack);
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
    	ItemStack cartridge = getCartridge(stack);
    	if (cartridge.isEmpty()){
    		return burrets;
    	}
    	return ItemCartridge.setBurrets(cartridge,burrets);
    }

    public static List<ItemStack> getBurrets(ItemStack stack){
    	List<ItemStack> retList = new ArrayList<ItemStack>();

    	ItemStack cartridge = getCartridge(stack);
    	if (cartridge.isEmpty()){
    		return retList;
    	}
    	return ItemCartridge.getBurrets(cartridge);
    }

    public static ItemStack reload(ItemStack stack, ItemStack cartridge){
    	List<ItemStack> setStack = new ArrayList<ItemStack>();
    	return setCartridge(stack,cartridge);
    }

    public static ItemStack setCartridge(ItemStack stack, ItemStack cartridge){
    	CompoundNBT tag = getItemTagCompound(stack);
    	ItemStack ret= getCartridge(stack).copy();
    	if (cartridge.isEmpty()){
    		tag.remove("cartridge");
    	}else{
    		tag.put("cartridge", cartridge.write(new CompoundNBT()));
    	}
    	return ret;
    }

    public static ItemStack getCartridge(ItemStack stakc){
    	CompoundNBT tag = getItemTagCompound(stakc);
    	if (tag.contains("cartridge")){
    		return ItemStack.read(tag.getCompound("cartridge"));
    	}
    	return ItemStack.EMPTY;
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
			if (stack.getItem() == ItemCore.item_automatic) {
				return new ContainerAutomatic(id, playerInv, ((ItemAutomatic)stack.getItem()).getInventory(stack));
			}
			return null;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TranslationTextComponent("container.automatic");
		}
    }
}
