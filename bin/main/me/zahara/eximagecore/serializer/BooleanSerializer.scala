package me.zahara.eximagecore.serializer

import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer

implicit object BooleanSerializer extends Serializer[Boolean] :
  override def fromBuffer(buffer: PacketBuffer): Boolean = buffer.readBoolean()

  override def writeToBuffer(buffer: PacketBuffer, value: Boolean): Unit = buffer.writeBoolean(value)

  override def fromTag(name: String, tag: CompoundNBT): Option[Boolean] = 
    if tag.contains(name) then
      Some(tag.getBoolean(name))
    else
      None  

  override def writeToTag(name: String, value: Boolean, tag: CompoundNBT): Unit = tag.putBoolean(name, value)
