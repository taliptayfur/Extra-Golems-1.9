package com.golems.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPowerProvider extends BlockLightProvider 
{	
	public static final IProperty POWER = PropertyInteger.create("power", 0, 15);
	
	public BlockPowerProvider()
    {
        super(0);
        setDefaultState(blockState.getBaseState().withProperty(POWER, 0));  
    }
   
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(POWER, meta % 16);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(POWER)).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { POWER });
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return this.getMetaFromState(blockState);
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return this.getMetaFromState(blockState);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMovingPowerSource();
    }
}