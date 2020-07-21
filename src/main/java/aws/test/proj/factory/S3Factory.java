package aws.test.proj.factory;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

public class S3Factory {

	private static final Region REGION = Region.US_EAST_1;

	public static S3AsyncClient asyncS3client() {

		return S3AsyncClient.builder()
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.region(REGION)
				.build();
	}
}
