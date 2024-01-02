package com.example.apricotsharepluging

/*
    Desarrollador David shiro Beltran
    Fecha 02/01/2024
    Update ---------
*/

import android.app.Activity
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.FileProvider
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import java.io.File
import java.nio.file.FileSystem

class ApricotSharePluging(godot: Godot?) : GodotPlugin(godot){
    private lateinit var aActivity: Activity
    private lateinit var aGodotLayout : FrameLayout
    private var Tag = "Godot Engine Message"

    override fun onMainCreate(activity: Activity?): View? {
        aActivity = super.getActivity()!!
        aGodotLayout = FrameLayout(aActivity)
        return aGodotLayout
    }

    override fun getPluginName(): String {
        return this::class.simpleName.toString()
    }

    @UsedByGodot
    fun apricotShareText(title:String = "", subject:String = "", text:String = ""): Boolean {
        Log.d(Tag, "Share text godot")
        if(text != "") {
            var shareText:Intent = Intent(Intent.ACTION_SEND)
            shareText.setType("text/plain")
            shareText.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareText.putExtra(Intent.EXTRA_TEXT, text)
            shareText.putExtra(Intent.EXTRA_TITLE, title)
            aActivity.startActivity(Intent.createChooser(shareText, title))
            return true
        }
        return false
    }

    @UsedByGodot
    fun apricotShareImg(pathImg:String = "", title:String = "", subject:String = "", text:String = ""): Boolean{
        Log.d(Tag, "Share Img godot engine")
        if(pathImg != ""){
            var file:File = File(pathImg)
            var uri: Uri
            try {
                uri = FileProvider.getUriForFile(aActivity, aActivity.packageName, file)
            }catch (e:IllegalArgumentException){
                Log.d(Tag, "The selected file can't be shared " + pathImg)
                return false
            }
            var shareText:Intent = Intent(Intent.ACTION_SEND)
            shareText.setType("Image/*")
            shareText.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareText.putExtra(Intent.EXTRA_TEXT, text)
            shareText.putExtra(Intent.EXTRA_TITLE, title)
            shareText.putExtra(Intent.EXTRA_STREAM, pathImg)
            shareText.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            aActivity.startActivity(Intent.createChooser(shareText, title))
        }
        return false
    }
}