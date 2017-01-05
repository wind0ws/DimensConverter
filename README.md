# DimensConverter
The dimens converter for android.

>This is intent to calculate dp size to adapt for different devices.

###Sample usage:

1.compile project and build a jar.

2.use "-i" to go to  edit mode,you can input dimens.xml path,standard dimens dpi and res folder path manually.

3.use "-dimens -dimensDpi -res -dpiNames" for generate dimens.
 * -dimens    : standard dimens.xml path.
 * -dimensDpi : standard dimens.xml dpi name,eg: mdpi
 * -res : res folder,which will generate dimens to this folder.
 * -dpiNames : which dpi should generate.

4.use "-h": show help.

5.sample:
```$xslt
java -jar Dimens.jar -dimens /Users/threshold/Documents/Android/projects/RxJava2Demo/app/src/main/res/values/dimens.xml -dimensDpi xhdpi -res /Users/threshold/Documents/Android/projects/RxJava2Demo/app/src/main/res -dpiNames ldpi,mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi

```
this sample means the standard dimens.xml is xhdpi and will generate ldpi,mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi dimens in res folder.(Actually will save it to res/values-dpiName folder)
