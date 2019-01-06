// See LICENSE for license details.
//
//Start with a static tb and try to genererate a gnerator for it
package decouple_branch
import chisel3._
import chisel3.util._
import chisel3.experimental._
import dsptools._
import dsptools.numbers._

//Class of programmable
class decouple_branch[ T<: Data]( proto: T, n: Int=5) extends Module {
    val io=IO( new Bundle {
        val Ai = Flipped(DecoupledIO(proto.cloneType))
        val Bo = Vec(n,DecoupledIO(proto.cloneType))
       }
   )
    io.Bo.map(_.valid:=io.Ai.valid)
    io.Ai.ready:=true.B
    when (io.Ai.ready ) {
            io.Bo.map(_.bits:=io.Ai.bits)
        } .otherwise {
            io.Bo.map(_.bits:=0.U.asTypeOf(io.Ai.bits))
    }
}


object decouple_branch extends App {
  val proto = UInt(8.W)
  chisel3.Driver.execute(args, () => new decouple_branch(proto=proto, n=8))
}

