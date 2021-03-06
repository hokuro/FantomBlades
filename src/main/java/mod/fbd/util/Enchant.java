package mod.fbd.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;

public class Enchant {
	private Enchantment enc;
	private int lvl;

	public Enchant(Enchantment enchant, int lv){
		enc = enchant;
		lvl =lv;
	}

	public Enchantment Enchantment(){
		return enc;
	}

	public void Enchantment(Enchantment enc){
		this.enc = enc;
	}

	public int Level(){
		return lvl;
	}
	public void Level(int lv){
		lvl = lv;
	}

	public String toString(){
		return I18n.format(enc.getName()) + "/" + lvl;
	}
}
