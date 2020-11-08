# preface
Hibernate is a divine tech that mere mortals can hardly master (except the simplest CRUD operations).
How sweet it is in ActiveRecord or Eloquent to control your models and how it's missed in JVM cruel world, for someone 
who wants to jump in it.
This lib is an approach to ORM of a hobby programmer!

# features
* no reflection, all is generated through annotations
* unopinionated, unobtrusive (may become obtrusive for some features).
* can be used with minimal obtrusion, and has advanced features like active record
* Uses Jooq as query generator. Basically this lib is an active record atop of it. Gives you static queries.
* supports all kinds of relations including polymorphic
* properties trackers
* good validation approach

# installation
```kotlin
//build.gradle
repositories {
    mavenCentral()
    maven {
        url  "https://dl.bintray.com/wavywalk/maven" //add this repo
    }
}

dependencies {
    compile 'at.wavywalk.simpler:simpler-orm:0.1.0'
    compile 'at.wavywalk.simpler:simpler-orm:0.1.0:sources'
    kapt 'at.wavywalk.simpler:simpler-orm:0.1.0' // kapt it
}
```
# dependencies
Depends on Jooq heavily.

Json serialization is interfaced. Jackson adapter available in separate repo.

# setup
run in your app

```kotlin
SimplerOrmDependenciesManager.provider = SimplerOrmDependenciesProvider(
            jsonObjectMapper = JacksonObjectMapperAdapter(), //adapter for Json serialization
            defaultDslContext = dsl // Jooqs dsl context with datasource // e.g. some pooled ds // refer to Jooq, it's easy0
        )
```

# Basic example
Create/migrate db.

