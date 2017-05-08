package com.nalulabs.lib.mvp;

/**
 * Created by fabiocollini on 13/04/17.
 */

public abstract class ManagedException extends Exception
{

    private int errorMessageRes;

    private int retryButtonText;

    public ManagedException(int errorMessageRes, int retryButtonText)
    {
        this.errorMessageRes = errorMessageRes;
        this.retryButtonText = retryButtonText;
    }

    public ManagedException(int errorMessageRes, int retryButtonText, Throwable cause)
    {
        super(cause);
        this.errorMessageRes = errorMessageRes;
        this.retryButtonText = retryButtonText;
    }

    public int getErrorMessageRes()
    {
        return errorMessageRes;
    }

    public int getRetryButtonText()
    {
        return retryButtonText;
    }
}
