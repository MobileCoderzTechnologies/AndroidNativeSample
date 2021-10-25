package com.androidnativesample.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.android.material.snackbar.Snackbar
import com.androidnativesample.R
import java.io.*

import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

object Utility {

    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            cursor.close()
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                )
            } else {
                return null
            }
        }
    }

    fun checkGif(path: String): Boolean {
        return path.matches(GIF_PATTERN.toRegex())
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        class Converter : AsyncTask<Void, Void, Bitmap>() {
            lateinit var myBitmap:Bitmap
            override fun doInBackground(vararg params: Void?): Bitmap {
                try {
                    val url = URL(src)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    myBitmap = BitmapFactory.decodeStream(input)
                    return myBitmap
                } catch (e: IOException) {
                }
                return myBitmap
            }
        }
        Converter().execute()
        return null
    }

    fun printHashKeyUsingSignature(packageManager: PackageManager , packageName: String) {
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("FacebookHashKey:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {
        }
    }

    fun saveTempBitmap(context: Context, mBitmap: Bitmap): String? {

        val outputDir = context.cacheDir

        var file: File? = null
        try {
            file = File.createTempFile("temp_post_img", ".jpg", outputDir)
            //outputFile.getAbsolutePath();
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val f3 = File(Environment.getExternalStorageDirectory().toString() + "/inpaint/")
        if (!f3.exists()) {
            f3.mkdirs()
        }
        var outStream: OutputStream? = null
        //File file = new File(Environment.getExternalStorageDirectory() + "/inpaint/"+"seconds"+".png");
        try {
            outStream = FileOutputStream(file!!)
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()

            //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        //getPath( Uri.parse(file.getAbsolutePath()), context);

        return file.absolutePath//getPath( Uri.parse(file.getAbsolutePath()), context);
    }
    fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(selectedImage)
        var ei: ExifInterface? = null
        ei = if (Build.VERSION.SDK_INT > 23)
            input?.let { ExifInterface(it) }
        else
            selectedImage.path?.let { ExifInterface(it) }

        return when (ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                TransformationUtils.rotateImage(img, 90)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                TransformationUtils.rotateImage(img, 180)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                TransformationUtils.rotateImage(img, 270)
            }
            else -> {
                img
            }
        }
    }

    @Throws(IOException::class)
    fun rotateRequiredImage(context: Context, img: Bitmap, selectedImage: Uri): Bitmap {

        val input = context.contentResolver.openInputStream(selectedImage)
        val ei: ExifInterface
        if (Build.VERSION.SDK_INT > 23)
            ei = ExifInterface(input!!)
        else
            ei = ExifInterface(selectedImage.path!!)

        val orientation =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(img, 270)
            else -> return img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    }


    fun scaleDown(
        realImage: Bitmap, maxImageSize: Float,
        filter: Boolean
    ): Bitmap {
        val ratio = Math.min(
            maxImageSize / realImage.width,
            maxImageSize / realImage.height
        )
        val width = Math.round(ratio * realImage.width)
        val height = Math.round(ratio * realImage.height)

        return Bitmap.createScaledBitmap(
            realImage, width,
            height, filter
        )
    }

    private const val GIF_PATTERN = "(.+?)\\.gif$"
    fun getRealPathFromURI(context: Context, contentURI: Uri): String? {
        val result: String?
        val cursor = context.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }



    fun changeStatusBar(context: AppCompatActivity) {
        val window = context.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#4D4D4D")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeStatusBarColorWithBlackTexts(context:AppCompatActivity) {
        val window = context.window
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.statusBarColor = ContextCompat.getColor(context, R.color.white)
        }
    }

    fun savePreferencesString(context: Context, key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun savePreferencesArrayList(context: Context, key: String, value: ArrayList<String>) {
        val set = HashSet<String>()
        set.addAll(value)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, set)
        editor.apply()
    }

    fun getPreferencesArrayList(context:Context, key:String): ArrayList<String>? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val set =  sharedPreferences.getStringSet(key,null)
        val list = ArrayList<String>()
            if(set!=null && set.size>0){
                list.addAll(set)
            }
        return list
    }

    fun savePreferencesInt(context: Context, key: String, k: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putInt(key, k)
        editor.apply()
    }

    fun saveLanguageInPreference(context: Context, key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getLanguageInPreference(context: Context, key: String): String? {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context) as SharedPreferences
        return sharedPreferences.getString(key, "")
    }

    fun getPreferenceString(context: Context, key: String): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, "").toString()
    }

    fun getPreferenceInt(context: Context, key: String): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(key, 0)
    }

    fun clearAllPreferences(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun showSnackbar(view: View, message: String) {
        var view = view
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        view = snackbar.view
        /*    val params = view.layoutParams as LinearLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        view.layoutParams = params*/
        view.setBackgroundColor(Color.parseColor("#C0C0C0"))
        snackbar.show()
    }


    fun roundOff(value: Double, places: Int): Double {
        //  require(places >= 0)
        var bd = BigDecimal.valueOf(value)
        bd = bd.setScale(1, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboardOnOutSideTouch(view: View, activity: Activity) {
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideKeyboard(activity)
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyboardOnOutSideTouch(innerView, activity)
            }
        }
    }

    fun convertNumberToGerman(num: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.GERMAN)
        return nf.format(num)
    }

    fun convertDateEnglish(pre: String, dateString: String, post: String): String {
        val parseFormat = SimpleDateFormat(pre, Locale.ENGLISH)
        parseFormat.timeZone = TimeZone.getTimeZone("UTC") as TimeZone
        var date = Date()
        try {
            date = parseFormat.parse(dateString)
            parseFormat.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat(post, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        // Log.e("dateTimeStamp",date)
        return format.format(date)
    }

    fun checkPhonePermission(context: Context): Boolean {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun changeStatusBarColor(context: AppCompatActivity, color: Int) {
        val window = context.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(context, color)
        }
    }

    fun getDateInEnglish(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
        val dateString: String = formatter.format(Date(timestamp))
        return dateString
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDate(pre: String, dateString: String, post: String): String {
        val parseFormat = SimpleDateFormat(pre)
        parseFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = parseFormat.parse(dateString)
            parseFormat.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val format = SimpleDateFormat(post)
        format.timeZone = TimeZone.getDefault()
        // Log.e("dateTimeStamp",date)

        return format.format(date)

    }
    @SuppressLint("SimpleDateFormat")
    fun convertUtcDate(pre: String, dateString: String, post: String): String {
        val parseFormat = SimpleDateFormat(pre)
        parseFormat.timeZone = TimeZone.getDefault()
        var date: Date? = null
        try {
            date = parseFormat.parse(dateString)
            parseFormat.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val format = SimpleDateFormat(post)
        format.timeZone = TimeZone.getTimeZone("UTC")

        // Log.e("dateTimeStamp",date)

        return format.format(date)

    }


    @SuppressLint("SimpleDateFormat")
    fun convertTimeAmerica(pre: String, dateString: String, post: String): String {
        val parseFormat = SimpleDateFormat(pre)
        parseFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = parseFormat.parse(dateString)
            parseFormat.timeZone = TimeZone.getDefault()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val format = SimpleDateFormat(post)
        format.timeZone = TimeZone.getDefault()
        // Log.e("dateTimeStamp",date)

        return format.format(date)
    }

    fun getDateWithTime(timestamp: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.ENGLISH)
        val dateString: String = formatter.format(Date(timestamp))
        return dateString
    }

    fun getDateTime(timestamp: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yy, hh:mm a", Locale.ENGLISH)
        val dateString: String = formatter.format(Date(timestamp))
        return dateString
    }


    class Run {
        companion object {
            fun after(delay: Long, process: () -> Unit) {
                Handler().postDelayed({
                    process()
                }, delay)
            }
        }
    }

    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }



    fun savePreferencesBoolean(context: Context, key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getPreferenceBoolean(context: Context, key: String): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, false)
    }



    fun  hasImageExtension(source:String):Boolean{
        return (source.contains(".png") || source.contains(".jpg")
                ||source.contains(".jpeg")||source.contains(".gif")||source.contains(".bmp"));
    }

    class SafeClickListener(
        private var defaultInterval: Int = 1000,
        private val onSafeCLick: (View) -> Unit
    ) : View.OnClickListener {
        private var lastTimeClicked: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
                return
            }
            lastTimeClicked = SystemClock.elapsedRealtime()
            onSafeCLick(v)
        }
    }



    fun getCorrectedDayOrMonth(value: Int):String{
        return if(value<10){
            "0$value"
        }else{
            value.toString()
        }
    }

