package mod.fbd.trade;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

public class Trades {
	public static class ItemForItemToTrade implements VillagerTrades.ITrade {
		private final Item buyItem;
		private final Item sellItem;
		private final int buyCount;
		private final int sellCount;
		private final int maxUse;
		private final int giveExp;
		private final float priceMultiplierIn;

		public ItemForItemToTrade(IItemProvider buy,int countBuy,  IItemProvider sell, int countSell, int max, int exp) {
	         this.buyItem = buy.asItem();
	         this.sellItem = sell.asItem();
	         this.buyCount = countBuy;
	         this.sellCount = countSell;
	         this.maxUse = max;
	         this.giveExp = exp;
	         this.priceMultiplierIn = 0.05F;
		}

		public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_) {
			ItemStack buy = new ItemStack(this.buyItem, this.buyCount);
			ItemStack sell = new ItemStack(this.sellItem, this.sellCount);
			return new MerchantOffer(buy, sell, this.maxUse, this.giveExp, this.priceMultiplierIn);
		}
	}
}
