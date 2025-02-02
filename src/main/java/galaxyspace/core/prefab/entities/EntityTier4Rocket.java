package galaxyspace.core.prefab.entities;

import asmodeuscore.core.astronomy.SpaceData.Engine_Type;
import galaxyspace.core.GSBlocks;
import galaxyspace.core.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvancedLandingPadFull;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.event.EventLandingPadRemoval;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityTier4Rocket extends EntityTieredRocketWithEngine
{
    public EntityTier4Rocket(World par1World)
    {
        super(par1World, 4);
        this.setSize(2.0F, 10F);
    }

    public EntityTier4Rocket(World par1World, double par2, double par4, double par6, EnumRocketType rocketType)
    {
        super(par1World, par2, par4, par6, 4, rocketType);
    }
    
    public EntityTier4Rocket(World par1World, double par2, double par4, double par6, EnumRocketType rocketType, Engine_Type engine)
    {
        super(par1World, par2, par4, par6, 4, rocketType);
        this.setEngine(engine);
    }

    @Override
    public double getYOffset()
    {
        return 1.5F;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(GSItems.ROCKET_TIER_4, 1, this.rocketType.getIndex());
    }

    @Override
    public double getMountedYOffset()
    {
        return 3.75D;
    }

    @Override
    public float getRotateOffset()
    {
        return 3.25F;
    }

    @Override
    public double getOnPadYOffset()
    {
        return 0.0D;
    }

    @Override
    public void onLaunch()
    {
    	MinecraftForge.EVENT_BUS.post(new RocketLaunchEvent(this));
    	
    	
    	 if (!this.world.isRemote)
         {
         	GCPlayerStats stats = null;
         	
         	if (!this.getPassengers().isEmpty())
         	{
         	    for (Entity player : this.getPassengers())
         	    {
         	        if (player instanceof EntityPlayerMP)
         	        {
         	            stats = GCPlayerStats.get(player);
                        stats.setLaunchpadStack(null);

         	            if (!(this.world.provider instanceof IOrbitDimension))
         	            {
         	                stats.setCoordsTeleportedFromX(player.posX);
         	                stats.setCoordsTeleportedFromZ(player.posZ);
         	            }
         	        }
         	    }

         	    Entity playerMain = this.getPassengers().get(0);
         	    if (playerMain instanceof EntityPlayerMP)
         	        stats = GCPlayerStats.get(playerMain);
         	}

         	
             int amountRemoved = 0;

             PADSEARCH:
             for (int x = MathHelper.floor(this.posX) - 2; x <= MathHelper.floor(this.posX) + 3; x++)
             {
                 for (int y = MathHelper.floor(this.posY) - 3; y <= MathHelper.floor(this.posY) + 1; y++)
                 {
                     for (int z = MathHelper.floor(this.posZ) - 2; z <= MathHelper.floor(this.posZ) + 3; z++)
                     {
                         BlockPos pos = new BlockPos(x, y, z);
                         final Block block = this.world.getBlockState(pos).getBlock();

                         if (block != null && block instanceof BlockAdvancedLandingPadFull)
                         {
                             if (amountRemoved < 25)
                             {
                                 EventLandingPadRemoval event = new EventLandingPadRemoval(this.world, pos);
                                 MinecraftForge.EVENT_BUS.post(event);

                                 if (event.allow)
                                 {
                                     this.world.setBlockToAir(pos);
                                     amountRemoved = 25;
                                 }
                                 break PADSEARCH;
                             }
                         }
                     }
                 }
             }

             //Set the player's launchpad item for return on landing - or null if launchpads not removed
             if (stats != null && amountRemoved == 25)
             {
                 stats.setLaunchpadStack(new ItemStack(GSBlocks.ADVANCED_LANDING_PAD_SINGLE, 25, 0));
             }

             this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }
    }
    
    @Override
    protected void spawnParticles(boolean launched)
    {
        if (!this.isDead)
        {
            double sinPitch = Math.sin(this.rotationPitch / Constants.RADIANS_TO_DEGREES_D);
            double x1 = 3.2 * Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double z1 = 3.2 * Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D) * sinPitch;
            double y1 = 3.2 * Math.cos((this.rotationPitch - 180) / Constants.RADIANS_TO_DEGREES_D);
            if (this.launchPhase == EnumLaunchPhase.LANDING.ordinal() && this.targetVec != null)
            {
                double modifier = this.posY - this.targetVec.getY();
                modifier = Math.max(modifier, 180.0);
                x1 *= modifier / 200.0D;
                y1 *= Math.min(modifier / 200.0D, 2.5D);
                z1 *= modifier / 200.0D;
            }

            final double y2 = this.prevPosY + (this.posY - this.prevPosY) + y1 - 0.75 * this.motionY - 0.3 + 1.2D;

            final double x2 = this.posX + x1 + this.motionX;
            final double z2 = this.posZ + z1 + this.motionZ;
            Vector3 motionVec = new Vector3(x1 + this.motionX, y1 + this.motionY, z1 + this.motionZ);
            Vector3 d1 = new Vector3(y1 * 0.1D, -x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d2 = new Vector3(x1 * 0.1D, -z1 * 0.1D, y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d3 = new Vector3(-y1 * 0.1D, x1 * 0.1D, z1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 d4 = new Vector3(x1 * 0.1D, z1 * 0.1D, -y1 * 0.1D).rotate(315 - this.rotationYaw, motionVec);
            Vector3 mv1 = motionVec.clone().translate(d1);
            Vector3 mv2 = motionVec.clone().translate(d2);
            Vector3 mv3 = motionVec.clone().translate(d3);
            Vector3 mv4 = motionVec.clone().translate(d4);
            //T3 - Four flameballs which spread
            makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, this.getLaunched());
            //makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, this.getLaunched());
            makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, this.getLaunched());
            //makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, this.getLaunched());
            makeFlame(x2, y2, z2, new Vector3(x1, y1, z1), this.getLaunched());
            /*
            makeFlame(x2 + d1.x, y2 + d1.y, z2 + d1.z, mv1, this.getLaunched());
            makeFlame(x2 + d2.x, y2 + d2.y, z2 + d2.z, mv2, this.getLaunched());
            makeFlame(x2 + d3.x, y2 + d3.y, z2 + d3.z, mv3, this.getLaunched());
            makeFlame(x2 + d4.x, y2 + d4.y, z2 + d4.z, mv4, this.getLaunched());*/
        }
    }

    private void makeFlame(double x2, double y2, double z2, Vector3 motionVec, boolean getLaunched)
    {
        EntityLivingBase riddenByEntity = this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof EntityLivingBase) ? null : (EntityLivingBase) this.getPassengers().get(0);

        if (getLaunched)
        {
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 + 0.4, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2 - 0.4, y2, z2), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 + 0.4D), motionVec, new Object[] { riddenByEntity });
            GalacticraftCore.proxy.spawnParticle("launchFlameLaunched", new Vector3(x2, y2, z2 - 0.4D), motionVec, new Object[] { riddenByEntity });
            return;
        }

        if (this.ticksExisted % 2 == 0) return;

        y2 += 1.6D;
        double x1 = motionVec.x;
        double y1 = motionVec.y;
        double z1 = motionVec.z;
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 + 0.4 - this.rand.nextDouble() / 10), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10, y1 - 0.3D, z1 + 0.1D + this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4 + this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 - 0.1D - this.rand.nextDouble() / 10, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4 - this.rand.nextDouble() / 10, y2, z2 - 0.4 + this.rand.nextDouble() / 10), new Vector3(x1 + 0.1D + this.rand.nextDouble() / 10, y1 - 0.3D, z1 - 0.1D - this.rand.nextDouble() / 10), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 + 0.4, y2, z2), new Vector3(x1 + 0.3D, y1 - 0.3D, z1), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2 - 0.4, y2, z2), new Vector3(x1 - 0.3D, y1 - 0.3D, z1), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 + 0.4D), new Vector3(x1, y1 - 0.3D, z1 + 0.3D), new Object[] { riddenByEntity });
        GalacticraftCore.proxy.spawnParticle("launchFlameIdle", new Vector3(x2, y2, z2 - 0.4D), new Vector3(x1, y1 - 0.3D, z1 - 0.3D), new Object[] { riddenByEntity });
    }

    @Override
    public int getFuelTankCapacity()
    {
        return 2200;
    }
    
    @Override
    public boolean isDockValid(IFuelDock dock)
    {
        return dock instanceof IFuelDock;
    }
   
    @Override
    public float getRenderOffsetY()
    {
        return -0.15F;
    }
}
