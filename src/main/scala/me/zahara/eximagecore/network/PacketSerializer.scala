package me.zahara.eximagecore.network

import io.netty.buffer.ByteBuf
import net.minecraft.network.PacketBuffer

import scala.reflect.ClassTag

/**
 * Серелизатор для пакета PacketType
 * @tparam PacketType тип пакета
 */
trait PacketSerializer[PacketType <: Packet](implicit val tag : ClassTag[PacketType]):
  /**
   * Записывает пакет в буффер.
   * @param packet Пакет для записи
   * @param buf Буффер для записи
   * @return Возвращает buf
   */
  def encode(packet : PacketType, buf : PacketBuffer) : ByteBuf

  /**
   * Создаёт новый пакет из данных из буффера.
   * @param buf Буффер для чтения
   * @return Прочитанную инстанцию пакета.
   */
  def decode(buf : ByteBuf) : PacketType