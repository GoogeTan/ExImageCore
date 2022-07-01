package me.zahara.eximagecore

import me.zahara.eximagecore.Main.MOD_ID
import net.minecraftforge.fml.common.Mod
import me.zahara.eximagecore.init.ModItems
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod(MOD_ID)
class Main:
  val bus = FMLJavaModLoadingContext.get.getModEventBus
  ModItems.register(bus)

object Main:
  inline val MOD_ID = "eximagecore"