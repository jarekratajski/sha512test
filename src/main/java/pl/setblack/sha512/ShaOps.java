package pl.setblack.sha512;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

public class ShaOps {
    final static int rounds = 1024;

    final MessageDigest md512;
    final MessageDigest bcMd512;

    public ShaOps() {
        try {
            this.md512 = MessageDigest.getInstance("SHA-512");

            Security.addProvider(new BouncyCastleProvider());
            this.bcMd512 = MessageDigest.getInstance("SHA-512","BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new IllegalStateException(e);
        }

    }

    private double someop(double x) {
        return Math.sin(x);
    }

    byte[] shaJvm(byte[] input) {
        byte[] next = input;
        for (int i = 0; i < rounds; i++) {
            next = md512.digest(next);
        }
        return next;
    }

    byte[] shaBC(byte[] input) {
        byte[] next = input;
        for (int i = 0; i < rounds; i++) {
            next = bcMd512.digest(next);
        }
        return next;

    }

    byte[][] shaJvm(ShaData data) {
        byte[][] result = new byte[data.dataToHash.length][];
        for (int i = 0; i < data.dataToHash.length; i++) {
            result[i] = shaJvm(data.dataToHash[i]);
        }
        return result;
    }

    byte[][] shaBC(ShaData data) {
        byte[][] result = new byte[data.dataToHash.length][];
        for (int i = 0; i < data.dataToHash.length; i++) {
            result[i] = shaBC(data.dataToHash[i]);
        }
        return result;
    }


    byte[][] shaCommonsCodec(ShaData data) {
        byte[][] result = new byte[data.dataToHash.length][];
        for (int i = 0; i < data.dataToHash.length; i++) {
            byte[] next = data.dataToHash[i];
            for (int r=0; r<rounds; r++) {
                next = DigestUtils.sha512(next);
            }
            result[i] = next;
        }
        return result;
    }

    static String makeHex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    static String[] makeHex(byte[][] data) {
        String[] result = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = makeHex(data[i]);
        }
        return result;
    }


    public static void main(String[] args) {
        ShaData shaData = new ShaData(32);
        ShaOps shaLib = new ShaOps();
        byte[][] normalResult = shaLib.shaJvm(shaData);
        byte[][] bcRes = shaLib.shaBC(shaData);
        byte[][] apacheCommonsRes = shaLib.shaCommonsCodec(shaData);

        assert Arrays.deepEquals(normalResult, bcRes);
        assert Arrays.deepEquals(normalResult, apacheCommonsRes);
        String[] hexes = makeHex(normalResult);
        System.out.println(Arrays.asList(hexes));
        String[] apacheHexes = makeHex(apacheCommonsRes);
        System.out.println(Arrays.asList(apacheHexes));
    }
}
