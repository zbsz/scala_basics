package com.geteit.react

import com.geteit.app.{GtContext, GtApplication}
import com.geteit.events.EventContext
import com.geteit.inject.{GtAppModule, GtModule, Module}
import com.geteit.react.service.{PlaybackStorage, PlaybackService}

class ReactiveUiApplication extends GtApplication {

  override lazy val module = AppModule :: GtModule

  val AppModule = Module { implicit bind =>
    bind [PlaybackStorage] to new PlaybackStorage(getApplicationContext)
    bind [PlaybackService] to new PlaybackService(getApplicationContext)(EventContext.Global, bind)
  }
}
