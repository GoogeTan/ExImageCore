package me.zahara.eximagecore.image

import net.minecraft.util.Direction

import java.net.URL

case class ImageSettings(
                     url : URL = URL(""),
                     rotation : Direction = Direction.EAST,
                     width : Int = 1,
                     height : Int = 1,
                     alpha : Int = 255,
                     brightness : Float = 1.0,
                     flippedY : Boolean = false,
                     flippedX : Boolean = false
                    )

object ImageSettings:
  def unapply(settings: ImageSettings) : (URL, Direction, Int, Int, Int, Float, Boolean, Boolean) =
    (settings.url, settings.rotation, settings.width, settings.height, settings.alpha, settings.brightness, settings.flippedY, settings.flippedX)