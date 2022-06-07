package me.zahara.eximagecore.network

import me.zahara.eximagecore.network.NetworkManager.CHANNEL
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.RegistryKey
import net.minecraft.world.{DimensionType, World}
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}
import net.minecraftforge.fml.network.PacketDistributor

/**
 * Интерфейс любого сетевого пакета.
 */
trait Packet:
  /**
   * Вызывается на клиенте для обработки пакета.
   * @param player Сущность игрока
   */
  @OnlyIn(Dist.CLIENT)
  def processClient(player : Option[ClientPlayerEntity]) : Unit = ()

  /**
   * Вызывается на сервере для обработки пакета.
   * @param player Сущность игрока, который послал пакет.
   */
  @OnlyIn(Dist.DEDICATED_SERVER)
  def processServer(player : Option[ServerPlayerEntity]) : Unit = ()

  /**
   * Посылает пакет с сервера всем игрокам.
   */
  @OnlyIn(Dist.DEDICATED_SERVER)
  def sendToAllPlayers(): Unit = CHANNEL.send(PacketDistributor.ALL.noArg(), this)

  /**
   * Посылает пакет с клиента на сервер.
   */
  @OnlyIn(Dist.CLIENT)
  def sendToServer(): Unit = CHANNEL.send(PacketDistributor.SERVER.noArg(), this)

  /**
   * Посылает пакет с сервера конкретному игроку.
   * @param player Сущность игрока, которому будет послан пакет.
   */
  @OnlyIn(Dist.DEDICATED_SERVER)
  def sendToPlayer(player : ServerPlayerEntity): Unit = CHANNEL.send(PacketDistributor.PLAYER.`with`(() => { player }), this)

  /**
   * Посылает пакет с сервера всем игрокам из измерения.
   * @param world Мир, игроки в котором получат пакет.
   */
  @OnlyIn(Dist.DEDICATED_SERVER)
  def sendToDimension(world : RegistryKey[World]): Unit = CHANNEL.send(PacketDistributor.DIMENSION.`with`(() => { world }), this)
