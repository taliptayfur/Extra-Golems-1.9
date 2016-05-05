package com.golems.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class GolemMultiTextured extends GolemBase
{
	protected static final DataParameter<Byte> DATA_TEXTURE = EntityDataManager.<Byte>createKey(GolemMultiTextured.class, DataSerializers.BYTE);
	protected static final String NBT_TEXTURE = "GolemTextureData";
	
	/** {@code textures} cannot exceed 256 in length **/
	private final String[] textures;
	private final String texturePrefix;
	
	/**
	 * This is a base class for golems that change texture when player interacts.
	 * @param prefix example: "wooden" will initialize textures 
	 * {@code 'golem_wooden_textureNames[i]'} for every value in {@code textureNames}
	 * @param textureNames example:  {"red", "blue"} will initialize textures named
	 * {@code 'golem_x_red'} and {@code 'golem_x_blue'} if {@code prefix} is "x"
	 **/
	public GolemMultiTextured(World world, float attack, ItemStack pick, String prefix, String[] textureNames)
	{
		super(world, attack, pick);
		this.textures = textureNames;
		this.texturePrefix = prefix;
	}
	
	/**
	 * This is a base class for golems that change texture when player interacts.
	 * @param prefix example: "wooden" will initialize textures 
	 * {@code 'golem_wooden_textureNames[i]'} for every value in {@code textureNames}
	 * @param textureNames example:  {"red", "blue"} will initialize textures named
	 * {@code 'golem_x_red'} and {@code 'golem_x_blue'} if {@code prefix} is "x"
	 **/
	public GolemMultiTextured(World world, float attack, Block pick, String prefix, String[] textureNames) 
	{
		this(world, attack, new ItemStack(pick), prefix, textureNames);		
	}
	
	@Override
	protected ResourceLocation applyTexture()
	{
		// apply TEMPORARY texture to avoid NPE. Actual texture is first applied in onLivingUpdate
		return this.makeGolemTexture("clay");
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(DATA_TEXTURE, Byte.valueOf((byte)0));
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		// only change texture when player has empty hand
		if(stack != null)
		{
			return super.processInteract(player, hand, stack);
		}
		else
		{
			int incremented = (this.getTextureNum() + 1) % this.textures.length;
			this.setTextureNum((byte)incremented);
			this.setTextureType(this.getSpecialGolemTexture(getTextureStringFromArray()));
			this.writeEntityToNBT(this.getEntityData());
			player.swingArm(hand);
			return true;
		}
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		// since textureNum is correct, update texture AFTER loading from NBT and init
		if(this.ticksExisted == 2)
		{
			this.setTextureType(this.getSpecialGolemTexture(getTextureStringFromArray()));
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
		nbt.setByte(NBT_TEXTURE, (byte)this.getTextureNum());
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setTextureNum(nbt.getByte(NBT_TEXTURE));
		this.setTextureType(this.getSpecialGolemTexture(getTextureStringFromArray()));
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
	{
		this.setTextureNum((byte)(this.textures.length > 0 ? this.rand.nextInt(this.textures.length) : 0));
		this.setTextureType(this.getSpecialGolemTexture(getTextureStringFromArray()));
		return super.onInitialSpawn(difficulty, data);
	}
	
	@Override
	public boolean doesInteractChangeTexture()
	{
		return true;
	}
	
	public void setTextureNum(byte toSet)
	{
		this.getDataManager().set(DATA_TEXTURE, new Byte(toSet));
	}
	
	public byte getTextureByte()
	{
		return this.getDataManager().get(DATA_TEXTURE);
	}

	public int getTextureNum() 
	{
		return this.getDataManager().get(DATA_TEXTURE).byteValue();
	}
	
	/** Call getGolemTexture with specialized name concatenation **/
	public ResourceLocation getSpecialGolemTexture()
	{
		return getSpecialGolemTexture(this.getTextureStringFromArray());
	}
	
	/** Call getGolemTexture with specialized name concatenation **/
	public ResourceLocation getSpecialGolemTexture(String s)
	{
		return GolemBase.makeGolemTexture(getModId(), this.getTexturePrefix() + "_" + s);
	}
	
	public String getTextureStringFromArray()
	{
		return this.textures[this.getTextureNum()];
	}
	
	public String getTexturePrefix()
	{
		return this.texturePrefix;
	}
	
	public String[] getTextureStringArray()
	{
		return this.textures;
	}
	
	public abstract String getModId();
}

