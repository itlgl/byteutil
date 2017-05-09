# byteutil

有关byte数据处理的工具类

## 如何使用

maven配置：

```maven
<repositories>
    <repository>
    <id>ligl-mvn-repo</id>
    <url>https://raw.githubusercontent.com/ligl01/mvn-repo/master/</url>
    </repository>
</repositories>
  
<dependencies>
    <dependency>
        <groupId>com.ligl</groupId>
        <artifactId>byteutil</artifactId>
        <version>0.0.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

gradle配置：

```gradle
repositories {
    maven { url 'https://raw.githubusercontent.com/ligl01/mvn-repo/master'}
}

dependencies {
    compile 'com.ligl:byteutil:0.0.1'
}
```

如果运行中出现问题：

```
Error:Execution failed for task ':app:transformResourcesWithMergeJavaResForDebug'.
> com.android.build.api.transform.TransformException: com.android.builder.packaging.DuplicateFileException: Duplicate files copied in APK META-INF/maven/com.ligl/byteutil/pom.properties
	File1: C:\Users\ligl01\.gradle\caches\modules-2\files-2.1\com.ligl\byteutil\0.0.1\48d08a5499328c65e87fcbd1f01fdd6ad686eca2\byteutil-0.0.1.jar
	File2: C:\Users\ligl01\.gradle\caches\modules-2\files-2.1\com.ligl\byteutil\0.0.1\48d08a5499328c65e87fcbd1f01fdd6ad686eca2\byteutil-0.0.1.jar
```

参考：[StackOverflow](http://stackoverflow.com/questions/27977396/android-studio-duplicate-files-copied-in-apk-meta-inf-dependencies-when-compile)上的回答，在`build.gradle`文件中加入:

```
android {

    packagingOptions {
        exclude 'META-INF/*'
    }
}
```


## 关于我
一个Android开发者

邮箱: ligl6688@gmail.com
