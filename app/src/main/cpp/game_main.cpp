#include <jni.h>
#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_turrinistudio_gtav_MainActivity_stringFromJNI(JNIEnv* env, jobject) {
    __android_log_print(ANDROID_LOG_INFO, "RAGE", "GTA Morocco Started");
    return env->NewStringUTF("GTA Morocco RAGE\n\nEngine: OK\nPrivate Build: Active\nTangier - Morocco");
}
