package aws.test.proj.exception;

public class HttpRequestException extends ATPException {

	public HttpRequestException(String url) {

		super("Request evaluation on '" + url + "' has aborted");
	}
}
