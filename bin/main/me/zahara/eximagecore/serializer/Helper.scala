package me.zahara.eximagecore.serializer

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import me.zahara.eximagecore.serializer.*
import me.zahara.eximagecore.serializer.Serializer.*


import java.net.URL

/**
 * Набор вспомогательных функций для серелизации.
 */
object Helper:
  type SerializablePrimitives = BlockPos | Boolean | Direction | Int | String | URL | Float

  /**
   * Создаёт тег из набора серелизуемых типов.
   * @param xs Значения для серелизации
   * @return Тег со всеми серелизованными значениями.
   */
  def compound(xs: (String, SerializablePrimitives)*): CompoundNBT =
    val res = CompoundNBT()
    for (key, value) <- xs do
      value match
        case pos: BlockPos => res.putValue(key, pos)
        case bool: Boolean => res.putValue(key, bool)
        case dir: Direction => res.putValue(key, dir)
        case int: Int => res.putValue(key, int)
        case str: String => res.putValue(key, str)
        case url: URL => res.putValue(key, url)
        case float: Float => res.putValue(key, float)
    res

  type SerializerData[T] = (String,  Serializer[T])

  def uncompound[A](tag : CompoundNBT)(fst : SerializerData[A]) : Option[A] =
    fst._2.fromTag(fst._1, tag)

  def uncompound[A, B](tag : CompoundNBT)(fst : SerializerData[A], scd : SerializerData[B]) : Option[(A, B)] =
    for {
      a <- fst._2.fromTag(fst._1, tag)
      b <- scd._2.fromTag(scd._1, tag)
    } yield (a, b)

  def uncompound[A, B, C](tag : CompoundNBT)
                         (
                           fst : SerializerData[A],
                           scd : SerializerData[B],
                           thd : SerializerData[C]
                         ) : Option[(A, B, C)] =
    for {
      a <- fst._2.fromTag(fst._1, tag)
      b <- scd._2.fromTag(scd._1, tag)
      c <- thd._2.fromTag(thd._1, tag)
    } yield (a, b, c)

  def uncompound[A, B, C, D](tag : CompoundNBT)
                         (
                           fst : SerializerData[A],
                           scd : SerializerData[B],
                           thd : SerializerData[C],
                           fourth : SerializerData[D]
                         ) : Option[(A, B, C, D)] =
    for {
      a <- fst._2.fromTag(fst._1, tag)
      b <- scd._2.fromTag(scd._1, tag)
      c <- thd._2.fromTag(thd._1, tag)
      d <- fourth._2.fromTag(fourth._1, tag)
    } yield (a, b, c, d)

  def uncompound[A, B, C, D, E](tag : CompoundNBT)
                            (
                              fst : SerializerData[A],
                              scd : SerializerData[B],
                              thd : SerializerData[C],
                              fourth : SerializerData[D],
                              fifth : SerializerData[E]
                            ) : Option[(A, B, C, D, E)] =
    for {
      a <- fst._2.fromTag(fst._1, tag)
      b <- scd._2.fromTag(scd._1, tag)
      c <- thd._2.fromTag(thd._1, tag)
      d <- fourth._2.fromTag(fourth._1, tag)
      e <- fifth._2.fromTag(fifth._1, tag)
    } yield (a, b, c, d, e)