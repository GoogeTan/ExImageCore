package me.zahara.eximagecore.serializer

import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer

implicit object IntSerializer extends Serializer[Int] :
  override def fromBuffer(buffer: PacketBuffer): Int = buffer.readInt()

  override def writeToBuffer(buffer: PacketBuffer, value: Int): Unit = buffer.writeInt(value)

  override def fromTag(name: String, tag: CompoundNBT): Option[Int] =
    if tag.contains(name) then
      Some(tag.getInt(name))
    else
      None

  override def writeToTag(name: String, value: Int, tag: CompoundNBT): Unit = tag.putInt(name, value)
