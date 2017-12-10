package com.k2.XMLBuilder;

import java.io.PrintWriter;

public class CDataExample {

	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("!@#$%^&*()_+\n")
			.append("QWERTYUIOP{}\n")
			.append("ASDFGHJKL:\"|\n")
			.append("~ZXCVBNM<>?\n");
		
		XMLBuilder xb = new XMLBuilder();
		XMLDocument doc = xb.document("root");
		doc.elem("elm")
			.cData("AAAA")
			.up()
		.elem("elm")
			.cData(sb)
			.up();
		
		sb.append("1234567890-=\n")
			.append("qwertyuiop[]\n")
			.append("asdfghjkl;'\\\n")
			.append("`zxcvbnm,./");
		
		doc.toXml(new PrintWriter(System.out)).flush();

	}

}
