package pl.setblack.sha512;

import java.nio.charset.Charset;

public class ShaData {
    final byte[][] dataToHash;
    final static String defaultMmessagePrefix = "ojej a co to takie  tutaj bajty:";
    final static Charset aCharset = Charset.forName("ISO-8859-1");
    ShaData(int totalSize) {
        dataToHash = new byte[totalSize][];
        for (int i = 0; i< totalSize; i++) {
            final byte[] msgBytes = (defaultMmessagePrefix + i).getBytes(aCharset);
            dataToHash[i] = msgBytes;
        }
    }
}
