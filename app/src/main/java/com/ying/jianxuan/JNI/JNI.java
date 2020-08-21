package com.ying.jianxuan.JNI;

public class JNI {
    /*
    hss3uro2hsxfogfq.onion
    xmh57jrzrnw6insl.onion
    msydqstlz2kzerdg.onion
    gjobqjj7wyczbqie.onion
    searchb5a7tmimez.onion
    archive.org
    http //vlib.org
    wolframalpha.com/
    deepwebtech.com
    duckduckgo.com
    zqktlwi4fecvo6ri.onion/wiki/index.php/Main_Page
    archivecaslytosk.onion/
    dnmugu4755642434.onion
     */
    static {
        System.loadLibrary("native-lib");
    }

    public native  void setfile(String srcPath, String targetPath);
    public native String getFile(String path);

}
