package mod.fbd.entity;

import java.util.Random;

import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class ProfessionGunSmith extends VillagerProfession {

    private VillagerCareer[] careers;
	public ProfessionGunSmith(String name, String texture, String zombie) {
		super(name, texture, zombie);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    public void init(VillagerCareer... careers){
        this.careers = careers;
    }

    @Override
    public VillagerCareer getCareer(int id){
        return careers[id];
    }

    @Override
    public int getRandomCareer(Random rand){
        return rand.nextInt(careers.length);
    }
}