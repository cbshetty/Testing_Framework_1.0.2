package com.api.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class PojoMap {

	/*public static Quote1StdResponse MDSQuote1Std_BinarytoPojo(byte[] binary) {
		binary = Arrays.copyOfRange(binary, 3, binary.length);
		ByteBuffer packet = ByteBuffer.wrap(binary).order(ByteOrder.LITTLE_ENDIAN);
		Quote1StdResponse quoteAPI1Response = new Quote1StdResponse(packet.get(),
				packet.getInt(),
				packet.get(),
				packet.getInt(),
				packet.getInt(),
				packet.getInt(),
				packet.getInt());
		return quoteAPI1Response;
	}*/

	
}
