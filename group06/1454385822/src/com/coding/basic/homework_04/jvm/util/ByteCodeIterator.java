package com.coding.basic.homework_04.jvm.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public  class ByteCodeIterator {
	byte[] codes;
	int pos = 0;

	public ByteCodeIterator(byte[] codes) {
		this.codes = codes;
	}

	

	public byte[] getBytes(int len) {
		if (pos + len >= codes.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		byte[] data = Arrays.copyOfRange(codes, pos, pos + len);
		pos += len;
		return data;
	}

	public int nextU1toInt() {

		return Util.byteToInt(new byte[] { codes[pos++] });
	}

	public int nextU2ToInt() {
		return Util.byteToInt(new byte[] { codes[pos++], codes[pos++] });
	}

	public int nextU4ToInt() {
		return Util.byteToInt(new byte[] { codes[pos++], codes[pos++], codes[pos++], codes[pos++] });
	}

	public String nextU4ToHexString() {
		return Util.byteToHexString(new byte[] { codes[pos++], codes[pos++], codes[pos++], codes[pos++] });
	}

	public String nextUxToHexString(int len) throws UnsupportedEncodingException {
		byte[] tmp = new byte[len];

		for (int i = 0; i < len; i++) {
			tmp[i] = codes[pos++];
		}
		return new String(tmp, "utf8");

	}

	public void back(int n) {
		this.pos -= n;
	}
}
