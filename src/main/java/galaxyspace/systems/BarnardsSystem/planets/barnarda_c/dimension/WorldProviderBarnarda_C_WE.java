package galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension;

import java.util.List;

import javax.annotation.Nullable;

import asmodeuscore.api.dimension.IProviderFreeze;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_CaveGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_RavineGen;
import asmodeuscore.core.astronomy.dimension.world.worldengine.standardcustomgen.WE_TerrainGenerator;
import galaxyspace.core.util.GSDimensions;
import galaxyspace.systems.BarnardsSystem.BarnardsSystemBodies;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.sky.SkyProviderBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.BiomeDecoratorBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.BiomeProviderBarnarda_C;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Beach;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_DeepOcean;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Dunes;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Forest;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Mountains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Ocean;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Plains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_River;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_SnowPlains;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_Swampland;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.world.gen.we.Barnarda_C_YellowPlains;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderBarnarda_C_WE extends WE_WorldProvider implements IProviderFreeze {

	private final float[] colorsSunriseSunset = new float[4];
	public static WE_ChunkProvider chunk;
	
	@Override
    public double getHorizon() {
        return 44.0D;
    }

    @Override
    public float getFallDamageModifier() {
        return 0.16F;
    }

    @Override
    public double getFuelUsageMultiplier() {
        return 0.8;
    }

    @Override
    public double getMeteorFrequency() {
        return 0.0;
    }

    @Override
    public float getSoundVolReductionAmount() {
        return Float.MIN_VALUE;
    }
    
    @Override
    public boolean canRainOrSnow() {
        return true;
    }
        
    @Override
    public CelestialBody getCelestialBody() {
        return BarnardsSystemBodies.Barnarda_C;
    }

    @Override
    public Class<? extends IChunkGenerator> getChunkProviderClass() {
        return WE_ChunkProvider.class;

    }
    
    @Override 
    public Class<? extends BiomeProvider> getBiomeProviderClass() { 
    	return BiomeProviderBarnarda_C.class; 
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
    	return super.calcSunriseSunsetColors(celestialAngle, partialTicks);
      /*  float f = 0.4F;
        float f1 = MathHelper.cos(celestialAngle * ((float)Math.PI * 2F)) - 0.0F;
        float f2 = -0.0F;

        if (f1 >= -0.4F && f1 <= 0.4F)
        {
            float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * (float)Math.PI)) * 0.99F;
            f4 = f4 * f4;
            this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = f4;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }*/
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(86 / 255.0F * f, 180 / 255.0F * f, 240 / 255.0F * f);
    }

    @Override
    public Vector3 getSkyColor() {
    	float f = 0.6F - this.getStarBrightness(1.0F);
        return new Vector3(100 / 255.0F * f, 220 / 255.0F * f, 250 / 255.0F * f);
    }*/
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getFogColor() {
    	float f = 1.0F - this.getStarBrightness(1.0F);
        return new Vector3(140 / 255.0F * f, 167 / 255.0F * f, 207 / 255.0F * f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vector3 getSkyColor() {

    	float f = 0.5F - this.getStarBrightness(1.0F);
    	if(world.isRaining())
    	{
    		f = 1.0F;
    		return new Vector3(47 / 255.0F * f, 47 / 255.0F * f, 47 / 255.0F * f);
    	}
    	return new Vector3(61 / 255.0F * f, 86 / 255.0F * f, 175 / 255.0F * f);

    }
    
    @Override
	public boolean isSkyColored() {
		return true;
	}
 
	@Override
	public boolean hasSunset() {
		return true;
	}

    @Override
    public boolean shouldForceRespawn() {
        return !ConfigManagerCore.forceOverworldRespawn;
    }    
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
    	float f = this.world.getCelestialAngle(par1);
        float f1 = 1.0F - (MathHelper.cos(f * ((float)Math.PI * 2F)) * 2.0F + 0.25F);
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        return f1 * f1 * 0.5F;   	
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
       float f1 = this.world.getCelestialAngle(1.0F);
       float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
       f2 = MathHelper.clamp(f2, 0.0F, 1.0F);

       f2 = 1.2F - f2;
       return f2 * 0.8F;
    }
    
    @Override
    public IRenderHandler getCloudRenderer(){
        return super.getCloudRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer()
    {
    	if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new SkyProviderBarnarda_C());
		}
    	
		return super.getSkyRenderer();
    }
    
    @Override
	public int getDungeonSpacing() {
		return 0;
	}
    
    @Override
	public ResourceLocation getDungeonChestType() {
		return null;
	}

	@Override
	public List<Block> getSurfaceBlocks() {
		return null;
	}

	@Override
	public DimensionType getDimensionType() {
	
		return GSDimensions.BARNARDA_C;
	}

	@Override
	public void genSettings(WE_ChunkProvider cp) {
		
		chunk = cp;
		
		cp.createChunkGen_List .clear(); 
		cp.createChunkGen_InXZ_List .clear(); 
		cp.createChunkGen_InXYZ_List.clear(); 
		cp.decorateChunkGen_List .clear(); 
		
		WE_Biome.setBiomeMap(cp, 1.45D, 4, 4800.0D, 1.0D);	

		WE_TerrainGenerator terrainGenerator = new WE_TerrainGenerator(); 
		terrainGenerator.worldStoneBlock = BRBlocks.BARNARDA_C_BLOCKS; 
		terrainGenerator.worldStoneBlockMeta = 1;
		terrainGenerator.worldSeaGen = true;
		terrainGenerator.worldSeaGenBlock = Blocks.WATER;
		terrainGenerator.worldSeaGenMaxY = 64;
		cp.createChunkGen_List.add(terrainGenerator);
		
		//-// 
		WE_CaveGen cg = new WE_CaveGen(); 
		cg.replaceBlocksList .clear(); 
		cg.replaceBlocksMetaList.clear(); 
		cg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta); 
		cg.lavaMaxY = 15;
		//cg.lavaBlock = CW_Main.bfLava2; 
		cp.createChunkGen_List.add(cg); 
		//-// 
		 
		WE_RavineGen rg = new WE_RavineGen();
		rg.replaceBlocksList    .clear();
		rg.replaceBlocksMetaList.clear();
		rg.addReplacingBlock(terrainGenerator.worldStoneBlock, (byte)terrainGenerator.worldStoneBlockMeta);
		rg.lavaBlock = Blocks.LAVA;
		rg.lavaMaxY = 15;		
		cp.createChunkGen_List.add(rg);
		
		cp.biomesList.clear();
		
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_DeepOcean(-4D, 4D));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-3.9D, 3.9D, false));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Swampland(-3.5D, 3.5D));		
		//DEEPOCEAN -2D, 2D
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(-1.9D, 1.9D, false));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(-1.66D, 1.66D, 1));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Plains(-1.63D, 1.63D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_River(-1.6D, 1.6D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Forest(-1.4D, 1.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_YellowPlains(-1.1D, 1.1D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_River(-1.0D, 1.0D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Dunes(-0.95D, 0.95D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.6D, 0.6D, 100, 2.8D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.4D, 0.4D, 180, 2.4D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_SnowPlains(-0.2D, 0.2D, 160));
		
		/*WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-1.08D, 1.08D, 100, 2.8D, 4));		
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.9D, 0.9D, 180, 2.4D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_SnowPlains(-0.5D, 0.5D, 160));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-0.4D, 0.4D, 80, 2.4D, 4));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_YellowPlains(-0.2D, 0.2D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Dunes(-0.0D, 0.0D));*/
		
		/*WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_SnowPlains(-1.8D, -1.4D, 160));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Mountains(-1.4D, -1.2D, 180, 2.4D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Plains(-1.2D, -0.4D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_River(-0.4D, -0.1D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Forest(-0.1D, 2.0D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(2.0D, 2.2D, 2));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(2.2D, 3.2D, false));
	//	WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_DeepOcean(3.2D, 4.0D));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(4.0D, 4.2D, false));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(4.2D, 4.5D, 3));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Dunes(4.5D, 5.0D));	
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Beach(5.0D, 5.2D, 4));
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(5.2D, 5.5D, false));
		//5.5 - 6.5 - deep ocean
		WE_Biome.addBiomeToGeneration(cp, new Barnarda_C_Ocean(6.5D, 6.8D, true));*/
		

	}
	
	@Override
	public BiomeDecoratorSpace getDecorator() {
		return new BiomeDecoratorBarnarda_C();
	}
}
