package matterlink.command

import matterlink.*
import matterlink.MatterLink.logger
import matterlink.bridge.MessageHandler
import com.google.common.collect.Lists
import matterlink.bridge.ServerChatHandler
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import org.apache.logging.log4j.core.jmx.Server


class CommandMatterlink : CommandBase() {
    private val aliases: List<String>

    init {
        aliases = Lists.newArrayList(MODID, "bridge", "BRIDGE")
    }

    override fun getName(): String {
        return "bridge"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "bridge <connect|disconnect>"
    }

    override fun getAliases(): List<String> {
        return aliases
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) /*throws CommandException*/ {
        if (args.isEmpty()) {
            //throw new WrongUsageException("")
            return
        }
        //TODO: check if sender is OP or test if normal users cannot send this


        val cmd = args[0].toLowerCase()
        when (cmd) {
            "connect" -> if (MessageHandler.start()) {
                MessageHandler.rcvQueue.clear()
                logger.info("Connected to matterbridge relay")
                ServerChatHandler.processMessages = true
            } else {
                logger.error("Connection to matterbridge relay failed.")
            }
            "disconnect" -> {
                MessageHandler.stop()
                ServerChatHandler.processMessages = false
            }
        }
    }


}