//    fun saveAuthData(context:Context, data:SignUpData?) {
//        savePreferencesString(context, AppConstants.NAME, data?.name ?: "")
//        savePreferencesString(context, AppConstants.LAST_NAME, data?.last_name ?: "")
//        savePreferencesString(context, AppConstants.EMAIL, data?.email ?: "")
//        savePreferencesString(context, AppConstants.BIO, data?.bio ?: "")
//        savePreferencesString(context, AppConstants.PROFILE_IMAGE, data?.profile_url ?: "")
//        savePreferencesInt(context, AppConstants.USER_ID, data?.id?:0)
//        savePreferencesInt(context, AppConstants.IS_PHONE_VERIFIED, data?.is_phone_verified?:0)
//        savePreferencesString(context, AppConstants.ROLE, data?.role?:"0")
//        savePreferencesString(context, AppConstants.PHONE_NUMBER, data?.phone?:"")
//        savePreferencesString(context, AppConstants.ADDRESS, data?.address?:"")
//        savePreferencesString(context, AppConstants.LATITUDE, data?.lat?:"")
//        savePreferencesString(context, AppConstants.LONGITUDE, data?.long?:"")
//        savePreferencesString(context, AppConstants.CITY, data?.city?:"")
//        savePreferencesString(context, AppConstants.STATE, data?.state?:"")
//      //  savePreferencesInt(context, AppConstants.DISTANCE, data?.distance?:0)
//    }
//    fun saveUserProfileData(context:Context, data:ProfileResponseData?) {
//        savePreferencesString(context, AppConstants.NAME, data?.name ?: "")
//        savePreferencesString(context, AppConstants.LAST_NAME, data?.last_name ?: "")
//        savePreferencesString(context, AppConstants.EMAIL, data?.email ?: "")
//        savePreferencesString(context, AppConstants.PROFILE_IMAGE, data?.profile_url ?: "")
//        savePreferencesInt(context, AppConstants.USER_ID, data?.id?:0)
//        savePreferencesString(context, AppConstants.BIO, data?.bio ?: "")
//        savePreferencesInt(context, AppConstants.IS_PHONE_VERIFIED, data?.is_phone_verified?:0)
//        //savePreferencesInt(context, AppConstants.DISTANCE, data?.distance?:0)
//        savePreferencesString(context, AppConstants.ROLE, data?.role?:"0")
//        savePreferencesString(context, AppConstants.PHONE_NUMBER, data?.phone?:"")
//        savePreferencesString(context, AppConstants.ADDRESS, data?.address?:"")
//        savePreferencesString(context, AppConstants.LATITUDE, data?.lat?:"")
//        savePreferencesString(context, AppConstants.LONGITUDE, data?.long?:"")
//        savePreferencesString(context, AppConstants.CITY, data?.city?:"")
//        savePreferencesString(context, AppConstants.STATE, data?.state?:"")
//    }
    fun replaceFragment(
        fragment: Fragment,
        removeStack: Boolean,
        activity: FragmentActivity,
        mContainer: FrameLayout
    ) {
        val fragmentManager = activity.supportFragmentManager
        val ftTransaction = fragmentManager.beginTransaction()
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ftTransaction.replace(mContainer.id, fragment)
        } else {
            ftTransaction.replace(mContainer.id, fragment)
            ftTransaction.addToBackStack(null)
        }
        ftTransaction.commit()
    }

    fun countChar(str: String, c: Char): Int {
        var count = 0

        for (element in str) {
            if (element == c)
                count++
        }

        return count
    }


    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS


    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    fun getTimeAgo(date: String): String {
        val  dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        Log.d("Time----",TimeZone.getDefault().toString())
        val  pasTime = dateFormat.parse(date)

        var time = pasTime.time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = currentDate().time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "moments ago"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }
    }

    fun likeCountManage(value:Int,suffix:String):String{
       return when {
           value==0 -> {
               "No ${suffix}s"
           }
           value==1 -> {
               "$value ${suffix}"
           }
           value>=100 -> {
               (value/1000).toString()+"k "+suffix+"s"
           }
           else -> {
               "$value ${suffix}s"
           }
       }
    }
     fun showDialogOK(context: Context, title: String, message: String, okListener: DialogInterface.OnClickListener) {
        val dialog = AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(title)
        dialog.setMessage(message)
            .setPositiveButton(context.getString(R.string.yes), okListener)
            .setNegativeButton(context.getString(R.string.no), okListener)
            .create()
            .show()
    }
    fun showImageFromGlide(context: Context?, view: ImageView, imageUrl: String, placeHolder: Int) {
        if (imageUrl.isNotEmpty()) {
            if (context != null) {
                /*  Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.NONE)
                      .skipMemoryCache(true).placeholder(placeHolder).error(placeHolder)
                      .into(view)*/

                Glide.with(context).load(imageUrl).placeholder(placeHolder).error(placeHolder)
                    .into(view)
            }
        }
    }
}

