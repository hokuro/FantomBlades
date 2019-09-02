package mod.fbd.world.village;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.feature.structure.VillagePieces.PieceWeight;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class VillageCrationHandler implements IVillageCreationHandler {

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new VillagePieces.PieceWeight(getComponentClass(), 8, MathHelper.nextInt(random, 0 + i, 3 + i * 2));
	}

	@Override
	public Class<? extends VillagePieces.Village> getComponentClass() {
		return ComponentVillageGunSmithHouse.class;
	}

	@Override
	public VillagePieces.Village buildComponent(VillagePieces.PieceWeight villagePiece, VillagePieces.Start startPiece, List<StructurePiece> pieces, Random random, int x,
            int y, int z, EnumFacing facing, int p5){
		return ComponentVillageGunSmithHouse.createPiece(startPiece, pieces, random, x, y, z, facing, p5);
	}

}
