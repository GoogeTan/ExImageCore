package me.zahara.eximagecore.serializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer

implicit object FloatSerializer extends Serializer[Float]:
  override def fromBuffer(buffer: PacketBuffer): Float = buffer.readFloat()

  override def fromTag(name: String, tag: CompoundNBT): Option[Float] =
    if tag.contains(name) then
      Some(tag.getFloat(name))
    else
      None  

  override def writeToBuffer(buffer: PacketBuffer, value: Float): Unit = buffer.writeFloat(value)

  override def writeToTag(name: String, value: Float, tag: CompoundNBT): Unit = tag.putFloat(name, value)
