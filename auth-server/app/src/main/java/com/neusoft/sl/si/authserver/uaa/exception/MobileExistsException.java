package com.neusoft.sl.si.authserver.uaa.exception;

import com.neusoft.sl.girder.ddd.core.exception.NestedSystemException;

public class MobileExistsException extends NestedSystemException {

	public MobileExistsException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
