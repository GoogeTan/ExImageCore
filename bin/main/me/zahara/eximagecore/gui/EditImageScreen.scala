package me.zahara.eximagecore.gui

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import me.zahara.eximagecore.Main
import me.zahara.eximagecore.gui.EditImageScreen.texture
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.renderer.RenderSkybox
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent

class EditImageScreen extends Screen(StringTextComponent("Edit image")):
  override def render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float): Unit =
    super.render(matrixStack, mouseX, mouseY, partialTicks)
    matrixStack.push()
    val textureManager = Minecraft.getInstance().textureManager
    textureManager.bindTexture(texture)
    AbstractGui.blit(matrixStack, 0, 0, 1.0f, 1.0f, 256, 256, 256, 256)
    matrixStack.pop()
    
object EditImageScreen:
  val texture: ResourceLocation = ResourceLocation(Main.MOD_ID,"textures/gui/edit.png")  