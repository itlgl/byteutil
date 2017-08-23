# byteutil

有关byte数据处理的工具类

## 依赖配置

### maven配置

```maven
<!-- jcenter仓库地址，默认maven没有这个仓库地址 -->
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com/</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.itlgl</groupId>
    <artifactId>byteutil</artifactId>
    <version>0.0.4</version>
</dependency>
```

### gradle配置

```gradle
compile 'com.itlgl:byteutil:0.0.4'
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

### 直接下载



## 如何打包

项目使用Eclipse编译，在`pom.xml`上右键，选择`Run As->Maven build...`，在`Goals`里输入`deploy`即可打包

## jcenter配置说明

1. 在项目的pom.xml文件中增加build plugins的信息，用来生成源码和test-jar
2. 将pom.xml文件中的部署位置修改为bintray的url
3. 在`<userdir>/.m2/`文件目录下增加一个settings.xml的文件，用于设置当前用户的环境配置，书写方式可以参考项目中的settings.xml。需要注意的是，pom.xml中`<distributionManagement>`标签下的`id`值要和setting.xml中`<server>`下的id值相同
4. 具体参考项目文件和[bintray-examples](https://github.com/bintray/bintray-examples)

## 关于我

一个Android开发者

## 联系我

email: ligl6688@gmail.com
