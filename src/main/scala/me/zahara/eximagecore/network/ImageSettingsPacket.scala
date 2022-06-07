package me.zahara.eximagecore.network
import me.zahara.eximagecore.image.{ImageEntity, ImageSettings}
import me.zahara.eximagecore.network.ImageSettingsPacket.*
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{DimensionType, World}
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}
import org.apache.logging.log4j.{LogManager, Logger}

/**
 * Пакет обновления настркоек какой-либо картинки.
 * Посылается с клиента при редактировании настроект через меню.
 * Посылается с сервера всем игрокам при изменении настроек.
 * @param entityId адресс сущности картинки
 * @param settings новые настройки
 */
class ImageSettingsPacket(entityId : Int, settings : ImageSettings) extends Packet:
  @OnlyIn(Dist.CLIENT)
  override def processClient(player: Option[ClientPlayerEntity]): Unit =
    player.toRight("Can not process settings packet. Player doesn't exist.")
      .flatMap(extractWorld)
      .flatMap(getImageEntityById)
      .fold(logger.error(_ : String), _.setSettings(settings))

  @OnlyIn(Dist.DEDICATED_SERVER)
  override def processServer(player: Option[ServerPlayerEntity]): Unit =
    player.toRight("Can not process settings packet. Player doesn't exist.")
      .flatMap(checkPlayerCanEdit)
      .flatMap(extractWorld)
      .flatMap(getImageEntityById)
      .fold(logger.error(_ : String), _.setSettings(settings))

  /**
   * Ищет картинку в мире
   * @param world мир, в котором происходит поиск
   * @return Сущность картинки или строку с ошибкой
   */
  def getImageEntityById(world : World) : Either[String, ImageEntity] =
    val entity = world.getEntityByID(entityId)
    if (entity.isInstanceOf[ImageEntity])
      Right(entity.asInstanceOf)
    else
      Left(s"Can not process settings packet. World hasn't entity with id $entityId")

  /**
   *  Дополнительная проверка, что игрок может редактировать картинку.
   * @param player Игрок
   * @return Сущность картинки или строку с ошибкой
   */
  def checkPlayerCanEdit(player : ServerPlayerEntity) : Either[String, ServerPlayerEntity] =
    if !player.getHeldItemMainhand.isEmpty then //TODO использовать спец предмет
      Right(player)
    else
      Left("Can not process settings packet. Player can not edit images without item.")

  /**
   * Извлекает мир из игрока, делая проверку, что мир существует.
   * @param player Игрок
   * @return Сущность картинки или строку с ошибкой
   */
  def extractWorld(player : PlayerEntity) : Either[String, World] =
    if player.world != null then
      Right(player.world)
    else
      Left("Can not process settings packet. World is null")

object ImageSettingsPacket:
  val logger: Logger = LogManager.getLogger