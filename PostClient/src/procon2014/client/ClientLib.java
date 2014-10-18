package procon2014.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ClientLib {
	private static final String PLAYER_ID_FIELD_NAME = "playerId";
	private static final String ANSWER_FIELD_NAME = "answer";
	private static final String PROBLEM_ID_FIELD_NAME = "problemId";
	private static final String SUBMIT_ANSWER_LOCATION = "/SubmitAnswer";
	private static final String PROBLEM_LOCATION = "/problem/";
	private static final String PROBLEM_FILENAME_FORMAT = "prob%1$02d.ppm";

	public static String submitAnswer(String serverUrl, String problemId,
			String playerId, String answer) {
		StringBuilder response = new StringBuilder();
		Map<String, String> options = new HashMap<String, String>();
		options.put(PLAYER_ID_FIELD_NAME, playerId);
		options.put(PROBLEM_ID_FIELD_NAME, problemId);
		options.put(ANSWER_FIELD_NAME, answer);
		String urlString = "http://" + serverUrl + SUBMIT_ANSWER_LOCATION;
		String bodyString = makeBody(options);

		try {
			URL url = new URL(urlString);

			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");

				// BodyïîèëÇ´çûÇ›
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						con.getOutputStream(), StandardCharsets.UTF_8));
				bw.write(bodyString);
				bw.flush();
				con.connect();

				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(
							con.getInputStream(), StandardCharsets.UTF_8);
					BufferedReader br = new BufferedReader(isr);
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
				}
			} catch (Exception e) {
				if (con != null) {
					con.disconnect();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	public static String makeBody(Map<String, String> options) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> pair : options.entrySet()) {
			sb.append(pair.getKey());
			sb.append('=');

			final String encodedValue;
			try {
				encodedValue = URLEncoder.encode(pair.getValue(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return null;
			}

			sb.append(encodedValue);
			sb.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String getProblem(String serverUrl, String problemId) {
		StringBuilder response = new StringBuilder();
		String urlString = "http://" + serverUrl + PROBLEM_LOCATION
				+ getProblemFileName(problemId);

		try {
			URL url = new URL(urlString);

			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");

				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(
							con.getInputStream(), StandardCharsets.UTF_8);
					BufferedReader br = new BufferedReader(isr);
					String line;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
				}
			} catch (Exception e) {
				if (con != null) {
					con.disconnect();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	public static String getProblemFileName(String problemId) {
		int id = Integer.parseInt(problemId);
		return String.format(PROBLEM_FILENAME_FORMAT, id);
	}
}
