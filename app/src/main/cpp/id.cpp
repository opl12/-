#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_getBJ(JNIEnv *env, jobject thiz) {
    // TODO: implement getBJ()
    std::string hello = "/storage/emulated/0/bj.txt";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_getfragment(JNIEnv *env, jobject thiz) {
    // TODO: implement getBJ()
    std::string hello = "/storage/emulated/0/JianXuan/fragment.txt";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_xmao(JNIEnv *env, jobject thiz) {
    // TODO: implement xmao()
    std::string hello = "/storage/emulated/0/JianXuan/fragment.txt";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_ua(JNIEnv *env, jobject thiz) {
    // TODO: implement ua()
    std::string url="http://1-2.ltd/ua.php";
    return env->NewStringUTF(url.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_app(JNIEnv *env, jobject thiz) {
    // TODO: implement app()
    std::string app="青阅";
    return env->NewStringUTF(app.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_key(JNIEnv *env, jclass clazz) {
    // TODO: implement key()
    std::string app="cBtRX6o_sMQ0yig--6qb-GpfbKrx5SfE";
    return env->NewStringUTF(app.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_qq(JNIEnv *env, jclass clazz) {
    // TODO: implement qq()
    std::string app="2958474980";
    return env->NewStringUTF(app.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_id_qun(JNIEnv *env, jclass clazz) {
    // TODO: implement qun()
    std::string app="mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D";
    return env->NewStringUTF(app.c_str());
}