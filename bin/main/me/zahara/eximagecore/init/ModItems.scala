package me.zahara.eximagecore.init

import net.minecraftforge.registries.DeferredRegister
import net.minecraft.item.Item
import me.zahara.eximagecore.Main
import net.minecraftforge.eventbus.api.IEventBus

object ModItems:
    val items = DeferredRegister.create(classOf[Item], Main.MOD_ID)

    val masterWand = items.register("master_wand", () => new Item(new Item.Properties().maxStackSize(1)))

    def register(bus : IEventBus) = items.register(bus)