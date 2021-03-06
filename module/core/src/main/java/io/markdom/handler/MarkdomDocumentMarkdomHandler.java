package io.markdom.handler;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomContentParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomTextContent;
import io.markdom.model.MarkdomUnorderedListBlock;
import io.markdom.util.ObjectHelper;

public final class MarkdomDocumentMarkdomHandler implements MarkdomHandler<MarkdomDocument> {

	private final MarkdomFactory factory;

	private final MarkdomDocument document;

	private final Stack<MarkdomListBlock> listBlocks = new Stack<MarkdomListBlock>();

	private final Stack<MarkdomBlockParent> blockParents = new Stack<MarkdomBlockParent>();

	private final Stack<MarkdomContentParent> contentParents = new Stack<MarkdomContentParent>();

	public MarkdomDocumentMarkdomHandler(MarkdomFactory factory) {
		this.factory = ObjectHelper.notNull("factory", factory);
		this.document = factory.document();
	}

	@Override
	public void onDocumentBegin() {
		blockParents.push(document);
	}

	@Override
	public void onBlocksBegin() {
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		MarkdomCodeBlock block = factory.codeBlock(code, hint);
		blockParents.peek().addBlock(block);
	}

	@Override
	public void onCommentBlock(String comment) {
		MarkdomCommentBlock block = factory.commentBlock(comment);
		blockParents.peek().addBlock(block);
	}

	@Override
	public void onDivisionBlock() {
		blockParents.peek().addBlock(factory.divisionBlock());
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		MarkdomHeadingBlock block = factory.headingBlock(level);
		blockParents.peek().addBlock(block);
		contentParents.push(block);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		contentParents.pop();
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		MarkdomOrderedListBlock block = factory.orderedListBlock(startIndex);
		blockParents.peek().addBlock(block);
		listBlocks.push(block);
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		listBlocks.pop();
	}

	@Override
	public void onParagraphBlockBegin() {
		MarkdomParagraphBlock block = factory.paragraphBlock();
		blockParents.peek().addBlock(block);
		contentParents.push(block);
	}

	@Override
	public void onParagraphBlockEnd() {
		contentParents.pop();
	}

	@Override
	public void onQuoteBlockBegin() {
		MarkdomQuoteBlock block = factory.quoteBlock();
		blockParents.peek().addBlock(block);
		blockParents.push(block);
	}

	@Override
	public void onQuoteBlockEnd() {
		blockParents.pop();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		MarkdomUnorderedListBlock block = factory.unorderedListBlock();
		blockParents.peek().addBlock(block);
		listBlocks.push(block);
	}

	@Override
	public void onUnorderedListBlockEnd() {
		listBlocks.pop();
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
	}

	@Override
	public void onNextBlock() {
	}

	@Override
	public void onBlocksEnd() {
	}

	@Override
	public void onListItemsBegin() {
	}

	@Override
	public void onListItemBegin() {
		MarkdomListItem item = factory.listItem();
		listBlocks.peek().addListItem(item);
		blockParents.push(item);
	}

	@Override
	public void onListItemEnd() {
		blockParents.pop();
	}

	@Override
	public void onNextListItem() {
	}

	@Override
	public void onListItemsEnd() {
	}

	@Override
	public void onContentsBegin() {
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
	}

	@Override
	public void onCodeContent(String code) {
		MarkdomCodeContent content = factory.codeContent(code);
		contentParents.peek().addContent(content);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		MarkdomEmphasisContent content = factory.emphasisContent(level);
		contentParents.peek().addContent(content);
		contentParents.push(content);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		contentParents.pop();
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		MarkdomImageContent content = factory.imageContent(uri, title, alternative);
		contentParents.peek().addContent(content);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		contentParents.peek().addContent(factory.lineBreakContent(hard));
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		MarkdomLinkContent content = factory.linkContent(uri, title);
		contentParents.peek().addContent(content);
		contentParents.push(content);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		contentParents.pop();
	}

	@Override
	public void onTextContent(String text) {
		MarkdomTextContent content = factory.textContent(text);
		contentParents.peek().addContent(content);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
	}

	@Override
	public void onNextContent() {
	}

	@Override
	public void onContentsEnd() {
	}

	@Override
	public void onDocumentEnd() {
		blockParents.pop();
	}

	@Override
	public MarkdomDocument getResult() {
		return document;
	}

}
