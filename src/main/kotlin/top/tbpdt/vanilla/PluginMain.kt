package top.tbpdt.vanilla

import kotlinx.coroutines.cancel
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.event.registerTo
import net.mamoe.mirai.utils.info
import top.tbpdt.configer.AutoConfig
import top.tbpdt.configer.EmojiConfig
import top.tbpdt.configer.GlobalConfig
import top.tbpdt.configer.MuteMeConfig
import top.tbpdt.utils.AccountUtils
import top.tbpdt.utils.CaveUtils
import top.tbpdt.utils.DBUtils
import top.tbpdt.utils.LogStrImage
import top.tbpdt.vanilla.configer.AutoPicsConfig
import top.tbpdt.vanilla.configer.CaveConfig
import top.tbpdt.vanilla.configer.CensorConfig
import top.tbpdt.vanilla.utils.CensorUtils

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "top.tbpdt.vanilla",
        name = "YZLbot-Vanilla",
        version = "2.0.7",
    ) {

        author("Takeoff0518")
    }
) {
    override fun onEnable() {
        logger.info { LogStrImage.STRIMG_YZLBOT }
        logger.info { "GitHub 地址：https://github.com/YZLbot/yzl-vanilla" }
        logger.info { "正在加载配置..." }
        AutoConfig.reload()
        EmojiConfig.reload()
        GlobalConfig.reload()
        CaveConfig.reload()
        MuteMeConfig.reload()
        CensorConfig.reload()
        AutoPicsConfig.reload()
        logger.info { "正在注册监听器到全局..." }
        EmojiFetch.registerTo(globalEventChannel())
        AdminHandler.registerTo(globalEventChannel())
        Cave.registerTo(globalEventChannel())
        Account.registerTo(globalEventChannel())
        MuteMe.registerTo(globalEventChannel())
        InviteProcessor.registerTo(globalEventChannel())
        AutoGroup.registerTo(globalEventChannel())
        ContentCensor.registerTo(globalEventChannel())
        AutoPics.registerTo(globalEventChannel())
        logger.info { "正在加载数据库" }
        DBUtils.initCaveDB()
        AccountUtils.createTable()
        CaveUtils.createTable()
        CensorUtils.initCensorDict()
        AutoPics.initPaths()
    }

    override fun onDisable() {
        logger.info { "正在注销监听器..." }
        EmojiFetch.cancel()
        AdminHandler.cancel()
        Cave.cancel()
        Account.cancel()
        CaveConfig.save()
        MuteMe.cancel()
        InviteProcessor.cancel()
        AutoGroup.cancel()
        ContentCensor.cancel()
        AutoPics.cancel()
        logger.info { "禁用成功！" }
    }

}