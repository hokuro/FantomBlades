package mod.fbd.world.village;

import java.util.List;
import java.util.Random;

import mod.fbd.core.ModRegister;
import mod.fbd.util.ModUtil;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class ComponentVillageGunSmithHouse extends Village {
	public ComponentVillageGunSmithHouse()
    {
    }

    public ComponentVillageGunSmithHouse(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing)
    {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = p_i45561_4_;
    }

    public static ComponentVillageGunSmithHouse createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int x, int y, int z, EnumFacing facing, int tp)
    {
    	StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 9, 7, 12, facing);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null ? new ComponentVillageGunSmithHouse(start, tp, rand, structureboundingbox, facing) : null;
    }


    @Override
    public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand){
    	super.buildComponent(componentIn, listIn, rand);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        if (this.averageGroundLvl < 0)
        {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if (this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.STONEBRICK.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
        IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
        IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.NETHER_BRICK.getDefaultState());
        IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.IRON_BARS.getDefaultState());
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
        this.setBlockState(worldIn, iblockstate5, 0, 4, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 0, 4, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 4, 4, structureBoundingBoxIn);
        IBlockState iblockstate7 = iblockstate1;
        IBlockState iblockstate8 = iblockstate2;
        IBlockState iblockstate9 = iblockstate4;
        IBlockState iblockstate10 = iblockstate3;

        for (int i = -1; i <= 2; ++i)
        {
            for (int j = 0; j <= 8; ++j)
            {
                this.setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);

                if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6))
                {
                    this.setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
                }
            }
        }

        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);

        for (int k = 4; k >= 1; --k)
        {
            this.setBlockState(worldIn, iblockstate5, k, 2 + k, 7 - k, structureBoundingBoxIn);

            for (int k1 = 8 - k; k1 <= 10; ++k1)
            {
                this.setBlockState(worldIn, iblockstate10, k, 2 + k, k1, structureBoundingBoxIn);
            }
        }

        this.setBlockState(worldIn, iblockstate5, 6, 6, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 7, 5, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate4, 6, 6, 4, structureBoundingBoxIn);

        for (int l = 6; l <= 8; ++l)
        {
            for (int l1 = 5; l1 <= 10; ++l1)
            {
                this.setBlockState(worldIn, iblockstate9, l, 12 - l, l1, structureBoundingBoxIn);
            }
        }

        EnumDyeColor glassColor = EnumDyeColor.byMetadata(ModUtil.random(16));
        this.setBlockState(worldIn, iblockstate6, 0, 2, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 0, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 4, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 6, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 8, 2, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 8, 2, 9, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 2, 2, 6, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 2, 2, 9, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 4, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate6, 6, 4, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate5, 5, 5, 10, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
        this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
        this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
        {
            this.setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);

            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
            }
        }

        for (int i1 = 0; i1 < 5; ++i1)
        {
            for (int i2 = 0; i2 < 9; ++i2)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, i2, 7, i1, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i2, -1, i1, structureBoundingBoxIn);
            }
        }

        for (int j1 = 5; j1 < 11; ++j1)
        {
            for (int j2 = 2; j2 < 9; ++j2)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, j2, 7, j1, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j2, -1, j1, structureBoundingBoxIn);
            }
        }

        this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 1);
        return true;
    }

    @Override
    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return VillagerRegistry.getId(ModRegister.gunSmith);
    }
}
