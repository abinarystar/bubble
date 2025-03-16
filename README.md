# bubble

## About

`bubble` is created as a demo to GraalVM, an AOT (ahead of time) compilation technique on Java to reduce application
start up time and memory usage.

## Getting Started

### Prerequisites

To run `bubble`, you will need the following applications installed on your system:

- [Apache Maven](https://maven.apache.org/download.cgi) (minimum version: 3.6.3)
- [JDK](https://jdk.java.net/archive/) (minimum version: 21)
- [GraalVM JDK](https://github.com/graalvm/graalvm-ce-builds/releases/) (minimum version: 21)

To verify your configuration, execute this command:

```shell
mvn -v
```

The output should display Java version with `GraalVM Community` as the vendor.

### Run without GraalVM

Before we start using GraalVM, we need to make sure `bubble` can run on regular JDK. Execute the following command on
your terminal to compile the project.

On Windows:

```shell
mvn clean compile test
```

Execute the following command to run the application.

On Windows:

```shell
mvn spring-boot:run
```

If everything works well, you will see a log `Started BubbleApplication in ... seconds (process running for ...)`.

Open [http://localhost:8080/docs](http://localhost:8080/docs) to access Swagger UI.

To stop the application, press `ctrl+c`.

### Run with GraalVM

Execute the following command to compile the project. This will take a longer time to finish.

On Windows:

```shell
mvn -Pnative clean native:compile
```

Execute the following command to run the application.

On Windows:

```shell
target\bubble.exe
```

If everything works well, you will see a log `Started BubbleApplication in ... seconds (process running for ...)`. To
stop the application, press `ctrl+c`.

## GraalVM

GraalVM JDK is created to support AOT on Java based application. It means that Java can be compiled into native binary.
Since our goal is to reduce start up time and memory usage, we need to do some adjustment into the compilation itself.
You can see more detailed explanation on [GraalVM site](https://www.graalvm.org/latest/reference-manual/native-image/).

To achieve it, required objects will be created during compilation. GraalVM will traverse our codes beginning from the
`main` method. However, since Java has mechanism, such as Reflection, GraalVM might have no idea about those codes. This
in turn results in missing critical codes that are required during runtime. To solve this issue, GraalVM provides a
mechanism to developers as a way to tell GraalVM what files should be included in the binary. To do this, we use
reachability metadata. You can see more detailed explanation
on [GraalVM site](https://www.graalvm.org/latest/reference-manual/native-image/metadata/).

## Project Structure

### `pom.xml`

First, let's see the [pom.xml](pom.xml) file. We will highlight important points when compiling projects into native
binary.

We use `spring-boot-starter-parent` as our parent pom as usual. Important of note to use GraalVM, Spring already
provides us the native build tool and native profile. When compiling the project into native binary, we use
`mvn -Pnative clean native:compile`, where `-Pnative` enables the inclusion of AOT plugin, and `native:compile` uses
native build tool to compile the project.

To use `lombok`, we need to inform Maven compiler plugin. We need to explicitly add `lombok` into annotation processor
paths.

### `src`

Next, we move to `src` folder. We can ignore the `test` folder.

In `main/java`, there are two subpackages under [com.abinarystar](src/main/java/com/abinarystar) package. `core` package
is there to provide core library for our project. In real world, this would be placed on different project, which would
be imported as project dependency. Let's pretend that `core` package is not there.

In [com.abinarystar.bubble](src/main/java/com/abinarystar/bubble), we have a normal project structure. You can explore
this by yourself. One important thing to note here is `MailService`
in [CreateUserCommand](src/main/java/com/abinarystar/bubble/command/user/CreateUserCommand.java) class. This class
should be self-explanatory, However, we will revisit this again.

### `core`

There are several subpackages in [com.abinarystar.core](src/main/java/com/abinarystar/core). In real world, these
packages would be separated into different module or project. This is to reduce project dependencies if a project
doesn't need everything all at once. Let's pretend that each subpackage is a separated library.

First, let's look into `mail` library.
In [MailAutoConfiguration](src/main/java/com/abinarystar/core/mail/MailAutoConfiguration.java) class, we use
`@ConditionalOnProperty` to determine the mail service implementation. In GraalVM, since we wanted to increase start up
time, we need to choose the condition before compilation happens. This point is important in GraalVM compilation. All
`@ConditionalOnProperty` needs to be resolved before compilation, so we need to determine the value beforehand. This
limits our configuration since we can't change the implementation after the binary has created.

Next, let's open the [SimpleMailService](src/main/java/com/abinarystar/core/mail/SimpleMailService.java) class. When
calling `send` method using `TemplateMailRequest`, it will call
`templateService.generate` to get the content template. Let's open
the [TemplateService](src/main/java/com/abinarystar/core/template/TemplateService.java) to see how it generates the
template. As you can see, we are using Velocity library to create the template. The problem with Velocity is that it has
not supported GraalVM yet. Because Velocity uses reflection technique, the compilation result will not include these
codes, resulting in runtime exception when using Velocity.

To fix this, let's
open [NativeImageAutoConfiguration](src/main/java/com/abinarystar/core/nativeimage/NativeImageAutoConfiguration.java)
class under `nativeimage` library. In this class, we define a
`@ImportRuntimeHints(NativeImageRuntimeHintsRegistrar.class)` to let Spring know we wanted to give hints during GraalVM
compilation. So let's
open [NativeImageRuntimeHintsRegistrar](src/main/java/com/abinarystar/core/nativeimage/NativeImageRuntimeHintsRegistrar.java)
class.

In `NativeImageRuntimeHintsRegistrar`, we can tell GraalVM to include classes, resources, etc. by registering it to
`RuntimeHints`. In an ideal world, the maintainer of each library should provide their reachability metadata for
GraalVM. However, since Velocity lacks required reachability metadata, we need to register it ourselves.

There are two ways to do this:

- Create `RuntimeHintsRegistrar` implementation
- or, create static hint file under `resources` folder

See [Spring Boot GraalVM](https://docs.spring.io/spring-boot/reference/packaging/native-image/advanced-topics.html#packaging.native-image.advanced.custom-hints).

If we use the second method, we need to put the hint on folder
`src/main/resources/META-INF/native-image/{groupId}/{artifactId}-additional-hints/`. To use this method, we need to know
what files to include. This might not be a good strategy in our case.

In this project, we opt to use the first method. However, we normally would still need to define the files manually.
Fortunately, we can leverage Java to scan library classes to automate the process.

If you want to try the second method, you can copy-paste the logic inside `NativeImageRuntimeHintsRegistrar` to produce
the list of classes and resources as a hint to GraalVM. Then create static hint file under `resources` folder and
disable `NativeImageRuntimeHintsRegistrar`.

### `properties`

In Spring, we can create a model for our custom properties. For example, let's
open [DataProperties](src/main/java/com/abinarystar/core/data/DataProperties.java) class. Here, we have a nested class.
One important point to note here is that, in order for this model to work in native binary, we need to add
`@NestedConfigurationProperty` on every nested class inside our properties model.

### `resources`

Let's look into [src/main/resources](src/main/resources) folder. There are several files not relevant to GraalVM. Those
are related to core “library”.

We can ignore these files since it should be part of core “library”:

- `META-INF/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- `META-INF/spring.factories`
- `data.properties`
- `mail.propertes`
- `swagger.properties`
- `template.properties`
- `web.properties`

In `native-image` folder, files under `native-image` will be read by `NativeImageRuntimeHintsRegistrar`.

In `velocity-templates` folder, all files will be read by `TemplateService`.

## License

`bubble` uses a small part of `abinarystar-spring` library, located under `com.abinarystar.core` package.
`abinarystar-spring` is released under Apache License, Version 2.0. See `NOTICE.txt` for more information.

`bubble` is released under [Unlicensed](UNLICENSE.txt) license.
