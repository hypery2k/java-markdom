package io.markdom.handler.json.jackson;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.common.MarkdomSchemas;
import io.markdom.handler.AbstractMarkdomHandler;
import lombok.SneakyThrows;

public final class JsonGeneratorMarkdomHandler extends AbstractMarkdomHandler<Void> {

	private final JsonGenerator generator;

	private final boolean includeSchema;

	public JsonGeneratorMarkdomHandler(JsonGenerator generator) {
		this(generator, false);
	}

	public JsonGeneratorMarkdomHandler(JsonGenerator generator, boolean includeSchema) {
		if (null == generator) {
			throw new IllegalArgumentException("The given JSON generator is null");
		}
		this.generator = generator;
		this.includeSchema = includeSchema;
	}

	@Override
	@SneakyThrows
	public void onDocumentBegin() {
		generator.writeStartObject();
		if (includeSchema) {
			generator.writeFieldName("$schema");
			generator.writeString(MarkdomSchemas.JSON);
		}
		generator.writeFieldName(MarkdomKeys.VERSION);
		generator.writeString("1.0");
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onDocumentEnd() {
		generator.writeEndArray();
		generator.writeEndObject();
		generator.flush();
	}

	@Override
	@SneakyThrows
	public void onBlockBegin(MarkdomBlockType type) {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.TYPE);
		generator.writeString(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeBlock(String code, Optional<String> hint) {
		generator.writeFieldName(MarkdomKeys.CODE);
		generator.writeString(code);
		if (hint.isPresent()) {
			generator.writeFieldName(MarkdomKeys.HINT);
			generator.writeString(hint.get());
		}
	}

	@Override
	@SneakyThrows
	public void onCommentBlock(String comment) {
		generator.writeFieldName(MarkdomKeys.COMMENT);
		generator.writeString(comment);
	}

	@Override
	@SneakyThrows
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		generator.writeFieldName(MarkdomKeys.LEVEL);
		generator.writeNumber(level.ordinal() + 1);
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockBegin() {
		generator.writeFieldName(MarkdomKeys.ITEMS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockBegin(Integer startIndex) {
		generator.writeFieldName(MarkdomKeys.START_INDEX);
		generator.writeNumber(startIndex);
		generator.writeFieldName(MarkdomKeys.ITEMS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onListItemBegin() {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onListItemEnd() {
		generator.writeEndArray();
		generator.writeEndObject();
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockEnd() {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockEnd(Integer startIndex) {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockBegin() {
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockEnd() {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onBlockEnd(MarkdomBlockType type) {
		generator.writeEndObject();
	}

	@Override
	@SneakyThrows
	public void onContentsBegin() {
		generator.writeFieldName(MarkdomKeys.CONTENTS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onContentBegin(MarkdomContentType type) {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.TYPE);
		generator.writeString(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeContent(String code) {
		generator.writeFieldName(MarkdomKeys.CODE);
		generator.writeString(code);
	}

	@Override
	@SneakyThrows
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		generator.writeFieldName(MarkdomKeys.LEVEL);
		generator.writeNumber(level.ordinal() + 1);
	}

	@Override
	@SneakyThrows
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		generator.writeFieldName(MarkdomKeys.URI);
		generator.writeString(uri);
		if (title.isPresent()) {
			generator.writeFieldName(MarkdomKeys.TITLE);
			generator.writeString(title.get());
		}
		if (alternative.isPresent()) {
			generator.writeFieldName(MarkdomKeys.ALTERNATIVE);
			generator.writeString(alternative.get());
		}
	}

	@Override
	@SneakyThrows
	public void onLineBreakContent(Boolean hard) {
		generator.writeFieldName(MarkdomKeys.HARD);
		generator.writeBoolean(hard);
	}

	@Override
	@SneakyThrows
	public void onLinkContentBegin(String uri, Optional<String> title) {
		generator.writeFieldName(MarkdomKeys.URI);
		generator.writeString(uri);
		if (title.isPresent()) {
			generator.writeFieldName(MarkdomKeys.TITLE);
			generator.writeString(title.get());
		}
	}

	@Override
	@SneakyThrows
	public void onTextContent(String text) {
		generator.writeFieldName(MarkdomKeys.TEXT);
		generator.writeString(text);
	}

	@Override
	@SneakyThrows
	public void onContentEnd(MarkdomContentType type) {
		generator.writeEndObject();
	}

	@Override
	@SneakyThrows
	public void onContentsEnd() {
		generator.writeEndArray();
	}

	@Override
	public Void getResult() {
		return null;
	}

}