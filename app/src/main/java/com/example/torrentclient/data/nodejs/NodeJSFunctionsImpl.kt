import android.content.Context
import android.content.res.AssetManager
import com.example.torrentclient.domain.repository.NodeJSFunctions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class NodeJSFunctionsImpl(private val context: Context): NodeJSFunctions {
    override fun deleteFolderRecursively(file: File): Boolean {
        return try {
            var res = true
            for (childFile in file.listFiles()) {
                res = if (childFile.isDirectory) {
                    res and deleteFolderRecursively(childFile)
                } else {
                    res and childFile.delete()
                }
            }
            res = res and file.delete()
            res
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

     override fun copyAssetFolder(
        assetManager: AssetManager,
        fromAssetPath: String,
        toPath: String
    ): Boolean {
        return try {
            val files = assetManager.list(fromAssetPath)
            var res = true
            if (files!!.size == 0) {
                res = res and copyAsset(
                    assetManager,
                    fromAssetPath,
                    toPath
                )
            } else {
                File(toPath).mkdirs()
                for (file in files) res = res and copyAssetFolder(
                    assetManager,
                    "$fromAssetPath/$file",
                    "$toPath/$file"
                )
            }
            res
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun copyAsset(
        assetManager: AssetManager,
        fromAssetPath: String,
        toPath: String
    ): Boolean {
        var `in`: InputStream? = null
        var out: OutputStream? = null
        return try {
            `in` = assetManager.open(fromAssetPath)
            File(toPath).createNewFile()
            out = FileOutputStream(toPath)
            copyFile(`in`, out)
            `in`.close()
            `in` = null
            out.flush()
            out.close()
            out = null
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

    override fun initNodeServer(): String{
        val nodeDir: String =
            context.applicationContext.filesDir.absolutePath + "/nodejs-project"
        val nodeDirReference = File(nodeDir)
        if (nodeDirReference.exists()) {
            deleteFolderRecursively(File(nodeDir))
        }
        copyAssetFolder(context.applicationContext.assets, "nodejs-project", nodeDir)

        return nodeDir
    }

}
