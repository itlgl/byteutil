# byteutil

有关byte数据处理的工具类

[![Build Status](https://www.travis-ci.org/itlgl/byteutil.svg?branch=master)](https://www.travis-ci.org/itlgl/byteutil)

## 如何使用

[doc文档](http://itlgl.com/byteutil/apidocs/)

## 依赖配置

### maven配置

```maven
<dependency>
    <groupId>com.itlgl</groupId>
    <artifactId>byteutil</artifactId>
    <version>0.0.6</version>
</dependency>
```

### gradle配置

```gradle
compile 'com.itlgl:byteutil:0.0.6'
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

[byteutil-0.0.6.jar](http://search.maven.org/remotecontent?filepath=com/itlgl/byteutil/0.0.6/byteutil-0.0.6.jar)

## 关于我

一个Android开发者
