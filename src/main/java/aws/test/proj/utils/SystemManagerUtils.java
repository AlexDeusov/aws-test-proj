package aws.test.proj.utils;

import aws.test.proj.exception.SystemManagerException;
import software.amazon.awssdk.services.ssm.SsmAsyncClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public final class SystemManagerUtils {

	private final static SsmAsyncClient ASYNC_CLIENT = SsmAsyncClient.builder().build();

	private final static String REST_API_BASE_URL_KEY = "REST_API_BASE_URL";
	private final static String S3_BUCKET_NAME_KEY = "S3_BUCKET_NAME_KEY";

	public static String findParameterByKey(String parameterKey) throws SystemManagerException {

		return getParameter(parameterKey);
	}

	public static String getRestApiBaseUrlParameter() throws SystemManagerException {

		return getParameter(REST_API_BASE_URL_KEY);
	}

	public static String getS3BucketNameParameter() throws SystemManagerException {

		return getParameter(S3_BUCKET_NAME_KEY);
	}

	private static String getParameter(String parameterKey) throws SystemManagerException {

		String parameterValue = System.getenv(parameterKey);

		GetParameterRequest parameterRequest = GetParameterRequest.builder()
				.name(parameterValue)
				.withDecryption(true)
				.build();
		try {
			GetParameterResponse parameterResponse = ASYNC_CLIENT.getParameter(parameterRequest).get();
			return parameterResponse.parameter().value();
		} catch (InterruptedException | ExecutionException e) {
			throw new SystemManagerException(parameterKey);
		}
	}
}
