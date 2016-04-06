// http://stackoverflow.com/questions/24417103/the-performance-of-java-scalas-deflator-for-compressing-bitset
// deflate algorithm described in RFC 1951.
//
package util.compression

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip.{InflaterInputStream, DeflaterOutputStream}


/**
 * Created by smcho on 1/4/15.
 */
object Compressor {

  def compress(str: String): Array[Byte] = {
    val bytes = str.getBytes()
    val deflater = new java.util.zip.Deflater
    val baos = new ByteArrayOutputStream
    val dos = new DeflaterOutputStream(baos, deflater)
    dos.write(bytes)
    baos.close
    dos.finish
    dos.close

    val ba = baos.toByteArray
    if (ba.size >= bytes.size) return bytes
    else return ba
  }

  def compress(bytes: Array[Byte]): Array[Byte] = {
    val deflater = new java.util.zip.Deflater
    val baos = new ByteArrayOutputStream
    val dos = new DeflaterOutputStream(baos, deflater)
    dos.write(bytes)
    baos.close
    dos.finish
    dos.close

    val ba = baos.toByteArray
    if (ba.size >= bytes.size) return bytes
    else return ba
  }

  def decompress(bytes: Array[Byte]): Array[Byte] = {
    val deflater = new java.util.zip.Inflater()
    val baos = new ByteArrayOutputStream(512)
    val bytesIn = new ByteArrayInputStream(bytes)
    val in = new InflaterInputStream(bytesIn, deflater)
    var go = true
    while (go) {
      val b = in.read
      if (b == -1)
        go = false
      else
        baos.write(b)
    }
    baos.close
    in.close
    // String(byte[] bytes, Charset charset)
    //new String(baos.toByteArray, "ASCII")
    baos.toByteArray
  }
}
