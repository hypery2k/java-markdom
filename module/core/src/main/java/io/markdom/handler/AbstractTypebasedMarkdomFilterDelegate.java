package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public abstract class AbstractTypebasedMarkdomFilterDelegate implements TypebasedMarkdomFilterDelegate {

	@Override
	public boolean testBlockType(MarkdomBlockType type) {
		return false;
	}

	@Override
	public boolean testContentType(MarkdomContentType type) {
		return false;
	}

}
