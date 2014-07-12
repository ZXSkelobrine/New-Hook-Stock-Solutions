package com.github.ZXSkelobrine.stock.global.variables;

public class ErrorLogItem {

	private String errorClass;
	private String errorMethod;
	private String additionalMessage;
	private String errorMessage;

	private int errorLine;

	private StackTraceElement[] stackTraceElements;

	public String getCompleteOutput(boolean includeStackTrace) {
		if (includeStackTrace) {
			StringBuilder sb = new StringBuilder();
			sb.append(errorClass + "." + errorMethod + "." + errorLine + ": " + errorMessage + " - " + additionalMessage);
			for (StackTraceElement ste : stackTraceElements) {
				sb.append("\t" + ste.getClassName() + "." + ste.getMethodName() + "." + ste.getLineNumber());
			}
			sb.append("\n");
			return sb.toString();
		} else {
			return errorClass + "." + errorMethod + "." + errorLine + ": " + errorMessage + " - " + additionalMessage;
		}
	}

	public ErrorLogItem(String errorClass, String errorMethod, String additionalMessage, String errorMessage, int errorLine, StackTraceElement[] stackTraceElements) {
		this.errorClass = errorClass;
		this.errorMethod = errorMethod;
		this.additionalMessage = additionalMessage;
		this.errorMessage = errorMessage;
		this.errorLine = errorLine;
		this.stackTraceElements = stackTraceElements;
	}

	/**
	 * @return the errorClass
	 */
	public String getErrorClass() {
		return errorClass;
	}

	/**
	 * @param errorClass
	 *            the errorClass to set
	 */
	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	/**
	 * @return the errorMethod
	 */
	public String getErrorMethod() {
		return errorMethod;
	}

	/**
	 * @param errorMethod
	 *            the errorMethod to set
	 */
	public void setErrorMethod(String errorMethod) {
		this.errorMethod = errorMethod;
	}

	/**
	 * @return the additionalMessage
	 */
	public String getAdditionalMessage() {
		return additionalMessage;
	}

	/**
	 * @param additionalMessage
	 *            the additionalMessage to set
	 */
	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorLine
	 */
	public int getErrorLine() {
		return errorLine;
	}

	/**
	 * @param errorLine
	 *            the errorLine to set
	 */
	public void setErrorLine(int errorLine) {
		this.errorLine = errorLine;
	}

	/**
	 * @return the stackTraceElements
	 */
	public StackTraceElement[] getStackTraceElements() {
		return stackTraceElements;
	}

	/**
	 * @param stackTraceElements
	 *            the stackTraceElements to set
	 */
	public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
		this.stackTraceElements = stackTraceElements;
	}

}
