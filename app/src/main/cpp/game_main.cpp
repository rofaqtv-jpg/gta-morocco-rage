#include <jni.h>
#include <android/log.h>
#include "rage/rage_core.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_turrinistudio_gtav_MainActivity_stringFromJNI(JNIEnv* env, jobject) {
    RAGE::Core::Init(); // كيبدأ المحرك بحال GTA
    return env->NewStringUTF("GTA Morocco RAGE Running");
}
