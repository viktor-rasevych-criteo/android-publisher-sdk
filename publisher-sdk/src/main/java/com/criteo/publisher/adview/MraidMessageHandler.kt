/*
 *    Copyright 2020 Criteo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.criteo.publisher.adview

import android.util.Log
import android.webkit.JavascriptInterface
import androidx.annotation.Keep
import com.criteo.publisher.annotation.Internal
import com.criteo.publisher.annotation.OpenForTesting
import com.criteo.publisher.logging.LogMessage
import com.criteo.publisher.logging.LoggerFactory
import com.criteo.publisher.util.asAndroidLogLevel

@OpenForTesting
@Internal
@Keep
class MraidMessageHandler {

  private val logger = LoggerFactory.getLogger(javaClass)
  private var listener: MraidMessageHandlerListener? = null

  fun setListener(listener: MraidMessageHandlerListener) {
    this.listener = listener
  }

  @JavascriptInterface
  fun log(logLevel: String, message: String, logId: String?) {
    logger.log(LogMessage(logLevel.asAndroidLogLevel() ?: Log.DEBUG, message, logId = logId))
  }

  @JavascriptInterface
  fun open(url: String) {
    listener?.onOpen(url)
  }

  @JavascriptInterface
  fun expand(width: Double, height: Double) {
    listener?.onExpand(width, height)
  }

  @JavascriptInterface
  fun close() {
    listener?.onClose()
  }

  @JavascriptInterface
  fun playVideo(url: String) {
    listener?.onPlayVideo(url)
  }
}
