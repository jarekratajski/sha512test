package pl.setblack.sha512;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
@Warmup(iterations = 4)
@Measurement(iterations = 5)
@Fork(3)
public class ShaBenchmark {

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
    public byte[][] lessAllocJvmSha512(){
        return shaLib.shaJvmLessAlloc(shaData);
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