package aws.test.proj.utils;

import aws.test.proj.exception.HttpRequestException;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.*;

public final class HttpUtils {

	public static HttpResponse<String> get(String url) throws HttpRequestException {

		HttpRequest request = buildRequest(url);

		try {
			return HttpClient.newBuilder()
					.proxy(ProxySelector.getDefault())
					.build()
					.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new HttpRequestException(url);
		}
	}

	private static HttpRequest buildRequest(String url) {

		return HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
	}
}
