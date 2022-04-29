package com.elearning.helper;

public class ApiRes<T> {
	protected String ErrorCode;
	protected boolean Error = false;
	protected String ErrorReason;
	protected String ToastMessage;
	protected T object;

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public boolean isError() {
		return Error;
	}

	public void setError(boolean error) {
		Error = error;
	}

	public String getErrorReason() {
		return ErrorReason;
	}

	public void setErrorReason(String errorReason) {
		ErrorReason = errorReason;
	}

	public String getToastMessage() {
		return ToastMessage;
	}

	public void setToastMessage(String toastMessage) {
		ToastMessage = toastMessage;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
