package me.zahara.eximagecore.image

import me.zahara.eximagecore.image.{ImageEntity, ImageSettings}
import me.zahara.eximagecore.serializer.*
import me.zahara.eximagecore.serializer.Serializer.*
import net.minecraft.client.renderer.texture.DownloadingTexture
import net.minecraft.entity.{Entity, EntityType}
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.play.IServerPlayNetHandler
import net.minecraft.network.{IPacket, PacketBuffer}
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.util.INBTSerializable

import java.net.URL
import javax.annotation.Nullable

class ImageEntity(
                   entityType : EntityType[_ <: ImageEntity],
                   level : World,
                   var settings : ImageSettings,
                   @Nullable var texture : DownloadingTexture = null
                 ) extends Entity(entityType, level):
  import ImageEntity.*

  override def readAdditionalSaveData(compound : CompoundNBT): Unit =
    settings = compound.getValue[ImageSettings]("settings").getOrElse(ImageSettings())

  override def addAdditionalSaveData(compound: CompoundNBT): Unit =
    compound.putValue("settings", settings)


  override def  defineSynchedData() = ()

  override def getAddEntityPacket = ???

  def setSettings(settings : ImageSettings): Unit =
    this.settings = settings
    //texture = DownloadingTexture(settings.url) //TODO устанавливать текстурку

object ImageEntity:
  class Packet(pos : BlockPos, settings: ImageSettings) extends IPacket[IServerPlayNetHandler]:
    override def handle(p_148833_1_ : IServerPlayNetHandler): Unit = ???

    override def read(p_148837_1_ : PacketBuffer): Unit = ???

    override def write(p_148840_1_ : PacketBuffer): Unit = ???