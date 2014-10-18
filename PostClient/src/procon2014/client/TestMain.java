package procon2014.client;

import java.util.HashMap;
import java.util.Map;

public class TestMain {

	public static void main(String[] args) {
		// Body Test
		Map<String, String> options = new HashMap<String, String>();
		options.put("key1", "value 1");
		options.put("key2", "value-2");
		System.out.println(ClientLib.makeBody(options));

		// Filename Test
		System.out.println(ClientLib.getProblemFileName("1"));

		// Run Test
		System.out.println(ClientLib.getProblem("localhost", "0"));
		StringBuilder sb = new StringBuilder("2\r\n");
		sb.append("11\r\n6\r\nRDLLUU\r\n");
		sb.append("02\r\n4\r\nURUR");
		System.out.println(ClientLib.submitAnswer("localhost", "0", "13",
				sb.toString()));
	}
}
