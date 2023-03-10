package me.cpjinan.playercdk.commands

import me.cpjinan.playercdk.manager.ConfigManager
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.chat.colored

/**
 * playercdk kit create <kit>
 * playercdk kit delete <kit>
 */
@CommandHeader(name = "playercdkkit", permission = "playercdk.admin", permissionDefault = PermissionDefault.FALSE)
object KitCommand {

    @CommandBody(permission = "playercdk.admin", permissionDefault = PermissionDefault.FALSE)
    val main = mainCommand {
        createHelper()
    }

    @CommandBody(permission = "playercdk.admin", permissionDefault = PermissionDefault.FALSE)
    val create = subCommand {
        dynamic("kit") {
            execute<ProxyCommandSender> { sender, context, _ ->
                ConfigManager.kit["kit." + context.get(2) + ".enable"] = true
                ConfigManager.kit["kit." + context.get(2) + ".action"] = listOf("tell §e§l礼包兑换成功!")

                ConfigManager.kit.saveToFile(ConfigManager.kit.file)

                sender.sendMessage(("&a礼包 &f" + context.get(2) + " &a已创建!").colored())
            }
        }
    }

    @CommandBody
    val delete = subCommand {
        dynamic("kit") {
            execute<ProxyCommandSender> { sender, context, _ ->
                ConfigManager.kit["kit." + context.get(2)] = null

                ConfigManager.kit.saveToFile(ConfigManager.kit.file)

                sender.sendMessage(("&c礼包 &f" + context.get(2) + " &c已删除!").colored())
            }
        }
    }

}