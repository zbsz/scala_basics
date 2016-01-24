#!/bin/shell

wget http://downloads.typesafe.com/scala/2.11.7/scala-2.11.7.tgz
tar zxf scala-2.11.7.tgz
cd scala-2.11.7/lib

echo "dexing scala library ..."
dx --dex --output=scala_library_211.jar scala-library.jar

echo "dexing scala reflect ..."
dx --dex --output=scala_reflect_211.jar scala-reflect.jar

libs="<?xml version="1.0" encoding="utf-8"?>\n
<permissions>\n
    <library name="scala_library_2.11" file="/system/framework/scala_library_211.jar" />\n
    <library name="scala_reflect_2.11" file="/system/framework/scala_reflect_211.jar" />\n
</permissions>\n
"
echo -e $libs > custom_libs.xml

echo "installing libs ..."
adb push scala_library_211.jar /sdcard/
adb push scala_reflect_211.jar /sdcard/
adb push custom_libs.xml /sdcard/
adb shell "su -c 'mount -o rw,remount /system'"
adb shell "su -c 'cp /sdcard/scala* /system/framework/'"
adb shell "su -c 'cp /sdcard/custom_libs.cml /system/etc/permissions/'"
adb shell "su -c 'chmod 644 /system/framework/scala_*'"
adb shell "su -c 'chmod 644 /system/etc/permissions/custom_libs.xml'"
echo "done"
