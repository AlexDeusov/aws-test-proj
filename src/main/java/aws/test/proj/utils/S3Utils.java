package aws.test.proj.utils;

import aws.test.proj.exception.S3Exception;
import aws.test.proj.exception.SystemManagerException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public final class S3Utils {

	private final static Region REGION = Region.US_EAST_1;

	public static PutObjectResponse uploadObject(ByteBuffer byteBuffer, int userId) throws SystemManagerException, S3Exception {

		try {
			return createClient().putObject(PutObjectRequest.builder()
							.bucket(SystemManagerUtils.getS3BucketNameParameter())
							.key(createObjectName(userId))
							.acl(ObjectCannedACL.PUBLIC_READ)
							.build(),
					AsyncRequestBody.fromByteBuffer(byteBuffer)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new S3Exception("Cannot upload object to S3");
		}
	}

	private static S3AsyncClient createClient() throws SystemManagerException {

		String accessKey = SystemManagerUtils.getAccessKeyParameter();
		String secretKey = SystemManagerUtils.getSecretKeyParameter();

		AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

		return S3AsyncClient.builder()
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(REGION)
				.build();
	}

	private static String createBucketName(int userId) {

		return "posts/user=" + userId + "/posts/";
	}

	private static String createObjectName(int userId) {

		return createBucketName(userId) + "posts_user=" + userId + "_" + System.currentTimeMillis() + ".json.gz";
	}
}
