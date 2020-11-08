# What's it?
A Freemarker adapter for simpler framework's templating rendering.
# how to install
```kotlin
//build.gradle
repositories {
    mavenCentral()
    maven {
        url  "https://dl.bintray.com/wavywalk/maven" //add this repo
    }
}

dependencies {
    compile 'at.wavywalk.simpler:simpler-freemarker-adapter:0.1.0:sources'
    compile 'at.wavywalk.simpler:simpler-freemarker-adapter:0.1.0'
}
```
# How to use with simpler
When building `SimplerDependenciesProvider`:

```kotlin
SimplerDependencyManager.provider = SimplerDependenciesProvider(
            //servletRequestParametersWrapper =  ServletRequestParametersWrapper(
            //    jsonParametersParser = JacksonParametersParser(ObjectMapper())
            //),
            //assetsPathProvider = AssetsPathProvider(publicFolderConfig),
            templateProcessor = FreemarkerTemplateProcessor(configuration) //pass here/ configuration is the configuration object that your suposed to build on your own
        )
```

That's it. It does nothing on it's own