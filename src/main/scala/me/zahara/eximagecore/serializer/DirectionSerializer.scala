package me.zahara.eximagecore.serializer

import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.Direction

implicit object DirectionSerializer extends Serializer[Direction] :
  override def fromBuffer(buffer: PacketBuffer): Direction = Direction.byIndex(buffer.readInt())

  override def writeToBuffer(buffer: PacketBuffer, value: Direction): Unit = buffer.writeInt(value.getIndex)

  override def fromTag(name: String, tag: CompoundNBT): Option[Direction] = 
    if tag.contains(name) && Direction.values().indices.contains(tag.getInt(name)) then
      Some(Direction.byIndex(tag.getInt(name)))
    else
      None  

  override def writeToTag(name: String, value: Direction, tag: CompoundNBT): Unit = tag.putInt(name, value.getIndex)
