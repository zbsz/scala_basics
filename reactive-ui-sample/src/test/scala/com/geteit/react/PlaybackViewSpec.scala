package com.geteit.react

import android.app.Activity
import com.geteit.app.ActivityHelper
import com.geteit.react.service.PlaybackState
import com.geteit.util.returning
import org.robolectric.annotation.Config
import org.robolectric.{Robolectric, Shadows}
import org.scalatest.{FeatureSpec, Matchers, RobolectricSuite}

@Config(manifest = "src/main/TestManifest.xml")
class PlaybackViewSpec extends FeatureSpec with Matchers with RobolectricSuite {

  lazy val activity = Robolectric.buildActivity(classOf[TestActivity]).create().resume()

  lazy val view = returning(new PlaybackView(activity.get(), null))(_.onAttachedToWindow())

  lazy val service = view.service

  feature("Playback state") {

    scenario("Update play btn icon on playback state changes.") {

      def btnDrawable = Shadows.shadowOf(view.btnPlay.getDrawable).getCreatedFromResId

      btnDrawable shouldEqual R.drawable.ico_play

      service.playbackState ! PlaybackState.Playing
      btnDrawable shouldEqual R.drawable.ico_pause

      service.playbackState ! PlaybackState.Paused
      btnDrawable shouldEqual R.drawable.ico_play

      service.playbackState ! PlaybackState.Stopped
      btnDrawable shouldEqual R.drawable.ico_play
    }
  }
}

class TestActivity extends Activity with ActivityHelper
