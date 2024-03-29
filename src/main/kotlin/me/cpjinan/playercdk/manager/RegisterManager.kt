package me.cpjinan.playercdk.manager

import org.bukkit.Bukkit
import taboolib.common.io.newFile
import taboolib.common.platform.Platform
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.info
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin
import java.net.HttpURLConnection
import java.net.URL

object RegisterManager {

    /**
     * 快捷注册方法
     */
    fun registerAll() {
        registerMetrics()
        registerDatabase()
        registerUpdate()
        registerUrl()
    }

    /**
     * bStats统计注册方法
     */
    private fun registerMetrics() {
        if (ConfigManager.options.getBoolean("metrics")) Metrics(18992, BukkitPlugin.getInstance().description.version, Platform.BUKKIT)
    }

    /**
     * 数据库注册方法
     */
    private fun registerDatabase() {
        if (ConfigManager.options.getString("database.use").equals("LOCAL")) { setupPlayerDatabase(newFile(getDataFolder(), "database.db")); return }
        if (ConfigManager.options.getString("database.use").equals("SQL")) ConfigManager.options.getConfigurationSection("database.source.SQL")?.let { setupPlayerDatabase(it) }
    }

    /**
     * 网页读取注册方法
     */
    private fun registerUrl() {
        Thread{
            val urlConnection = URL("https://cpjinan.github.io/Pages/PlayerCDK/notice.html").openConnection() as HttpURLConnection
            try {
                val message = urlConnection.inputStream.bufferedReader().readText()
                if(message.length > 2) info(message)
            } catch (_: java.net.ConnectException){
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    /**
     * 输出插件更新提示方法
     */
    private fun registerUpdate() {
        Thread{
            val urlConnection = URL("https://cpjinan.github.io/Pages/PlayerCDK/version.html").openConnection() as HttpURLConnection
            try {
                val latestVersion = urlConnection.inputStream.bufferedReader().readText()
                val version = BukkitPlugin.getInstance().description.version
                if(ConfigManager.options.getBoolean("update") && latestVersion != version){
                    info("发现了一个新的PlayerCDK版本！")
                    info("最新版本: $latestVersion")
                    info("当前版本: $version")
                    info("请加QQ群704109949以获取插件最新版本...")
                }
            } catch (_: java.net.ConnectException){
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

}