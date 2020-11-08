# Simpler
When I jumped into JVM (web dev side) world after things like Rails and stuff, one thought just bogged me all the time: 
* why the hell is it so complicated?
* why there is no stuff like Sinatra (and if there are they are so tightly coupled with other complicated stuff)?
* why to access passed parameters or form I have to write a 100 line class?
This lib is a hobby propgrammer's approach to writing a Kotlin based web apps.

 [what it provides](#description)
 
[dependencies](#description)

[how do I start](#installation)

[setup](#setup)

[routing](#routing)

[controllers](#controllers)

[request parsing](#requestparsing)


for a simpler orm go to [simpler-orm repo](https://github.com/WavyWalk/simpler-orm)

# What it provides <a name="description"></a>

* a router supporting named parameters and wild cards.
* a convenient wrapper for ServletContextObject, to ease your work with incoming parameters/forms/uploaded files (including hash format forms the way it is understood outside of Java world).
* primitive cookies wrapper
* a base controller (that you just can simply not use), that gives you ability for convenient accessors to the stuff above plus templating.
* non obtrusive non opinionated. Do what you want, EE provides you with just alright stuff.
# Dependencies <a name="dependencies"></a>
Most of the stuff is coded against an interface and you can write your own adapters.
for Json parameters wrapper there is [simpler-jacksonadapter](https://github.com/WavyWalk/simpler-jackson-adapter)
for Freemarker there is [simpler-freemarker-adapter](https://github.com/WavyWalk/simpler-freemarker-adapter)
for embedded server there's [simpler-embedded jetty](https://github.com/WavyWalk/simpler-embedded-jetty)
# How do I start? <a name="installation"></a>
```kotlin
//build.gradle
repositories {
    mavenCentral()
    maven {
        url  "https://dl.bintray.com/wavywalk/maven" //add this repo
    }
}

dependencies {
    compile 'at.wavywalk.simpler:simpler:0.1.0:sources'
    compile 'at.wavywalk.simpler:simpler:0.1.0'
}

```
# Setup: <a name="setup"></a>
* create your bootstrapper class and inherits from `SimplerApplicationBootstrapper`. Annotate your bootstrapper with `@WebListener`
 implement `runOnBootstrap` and `runOnShutDown` functions.
 In `runOnBootstrap` or via your fave dependecy injector provide a "simpler" with it's dependencies as follows (simplified):
 ```kotlin
SimplerDependencyManager.provider = SimplerDependenciesProvider(  
    servletRequestParametersWrapper = ServletRequestParametersWrapper(  
        jsonParametersParser = JacksonParametersParser(ObjectMapper()) // [simpler-jacksonadapter](https://github.com/WavyWalk/simpler-jackson-adapter)
    ),  
    assetsPathProvider = AssetsPathProvider(PublicFolderConfig()), // default is /src/main/webapp/public.  
    templateProcessor = FreemarkerTemplateProcessor(configuration) //an only one fro now adapter [simpler-freemarker-adapter](https://github.com/WavyWalk/simpler-freemarker-adapter) 
)
```
*  Configure routing:
create your routing class that inherits from `RoutesDrawer(Router)` for example, 
```kotlin
object RoutesConfig: RoutesDrawer(Router) {  
    override fun run() {  
        get("/*") {  
             BaseController(it).renderPlain("Hello")  
        }  
    }  
}
```
in your `runOnBootstrap()` call `RoutesConfig.run()`
use gretty or simpler-embedded-jetty (separate repo, create main.kt and run it from there) or drop it in to your container.
# Routing <a name="routing"></a>
Routing will look familiar to you, all you can use example is below:
as said it supports named arguments and wild cards. It doesn't use regex, so it should be fast (it implements sort of Radix Tree)
```kotlin
object RoutesConfig: RoutesDrawer(Router) {
    override fun run() {
        namespace("/api") {  // route namespaces
            namespace("/user") {  // namespaces in namespaces
                get("/:id") { //named route parameters
                    //yielded `it` is a context wrapper
                    UserController(it).show()  //calling a controller inheriting from BaseController
                }
                namespace("/:userId/account") {  // named route parameters in namespaces
                    get("/:accountId") {
                        AccountOfUserController(it).show()
                    }
                }
                get("") {
                    UserController(it).index()
                }
                put("/:id") {  // all rest terms
                    UserController(it).update()
                }
                post("") {
                    UserController(it).create()
                }
                delete("/:id") {
                    UserController(it).delete()
                }
                get("/:id/hello") {
                    UserController(it).sayhi()
                }
            }
        }
        get("/public/*") {  //catch all routes. you can add routes after it and it will resolve correectly. eg. \/* will not consume \/*/hello/:id
            ToDefaultServletForwarder().forward(it) //forward servlet request to containers default servlet (e.g. for file serving) // just example implied that you'd just use stuff like nginx for that   
        }
        get("/favicon.ico") {
            PrimitiveFileServer().serveFile(it) // just for dev purposes serve a file  
        }
        get("/*") {
            BaseController(it).renderTemplate("index.ftl", mutableMapOf())  // render template
        }
        get("/*") {
            BaseController(it).requestParams().get("user")?.get("name")?.string //process a form without implementing a controller
        }
        get("/getMeAstandartRequestResponse") {  //access standart request objects
            it.request.requestURI
            it.response.writer.print(it.request.requestURI)
            it.response.writer.close()
        }
    }
}
```
# Controllers <a name="controllers"></a>
For a convenient use there is provided a SimplerBaseController from which you can inherit.
Just inspect code in it.
Main features are:
```kotlin
fun routeParams(): Map<String, String> // e.g for route /user/:userId / routeParams().getOrDefault(":userId", null)
fun renderTemplate(templateName: String, dataModel: Any) //would render template through provided adapter
fun requestQueryStringParams(): Map<String,String>
fun sendError(errorCode: Int)
fun head(statusCode: Int)
fun renderJson(body: String)
fun requestParams() //info below
//etc
//you can use standart request response objects there as well accessed via context.request / reponse
```
# Request parsing <a name="requestparsing"></a>
On of the best things simpler has - a convenient wrapper for your request params passed from client
be it a encoded form or a json it just get's what you need, without boilerplate. For forms it uses the hash format e.g. user[account][phone]  in html form. So you just treat forms like map/list objects.
just call `requestParams()`, and it will parse the encoded form (including uploaded file) or a Json (depending on content type), and you just get a unified access to it.
for example you get a post request of:
```yaml
user:
    name: joe
    id: 10
    friends:
        - user:
        name: foo
        id: 10
        - user:
        name: bar
    avatar:
        file: an uploaded file
```
it may come as a encoded form (user[name]="joe", user[friends][0][name]="foo" and etc.) or as a json:
and you just simply use your request params without writing parsers or anything (well you can if you want to):
```kotlin
requestParams().get("user")?.get("joe").string
requestParams().get("user")?.get("id").long
requestParams().get("user")?.get("friends")?.paramList()?.first()?.get("name")?.string
requestParams().get("user")?.get("avatar")?.get("file")?.fileItem
```
File uploads are handled with Apache Commons FileUpload. To get a FileItem look at the example above.
to limit max file body call requestParams(maxContentLenght: Long). Encoded form wrapper is coded against an interface so you can provide your own adapter (e.g. use Servlet > 3.0 spec for file handling). I remained on Apache's because I believe it is used as default for file uploads.

that's basically it

# Licensing
All code written by me in this repo is under MIT or WTFPL licenses, whatever you wish.
For dependencies, see their respective licenses.