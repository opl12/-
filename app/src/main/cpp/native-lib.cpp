#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ying_jianxuan_JNI_JNI_getFile(JNIEnv *env, jobject thiz, jstring path) {
    // TODO: implement getFile()
    const char *jpath = env->GetStringUTFChars(path, NULL);
    FILE *pFile;
    pFile = fopen(jpath, "rw");
    if (pFile == NULL) {
        return env->NewStringUTF("文件读出失败");
    }
    char *pBuf;  //定义文件指针
    fseek(pFile, 0, SEEK_END); //把指针移动到文件的结尾 ，获取文件长度
    int size = (int) ftell(pFile); //获取文件长度
    pBuf = new char[size + 1]; //定义数组长度
    rewind(pFile); //把指针移动到文件开头 因为我们一开始把指针移动到结尾，如果不移动回来 会出错
    fread(pBuf, 1, (size_t) size, pFile); //读文件
    pBuf[size] = 0; //把读到的文件最后一位 写为0 要不然系统会一直寻找到0后才结束
    fclose(pFile); // 关闭文件
    return env->NewStringUTF(pBuf);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_ying_jianxuan_JNI_JNI_setfile(JNIEnv *env, jobject thiz, jstring src_path,
                                                jstring target_path) {
    // TODO: implement readAndWriteFile()
    //因为c的fopen函数对应的路径参数必须是char类型的，所以我们需要将jstring 转换为 char,因为在JNI中jChar == char所以调用
    //GetStringUTFChars函数即可。
    const char * src = env-> GetStringUTFChars( src_path, NULL);
    const char * target = env-> GetStringUTFChars(target_path, NULL);
    FILE * rf = fopen(src, "rb");
    FILE * wf = fopen(target, "wb");
    char buff [50];
    int len;
    while(len = fread(buff, sizeof(char), 50 , rf) !=0){
        fwrite(buff, sizeof(char) , len , wf);
    }
    fclose(wf);
    fclose(rf);
    env -> ReleaseStringUTFChars( src_path, src); //通知JVM释放内存
    env -> ReleaseStringUTFChars(target_path, target); //通知JVM释放内存

}