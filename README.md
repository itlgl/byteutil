# byteutil

有关byte数据处理的工具类

## 依赖配置

** maven配置： **

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
        <version>0.0.3</version>
    </dependency>
</dependencies>
```

** gradle配置： **

```gradle
repositories {
    maven { url 'https://raw.githubusercontent.com/ligl01/mvn-repo/master'}
}

dependencies {
    compile 'com.ligl:byteutil:0.0.3'
}
```

** Android Studio中使用额外增加配置： **

```gradle
android {

    packagingOptions {
        exclude 'META-INF/maven/com.ligl/byteutil/pom.properties'
        exclude 'META-INF/maven/com.ligl/byteutil/pom.xml'
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

## 如何打包

项目使用Eclipse编译，在`pom.xml`上右键，选择`Run As->Maven build...`，在`Goals`里输入`deploy`即可打包

默认maven包的存放目录为`file:D:/github/mvn-repo/`，可以在`pom.xml`文件中修改打包位置

## 关于我
一个Android开发者

## 联系我

邮箱: ligl6688@gmail.com
