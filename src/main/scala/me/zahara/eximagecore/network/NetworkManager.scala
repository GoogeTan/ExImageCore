package me.zahara.eximagecore.network

import com.sun.org.apache.xpath.internal.operations.Minus
import io.netty.buffer.ByteBuf
import me.zahara.eximagecore.Main
import net.minecraftforge.fml.network.{NetworkEvent, NetworkRegistry, PacketDistributor}
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.player.ClientPlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraft.world.DimensionType
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}
import net.minecraftforge.fml.LogicalSide

import java.util.function.Supplier
import scala.reflect.ClassTag
import scala.util.Try

/**
 * Содержит в себе всю логику работы с сетью. 
 */
object NetworkManager:
  private[network] val CHANNEL = NetworkRegistry.newSimpleChannel(ResourceLocation(Main.MOD_ID, "network"), () => "2.0", _ => true, _ => true)
  private var id = 0

  /**
   * Регестрирует пакет.
   * @param serializer объект серелизатор
   * @tparam T тип пакета.
   */
  def registerPacket[T <: Packet](implicit serializer: PacketSerializer[T]) : Unit =
    CHANNEL.registerMessage[T](id, serializer.tag.runtimeClass.asInstanceOf, serializer.encode, serializer.decode,
    {
      (packet : T, contextGetter : Supplier[NetworkEvent.Context]) =>
        val context = contextGetter.get()
        context.getDirection.getReceptionSide match
          case LogicalSide.CLIENT =>
            packet.processClient(Try(Minecraft.getInstance().player).toOption)
          case LogicalSide.SERVER =>
            packet.processServer(Option(context.getSender))
    })
    id += 1