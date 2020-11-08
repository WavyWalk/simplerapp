package commands.utils

import ApplicationBootstrapper


fun initializeCliMode() {
    App.isInCliMode = true
    ApplicationBootstrapper().runOnBootstrap()
}