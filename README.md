# byteutil

有关byte数据处理的工具类

[![Build Status](https://www.travis-ci.org/itlgl/byteutil.svg?branch=master)](https://www.travis-ci.org/itlgl/byteutil)

## 依赖配置

### maven配置

```maven
<dependency>
    <groupId>com.itlgl</groupId>
    <artifactId>byteutil</artifactId>
    <version>0.0.5</version>
</dependency>
```

### gradle配置

```gradle
compile 'com.itlgl:byteutil:0.0.5'
```

Android Studio中需要额外增加配置

```gradle
android {

    packagingOptions {
        exclude 'META-INF/maven/com.itlgl/byteutil/pom.properties'
        exclude 'META-INF/maven/com.itlgl/byteutil/pom.xml'
    }
}
```

否则会因为`META-INF`文件报错：

```cmd
Error:Execution failed for task ':app:transformResourcesWithMergeJavaResForDebug'.
> com.android.build.api.transform.TransformException: com.android.builder.packaging.DuplicateFileException: Duplicate files copied in APK META-INF/maven/com.ligl/byteutil/pom.properties
	File1: C:\Users\ligl01\.gradle\caches\modules-2\files-2.1\com.ligl\byteutil\0.0.1\48d08a5499328c65e87fcbd1f01fdd6ad686eca2\byteutil-0.0.1.jar
	File2: C:\Users\ligl01\.gradle\caches\modules-2\files-2.1\com.ligl\byteutil\0.0.1\48d08a5499328c65e87fcbd1f01fdd6ad686eca2\byteutil-0.0.1.jar
```

## 直接下载jar包

[byteutil-0.0.5.jar](http://search.maven.org/remotecontent?filepath=com/itlgl/byteutil/0.0.5/byteutil-0.0.5.jar)

## jcenter配置说明

1. 在项目的pom.xml文件中增加build plugins的信息，用来生成源码和test-jar
2. 将pom.xml文件中的部署位置修改为bintray的url
3. 在`<userdir>/.m2/`文件目录下增加一个settings.xml的文件，用于设置当前用户的环境配置，书写方式可以参考项目中的settings.xml。需要注意的是，pom.xml中`<distributionManagement>`标签下的`id`值要和setting.xml中`<server>`下的id值相同
4. 具体参考项目文件和[bintray-examples](https://github.com/bintray/bintray-examples)

## maven配置说明

maven配置参考`http://www.cnblogs.com/softidea/p/6743108.html`文章进行设置，因为需要同时上传到两个仓库中，所以修改了一下pom文件，建立了两个profile，具体参考项目配置

## 如何打包上传到中央仓库

因为需要同时上传到两个仓库中，所以修改了一下pom文件，建立了两个profile，进行分别上传

不知道因为什么问题，在Eclipse中是不能进行打包上传操作的，使用命令窗口可以

上传到maven仓库的指令：
```cmd
mvn clean deploy -P sonatype-oss-release -Darguments="gpg.passphrase=这个passphrase没有起作用"
```

上传到jcenter仓库的指令：
```cmd
mvn clean deploy -P jcenter-bintray-release -Darguments="gpg.passphrase=这个passphrase没有起作用"
```

## 关于我

一个Android开发者

## 联系我

email: ligl6688@gmail.com
