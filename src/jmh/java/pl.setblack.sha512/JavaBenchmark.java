package pl.setblack.sha512;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(1)
public class JavaBenchmark {

    ShaData shaData = new ShaData(128);
    ShaOps shaLib = new ShaOps();

    @Setup
    public void setUp() {

    }

    @Benchmark
    public byte[][] jvmSha512(){
        return shaLib.shaJvm(shaData);
    }

    @Benchmark
    public byte[][] jvmShaBC512(){
        return shaLib.shaBC(shaData);
    }

    @Benchmark
    public byte[][] apacheSha512(){
        return shaLib.shaCommonsCodec(shaData);
    }


}