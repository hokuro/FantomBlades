package mod.fbd.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

public class ItemSouleater extends Item{

	protected String translationKey;

	public ItemSouleater(Item.Properties property) {
		super(property);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		CompoundNBT nbt = new CompoundNBT();
		CompoundNBT additional = new CompoundNBT();
		target.writeWithoutTypeId(nbt);
		target.writeAdditional(additional);
		ItemStack soul = new ItemStack(ItemCore.item_mobsoul);
		ItemMobSoul.addReadData(soul, target.getType().getRegistryName().toString(), nbt, additional);
		if (!playerIn.world.isRemote) {
			target.entityDropItem(soul);
			target.remove();
			if (!playerIn.abilities.isCreativeMode) {
				stack.damageItem(1, playerIn, (entity)->{
					entity.sendBreakAnimation(hand);
				});
			}
		}
		return true;
	}
}
