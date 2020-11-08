# What's it?
An adapter for simpler framework's json requests parser.
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
    compile 'at.wavywalk.simpler:simpler-jacksonadapter:0.1.0:sources'
    compile 'at.wavywalk.simpler:simpler-jacksonadapter:0.1.0'
}

```
# How to use with simpler
When building `SimplerDependenciesProvider`, as a value to `servletRequestParametersWrapper` argument pass:

```kotlin
ServletRequestParametersWrapper(  
        jsonParametersParser = JacksonParametersParser(ObjectMapper()) //simplified example
    )
```

That's it. It does nothing on it's own