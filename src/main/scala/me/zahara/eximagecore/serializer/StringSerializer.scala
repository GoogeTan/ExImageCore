package me.zahara.eximagecore.serializer

import me.zahara.eximagecore.serializer.Serializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer

implicit object StringSerializer extends Serializer[String] :
  override def fromBuffer(buffer: PacketBuffer): String = buffer.readString()

  override def writeToBuffer(buffer: PacketBuffer, value: String): Unit = buffer.writeString(value)

  override def fromTag(name: String, tag: CompoundNBT): Option[String] =
    if tag.contains(name) then
      Some(tag.getString(name))
    else
      None  

  override def writeToTag(name: String, value: String, tag: CompoundNBT): Unit = tag.putString(name, value)
