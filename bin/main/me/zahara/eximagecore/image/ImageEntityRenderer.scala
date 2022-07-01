package me.zahara.eximagecore.image

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import me.zahara.eximagecore.image.{ImageEntity, ImageSettings}
import net.minecraft.client.renderer.entity.{EntityRenderer, EntityRendererManager}
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.texture.DownloadingTexture
import net.minecraft.client.renderer.{IRenderTypeBuffer, RenderType, Tessellator}
import net.minecraft.util.Direction.*
import net.minecraft.util.{Direction, ResourceLocation}
import org.lwjgl.opengl.GL11

import scala.collection.mutable

class ImageEntityRenderer(entity : EntityRendererManager) extends EntityRenderer[ImageEntity](entity):

  def applyDirection(direction: Direction): Unit =
    var rotation: Int = 0
    direction match
      case EAST  => rotation = 0
      case NORTH => rotation = 90
      case SOUTH => rotation = 270
      case WEST  => rotation = 180
      case UP =>
        GL11.glRotated(90, 1, 0, 0)
        GL11.glRotated(-90, 0, 0, 1)
      case DOWN =>
        GL11.glRotated(-90, 1, 0, 0)
        GL11.glRotated(-90, 0, 0, 1)
      case _ =>
    GL11.glRotated(rotation, 0, 1, 0)

  override def getEntityTexture(entity: ImageEntity): ResourceLocation = ???

  override def render(
                       entityIn: ImageEntity,
                       entityYaw: Float,
                       partialTicks: Float,
                       matrixStackIn: MatrixStack,
                       bufferIn: IRenderTypeBuffer,
                       packedLightIn: Int
                     ): Unit =
    val texture = entityIn.texture
    if texture != null then
      val x = entityIn.getPosX
      val y = entityIn.getPosY
      val z = entityIn.getPosZ
      val (url, rotation, width, height, alpha, brightness, flippedY, flippedX) = ImageSettings.unapply(entityIn.settings)
      GlStateManager.disableLighting()
      GlStateManager.color4f(brightness, brightness, brightness, alpha)

      texture.bindTexture()

      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

      GlStateManager.pushMatrix()
      GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5)

      applyDirection(rotation)
      if (rotation == Direction.UP || rotation == Direction.DOWN)
        GL11.glRotatef(90, 0, 1, 0)

      GL11.glTexCoord3f(if flippedY then 0 else 1, if flippedX then 0 else 1, 0)
      GL11.glVertex3f(0.5F, -0.5f, -0.5f)
      GL11.glTexCoord3f(if flippedY then 0 else 1, if flippedX then 1 else 0, 0)
      GL11.glVertex3f(0.5f, 0.5f, -0.5f)
      GL11.glTexCoord3f(if flippedY then 1 else 0, if flippedX then 1 else 0, 0)
      GL11.glVertex3f(0.5f, 0.5f, 0.5f)
      GL11.glTexCoord3f(if flippedY then 1 else 0, if flippedX then 0 else 1, 0)
      GL11.glVertex3f(0.5f, -0.5f, 0.5f)
      GL11.glEnd()

      GlStateManager.popMatrix()

      GlStateManager.disableRescaleNormal()
      GlStateManager.disableBlend()
      GlStateManager.enableLighting()