Generate Jooq schema (refer to Jooq's docs).

Than, simple example class would look something like this:
```
@IsModel(jooqTable = Users::class) // Jooq's table's class
class User {
    //That record lazy property is required to be, through that property you access the active record like capabilities.
    val record: UserRecord by lazy { UserRecord(this) } // UserRecord will be generated

    @TableField(name="ID")
    @IsPrimaryKey // for now only self incrementing primary keys supported
    var id: Long? = null

    @TableField(name="NAME")
    var name: String? = null
        
    @HasOne(model = Account::class, fieldOnThat = "USER_ID", fieldOnThis = "ID") // :)
    var accounts: MutableList<Account>? = null
}

```
When building all will be generated

# Querying
some complicated example of querying and loading associated objects.
```kotlin
 val approval = ApprovalRecord.GET()
     //pass any Jooq conditions.
     .where(table.APPROVABLE_ID.eq(contractId).and(table.APPROVABLE_TYPE.eq(Approval.APPROVABLE_TYPES.CONTRACT)))
     //theres just no lazy loading for associated, you must specify things explicitly
     //but that's not a bad thing, you are in total control of graph
     .preload {
        //select which associated to preload // this case HasMany ApprovalRejection
         it.approvalRejections() {
             //you can preload sub associated objects
             it.preload {
                 it.approvalRejectionToUploadedDocumentLinks() {
                     //nested preloads
                     it.preload {
                         it.uploadedDocument() {
                            //you can also put queries specific for appropriate preload.
                            it.where(UPLOADED_DOCUMENTS.TYPE.eq(UploadedDocument.DOCUMENT_TYPES.ACTUAL))
                         }
                     }
                 }
                 it.user() {
                    it.preload {
                        it.account() {
                            //which to properties to select when preloading
                            it.whichPropertiesToLoad.also {
                                it.password = false
                            }
                        }
                    }
                 }
                 it.discussion() {
                    it.preload {
                        it.discussionMessages()
                    }
                 }
             }
         }
         it.approvalSteps() {
             it.preload {
                 it.approvalStepToApproverLinks() {
                     it.preload {
                         it.user()
                     }
                 }
                 it.approvalStepToUploadedDocumentLinks() {
                     it.preload {
                         it.uploadedDocument()
                     }
                 }
             }
         }
     }
     .limit(1)
     .execute()
     .firstOrNull()!!
     
 approval.record.approvalRejections?.forEach {
    var email = it.record.user!!.record.account!!.record.email // joe@doe.com
 }
```
In the above example everything will be loaded just if you'd write it by hand. No n+1 problems and no need to jump into intrinsic in order to understand
if something will implicitly be done, it will obbey you just as you would explicitly command, without doing something funky in the background.

Zero unsafe things like passing strings etc. As everything is static, IDE will almost write your queries for you.

# Persisting
When saving, with `record.save()`: 
- create if not exists.
- do nothing if exists and not changed
- throw ModelInvalid if record is invalid (`record.validationManager.isValid()`)
- update only changed properties (there is `record.propertiesChangeTracker`)
- no associated models are saved.

when saving with `record.saveCascade()`
- all as above
- same as above applied for associated model on model (they'll also call saveCascade())

in transaction scenarios transaction dsl should be passed `to save(tx)` `saveCascade(tx)` 

There is no cascaded destruction. Destruction/orphaning managed explicitly (referential constraints are your friends).

# Serialization

And now how you'd serialize the above example to json:
```kotlin
ApprovalToJsonSerializer(approval).also {
        it.includeApprovalRejections() {
            it.includeApprovalRejectionToUploadedDocumentLinks() {
                it.includeUploadedDocument()
            }
            it.includeDiscussion()
            it.includeUser() {
                it.includeAccount() {
                    it.whichPropertiesToSerialize.also {
                        it.password = false
                    }
                }
            }
            it.includeDiscussion {
                it.includeDiscussionMessages()
            }
        }
        it.includeApprovalSteps() {
            it.includeApprovalStepToUploadedDocumentLinks() {
                it.includeUploadedDocument()
            }
            it.includeApprovalStepToApproverLinks() {
                it.includeUser()
    
            }
        }
    }.serializeToString()
```
Again you're in control of serialized result.

# Annotations

Yeah I know they look funny and are no JPA complaint.

You annotate your model with `@IsModel`, there you must provide Jooq's table class as argument.

Your mapped to DB fields are annotated with `@TableField` there you pass a name as it's in your db.

Your primary key should be annotated with @IsPrimaryKey. //only autoincremented keys are supported for now.

# associations
```kotlin
@HasMany(model: KClass<*>, fieldOnThis: String, fieldOnThat: String )
var accounts: MutableList<Account>? = null

@HasOne(model: KClass<*>, fieldOnThis: String, fieldOnThat: String )
var account: Account? = null

@BelongsTo(model: KClass<*>, fieldOnThis: String, fieldOnThat: String )
var user: User? = null
```
there is also a polymorphic support but, it should better not be used.
ManyToMany is not implemented (for now). Can be done via linking that belongs to two models

# Validation
Record has an access to a validation manager, which has minimum required convenient methods to add, controll, remove the errors.
Auto validation is evil. Just write a specific class for it. For example a validator that is used for model of the above examples:
```kotlin
class ApprovalRejectionValidator(model: ApprovalRejection) : ApprovalRejectionValidatorTrait(model, model.record.validationManager) {
    //just create an explicit validation scenarios
    //or create just one - the mighty default
    fun ofContractCreateScenario(){
        validateReasonText()
        validateApprovalRejectionToUploadedDocumentLinks()
    }
    //provide a method for validation of a property
    fun validateReasonText() {
        val reasonText = model.reasonText
        if (reasonText == null || reasonText.isBlank()) {
            //ValidationManager provides methods for recording errors
            //later it can for example be asked if record should be saved
            // if not otherwise specified when saving will throw invalidError
            validationManager.addReasonTextError("should be provided")
        }
    }
    //invoke validators of associated
    fun validateApprovalRejectionToUploadedDocumentLinks() {
        model.record.approvalRejectionToUploadedDocumentLinks?.forEach {
            ApprovalRejectionToUploadedDocumentLinkValidator(it).defaultScenario()
            it.uploadedDocument?.let {
                UploadedDocumentValidator(it).defaultScenario()
                if (!it.record.validationManager.isValid()) {
                    //inform top model that it has nested errors, to not throw when saving.
                    //propagate error beforehand, not wasting a transaction.
                    validationManager.markAsHasNestedErrors()
                }
            } ?: throw IllegalStateException()
            if (!it.record.validationManager.isValid()) {
                validationManager.markAsHasNestedErrors()
            }
        }
    }
}
//in code
ApprovalRejectionValidator(approvalRejection).ofContractCreateScenario()
if (approvalRejection.record.isValid()) {
    approvalRejection.saveCascade()
}
```

# Transactions
```kotlin

val user = User() {
    it.name = "joe"
    it.account = Account() {
        it.record.validationManager.addEmailError("Invalid")
    }    
}
try {
    TransactionRunner.run {
        user.record.saveCascade(it.inTransactionDsl) // will throw ModelInvalid
    }
} catch (error: ModelInvalidError) {
    //tx is rolled back
    //nohing's saved    
}

```
# On implementation
As said, lib uses code generation from templates. Some may argue that code generation is evil and a bad practice. But I see it as follows -
Is reflection an answer? It's just the same sort of code generation, but the sort you can't touch, and see the end result.
It's a black box.
With generation you just get sort of a static metaprogramming thing.

You just can go to generated file and see how everything is implemented. Compiler catches most errors, and you get a IDE completion bonus.
Just of course do not modify the generated files.

# Non active record approach
It can be done, there are separate classes generated for querying, serializing, persisting etc. your plain user POJO without refering record. 
It's usable and fast but it's more code.
Those things should be used in some cases where extreme explicitness is required. *(I'm tired will write description later)

# licenses
All code written by me in this repo is under MIT or WTFPL licenses, whatever you wish.
For dependencies, see their respective licenses.

 





