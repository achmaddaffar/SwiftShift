package com.daffa.swiftshift.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.daffa.swiftshift.util.getFileName
import com.yalantis.ucrop.UCrop
import java.io.File

class CropActivityResultContract(
    private val xAspectRatio: Float,
    private val yAspectRatio: Float
): ActivityResultContract<Uri, Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(
            input,
            Uri.fromFile(
                File(
                    context.cacheDir,
                    context.contentResolver.getFileName(input)
                )
            )
        )
            .withAspectRatio(xAspectRatio, yAspectRatio)
            .getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return UCrop.getOutput(intent ?: return null)
    }
}