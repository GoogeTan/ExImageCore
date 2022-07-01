package me.zahara.eximagecore.serializer

import me.zahara.eximagecore.serializer.Serializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer

import java.net.URL
import scala.util.Try

implicit object URLSerializer extends Serializer[URL] :
  override def fromBuffer(buffer: PacketBuffer): URL = URL(buffer.readString())

  override def writeToBuffer(buffer: PacketBuffer, value: URL): Unit = buffer.writeString(value.toString)

  override def fromTag(name: String, tag: CompoundNBT): Option[URL] =
    if tag.contains(name) then
      Try(URL(tag.getString(name))).toOption
    else
      None

  override def writeToTag(name: String, value: URL, tag: CompoundNBT): Unit = tag.putString(name, value.toString)
