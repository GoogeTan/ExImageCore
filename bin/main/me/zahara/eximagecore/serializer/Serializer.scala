package me.zahara.eximagecore.serializer

import me.zahara.eximagecore.serializer.Serializer
import net.minecraft.nbt.{CompoundNBT, INBT}
import net.minecraft.network.PacketBuffer

trait Serializer[T]:
  /**
   * Читает значение из тега.
   * Если значения типа T там не оказалось, кидает исключение.
   */
  def fromTag(name : String, tag : CompoundNBT) : Option[T]

  /**
   * Читает значение из буффера для пакетов.
   * Если нужного значения там не оказалось, выкидывает исключение.
   */
  def fromBuffer(buffer : PacketBuffer) : T

  /**
   * Пишет в тег значение. Гарантируется, что создаётся подтег с именем name.
   */
  def writeToTag(name : String, value : T, tag : CompoundNBT) : Unit

  /**
   * Пишет в буффер значение.
   */
  def writeToBuffer(buffer : PacketBuffer, value : T) : Unit

object Serializer:
  extension(tag : CompoundNBT)
    /**
     * Пишет значение произвольного типа T, имеющего серелизатор.
     */
    def putValue[T](name : String, value : T)(implicit ser : Serializer[T]): Unit =
        ser.writeToTag(name, value, tag)

    /**
     * Читает значение произвольного типа T, имеющего серелизатор.
     */
    def getValue[T](name : String)(implicit ser : Serializer[T]): Option[T] =
      ser.fromTag(name, tag)

  extension(tag : PacketBuffer)
    /**
     * Пишет значение произвольного типа T, имеющего серелизатор.
     */
    def putValue[T](value : T)(implicit ser : Serializer[T]): Unit =
      ser.writeToBuffer(tag, value)
      
    /**
     * Читает значение произвольного типа T, имеющего серелизатор.
     */
    def getValue[T](implicit ser : Serializer[T]): T =
      ser.fromBuffer(tag)

