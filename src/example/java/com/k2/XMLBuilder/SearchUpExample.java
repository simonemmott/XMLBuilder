package com.k2.XMLBuilder;

import java.io.PrintWriter;
import java.util.List;

public class SearchUpExample {

	public static void main(String[] args) {
		
		XMLBuilder xb = new XMLBuilder();
		XMLDocument doc = xb.document("root");
		XMLElement ddd = doc
		.elem("aaa")
			.attr("id", "1")
			.attr("attr", "value")
			.attr("attr3", "may-be-not")
			.data("AAAA")
			.elem("aaa")
				.attr("id", "2")
				.attr("class", "cls")
				.attr("attr2", "no")
				.attr("attr3", "maybe not")
				.elem("bbb")
					.attr("id", "3")
					.attr("attr1",  "no")
					.attr("attr2", "yes")
					.attr("attr3", "or maybe")
					.elem("ccc")
						.attr("id", "4")
						.attr("class", "cls")
						.attr("attr2", "yes")
						.elem("ddd");
		doc
		.elem("elm")
			.attr("attr", "value2")
			.data("BBBB")
			.up()
		.toXml(new PrintWriter(System.out)).flush();
		
		List<XMLElement> found = ddd.findUp("aaa");
		
		for (XMLElement e : found) {
			System.out.println(e.toString());
		}

	}

}
