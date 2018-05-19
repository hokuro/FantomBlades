package mod.fbd.trade;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class VillagerTrade{

    public static class ListItemForAnotherItem implements EntityVillager.ITradeList
    {
        /** The item that is being bought for emeralds */
        public ItemStack itemToBuy;
        public ItemStack itemToSel;
        /**
         * The price info for the amount of emeralds to sell for, or if negative, the amount of the item to buy for
         * an emerald.
         */
        public EntityVillager.PriceInfo priceInfo;

        public ListItemForAnotherItem(Item getItem, Item tradeItem, EntityVillager.PriceInfo priceInfo)
        {
            this.itemToBuy = new ItemStack(getItem);
            this.itemToSel = new ItemStack(tradeItem);
            this.priceInfo = priceInfo;
        }

        public ListItemForAnotherItem(ItemStack getItem, ItemStack tradeItem, EntityVillager.PriceInfo priceInfo){
            this.itemToBuy = getItem;
            this.itemToSel = tradeItem;
            this.priceInfo = priceInfo;
        }

        public ListItemForAnotherItem(Item getItem, ItemStack tradeItem, PriceInfo priceInfo) {
            this.itemToBuy = new ItemStack(getItem);
            this.itemToSel = tradeItem;
            this.priceInfo = priceInfo;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;

            if (this.priceInfo != null)
            {
                i = this.priceInfo.getPrice(random);
            }

            ItemStack itemstack;
            ItemStack itemstack1;

            if (i < 0)
            {
                itemstack = itemToSel.copy();
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
            }
            else
            {
                itemstack = new ItemStack(itemToSel.getItem(), i, itemToSel.getMetadata());
                itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
            }

            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    public static class ListEnchantedItemForAnotherItem implements EntityVillager.ITradeList
    {
        /** The enchanted item stack to sell */
        public ItemStack enchantedItemStack;
        public ItemStack itemBySell;
        /** The price info determining the amount of emeralds to trade in for the enchanted item */
        public EntityVillager.PriceInfo priceInfo;

        public ListEnchantedItemForAnotherItem(Item p_i45814_1_, Item tradeItem, EntityVillager.PriceInfo p_i45814_2_)
        {
            this.enchantedItemStack = new ItemStack(p_i45814_1_);
            this.itemBySell = new ItemStack(tradeItem);
            this.priceInfo = p_i45814_2_;
        }

        public ListEnchantedItemForAnotherItem(Item p_i45814_1_, ItemStack tradeItem,EntityVillager.PriceInfo p_i45814_2_)
        {
            this.enchantedItemStack = new ItemStack(p_i45814_1_);
            this.itemBySell = tradeItem;
            this.priceInfo = p_i45814_2_;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
        {
            int i = 1;

            if (this.priceInfo != null)
            {
                i = this.priceInfo.getPrice(random);
            }

            ItemStack itemstack = new ItemStack(this.itemBySell.getItem(), i, this.itemBySell.getMetadata());
            ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata()), 5 + random.nextInt(15), false);
            recipeList.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

}
