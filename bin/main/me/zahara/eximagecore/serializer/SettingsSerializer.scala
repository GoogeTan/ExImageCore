package me.zahara.eximagecore.serializer

import Helper.compound
import me.zahara.eximagecore.image.ImageSettings
import me.zahara.eximagecore.serializer.Serializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import me.zahara.eximagecore.serializer.*
import me.zahara.eximagecore.serializer.Serializer.*

import net.minecraft.util.Direction
import java.net.URL

implicit object SettingsSerializer extends Serializer[ImageSettings]:
  override def fromBuffer(buffer: PacketBuffer): ImageSettings =
    ImageSettings(
      buffer.getValue,
      buffer.getValue,
      buffer.getValue,
      buffer.getValue,
      buffer.getValue,
      buffer.getValue,
      buffer.getValue,
      buffer.getValue
    )

  override def writeToBuffer(buffer: PacketBuffer, value: ImageSettings): Unit =
    buffer.putValue(value.url)
    buffer.putValue(value.rotation)
    buffer.putValue(value.width)
    buffer.putValue(value.height)
    buffer.putValue(value.alpha)
    buffer.putValue(value.brightness)
    buffer.putValue(value.flippedY)
    buffer.putValue(value.flippedX)

  override def fromTag(name: String, tag: CompoundNBT): Option[ImageSettings] =
    val innerTag = tag.getCompound(name)
    for {
      url        <- innerTag.getValue[URL]("url")
      rotation   <- innerTag.getValue[Direction]("rotation")
      width      <- innerTag.getValue[Int]("width")
      height     <- innerTag.getValue[Int]("height")
      alpha      <- innerTag.getValue[Int]("alpha")
      brightness <- innerTag.getValue[Float]("brightness")
      flippedY   <- innerTag.getValue[Boolean]("flippedY")
      flippedX   <- innerTag.getValue[Boolean]("flippedX")
    } yield ImageSettings(url, rotation, width, height, alpha, brightness, flippedY, flippedX)

  override def writeToTag(name: String, value: ImageSettings, tag: CompoundNBT): Unit =
    tag.put(name,
      compound(
        "url" -> value.url,
        "rotation" -> value.rotation,
        "width" -> value.width,
        "height" -> value.height,
        "alpha" -> value.alpha,
        "brightness" -> value.brightness,
        "flippedY" -> value.flippedY,
        "flippedX" -> value.flippedX
      )
    )
