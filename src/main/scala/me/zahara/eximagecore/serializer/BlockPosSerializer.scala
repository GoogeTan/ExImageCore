package me.zahara.eximagecore.serializer

import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import me.zahara.eximagecore.serializer.Helper.compound
import me.zahara.eximagecore.serializer.Serializer.*

implicit object BlockPosSerializer extends Serializer[BlockPos] :
  override def fromBuffer(buffer: PacketBuffer): BlockPos = buffer.readBlockPos()

  override def writeToBuffer(buffer: PacketBuffer, value: BlockPos): Unit = buffer.writeBlockPos(value)

  override def fromTag(name: String, tag: CompoundNBT): Option[BlockPos] =
    val inner = tag.getCompound(name)
    for {
      x <- inner.getValue[Int]("x")
      y <- inner.getValue[Int]("y")
      z <- inner.getValue[Int]("z")
    } yield BlockPos(x, y, z)


  override def writeToTag(name: String, value: BlockPos, tag: CompoundNBT): Unit =
    tag.put(name, compound("x" -> value.getX, "y" -> value.getY, "z" -> value.getZ))
