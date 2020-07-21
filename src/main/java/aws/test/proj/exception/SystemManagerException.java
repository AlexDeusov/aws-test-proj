package aws.test.proj.exception;

public class SystemManagerException extends ATPException {

	public SystemManagerException(String parameterKey) {

		super("Cannot get '" + parameterKey + "' value");
	}
}
