package aws.test.proj.factory;

import software.amazon.awssdk.services.ssm.SsmAsyncClient;

public class SystemManagerFactory {

	public static SsmAsyncClient systemManagerAsyncClient() {

		return SsmAsyncClient.builder().build();
	}
}
