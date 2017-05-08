package com.nalulabs.lib.mvp;

import com.nalulabs.lib.R;

/**
 * Created by fabiocollini on 13/04/17.
 */

public class ConnectionNotAvailableException extends ManagedException
{
    public ConnectionNotAvailableException()
    {
        super(R.string.connection_error_message, R.string.reconnect);
    }
}
