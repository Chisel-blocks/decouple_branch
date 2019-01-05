
// Dsp-block arbiter_test
// Description here 
// Inititally written by dsp-blocks initmodule.sh, 20190105
package arbiter_test

import chisel3.experimental._
import chisel3._
import dsptools._
import dsptools.numbers._
import breeze.math.Complex

class arbiter_test_io[T <:Data](proto: T,n: Int) 
   extends Bundle {
        val A       = Input(Vec(n,proto))
        val B       = Output(Vec(n,proto))
        override def cloneType = (new arbiter_test_io(proto.cloneType,n)).asInstanceOf[this.type]
   }

class arbiter_test[T <:Data] (proto: T,n: Int) extends Module {
    val io = IO(new arbiter_test_io( proto=proto, n=n))
    val register=RegInit(VecInit(Seq.fill(n)(0.U.asTypeOf(proto.cloneType))))
    register:=io.A
    io.B:=register
}

//This gives you verilog
object arbiter_test extends App {
    chisel3.Driver.execute(args, () => new arbiter_test(
        proto=DspComplex(UInt(16.W),UInt(16.W)), n=8) 
    )
}
